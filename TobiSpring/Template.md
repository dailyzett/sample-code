# 1. 템플릿

## 1.1 예외처리 기능을 갖춘 DAO

```java
public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "delete from users"
        );
        ps.executeUpdate();

        ps.close();
        c.close();
}
```

위 코드는 예외가 발생하면 close() 메서드를 실행하지 않고 바로 SQLException 을 던진다.
풀(Pool) 방식으로 운영되는 JDBC는 close() 메서드를 통해 리소스를 반환하는데,
Exception 때문에 close 메서드를 계속 수행하지 못하는 상황이 발생하면 리소스가 고갈되고 문제가 발생한다.

그래서 JDBC 코드에서는 try/catch/finally 구문 사용을 권장하고 있다.
이 구문을 적용하면 코드는 아래와 같이 변경된다.

```java
public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = null;

        try {
            ps = c.prepareStatement(
                    "delete from users");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
```

## 1.2 변하는 것과 변하지 않는 것

### 1.2.1 JDBC try/catch/finally 코드의 문제점

- 코드가 너무 지저분하다.
- 만약 close() 메서드를 빠뜨리면 이것이 누적되어 서버에 장애를 일으킬 수 있다.

### 1.2.2 분리와 재사용을 위한 디자인 패턴 적용

- **메서드 추출**

위 코드는 변하는 부분이 변하지 않는 부분보다 많다.
변하는 부분을 따로 메서드를 추출해도 이 메서드는 재사용성이 없기 때문에 별로 좋지 않은 방법이다.

- **템플릿 메서드 패턴의 적용**

이 방법은 변하지 않는 부분을 상위 클래스에 두고 변하는 부분은 추상 메서드로 정의해둬서 하위 클래스에서 오버라이드하여 새롭게 정의해 쓰도록 하는 것이다.
하지만 DAO 로직마다 상속을 통해 새로운 클래스를 만들어야 하기 때문에 여기서는 좋지 않은 방법이다.
또 확장 구조가 컴파일 시점에서 고정되어 버리기 때문에 그 관계에 대한 유연성이 떨어진다.
이것은 상속을 통해 확장을 꾀하는 템플릿 메서드 패턴의 한계이다.

- **전략 패턴의 적용**

오브젝트를 둘로 분리하고 클래스 레벨에서는 인터페이스를 통해서만 의존하도록 만드는 것이 전략 패턴이다.
OCP 관점에서 보면 확장에 해당하는 변하는 부분을 별도의 클래스로 만들어 추상화된 인터페이스를 통해 위임하는 방식이다.

![](2.png)

전략 패턴은 컨텍스트, 전략, 컨텍스트를 호출하는 클라이언트로 나뉜다.
컨텍스트를 따로 분리하면 아래와 같은 코드가 된다.

```java
public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
```
클라이언트로부터 StatementStrategy 타입의 전략 오브젝트를 제공받고 JDBC try/catch/finally 구조로
만들어진 컨텍스트 내에서 작업을 수행한다. 제공받은 전략 오브젝트는 PrepareStatement 생성이 필요한 시점에
호출해서 사용한다. 모든 JDBC 코드의 고정된 작업이 이 컨텍스트 메서드에 담겨 있는 것이다.

다음은 클라이언트의 책임을 담당하는 deleteAll() 메서드이다.
deleteAll() 은 전략 객체를 생성하고 컨텍스트를 호출하는 책임을 가지고 있다.

```java
public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(st);
}
```

## 1.3 JDBC 전략 패턴 최적화

기존 JDBC 의 add 메서드도 전략 패턴을 이용해 작성할 수 있다.

```java
public void add(User user) throws ClassNotFoundException, SQLException {
    StatementStrategy st = new AddStatement(user);
    jdbcContextWithStatementStrategy(st);
}
```
```java
public class AddStatement implements StatementStrategy{
    User user;

    public AddStatement(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values (?, ?, ?)"
        );
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        return ps;
    }
}
```

하지만 이 구조에도 단점이 있는데, DAO 메서드마다 새로운 StatementStrategy 구현 클래스를 만들어야 한다는 점이다.
이렇게 되면 기존 UserDao 때보다 클래스 파일의 개수가 늘어난다.
이래서는 런타임 시에 다이나믹하게 DI 해준다는 점을 제외하면 템플릿 메서드 패턴과 다른게 없다.

또다른 단점은 DAO 메서드에서 StatementStrategy 에 전달할 User와 같은 부가적인 정보가 있는 경우,
이를 위해 생성자와 이를 저장해둘 인스턴스 변수를 만들어야 한다는 점이다.

### 해결 방법. 익명 내부 클래스

생성자가 필요하고 그 클래스가 한 번 쓰고 안 쓰일 클래스라면 익명 내부 클래스로 선언할 수 있다.

```java
public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
    }
```

Java 8 의 람다를 이용하면 더욱 간단하게 변경할 수 있다.

```java
public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            return ps;
        });
    }
```

