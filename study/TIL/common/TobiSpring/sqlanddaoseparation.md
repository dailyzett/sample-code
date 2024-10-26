# 목차

- [목차](#목차)
- [7.1 SQL과 DAO의 분리](#71-sql과-dao의-분리)
  - [7.1.1 XML 설정을 이용한 분리](#711-xml-설정을-이용한-분리)
    - [개별 SQL 프로퍼티 방식](#개별-sql-프로퍼티-방식)
    - [SQL 맵 프로퍼티 방식](#sql-맵-프로퍼티-방식)
  - [7.1.2 SQL 제공 서비스](#712-sql-제공-서비스)
    - [SQL 서비스 인터페이스](#sql-서비스-인터페이스)

# 7.1 SQL과 DAO의 분리

현재 DAO는 순수한 데이터 액세스 코드만 남아 있다.
여기서 DB 테이블과 필드정보를 담고 있는 SQL 문장을 분리해보자.

굳이 분리하는 이유는 아래와 같다.
- 필드 이름과 SQL 문장은 변경될 수 있다.
- DB 검색 쿼리 최적화를 위해 SQL에 부가적인 내용을 넣어야 할 수도 있다.

물론 처음부터 최적화된 SQL을 잘 준비하면 되겠지만 현실적으로 쉽지 않다.
하지만 SQL 변경이 요구된다고 해서 그 때마다 DAO 코드를 수정하고 다시 컴파일해서 적용하는 것은 번거롭고 위험성도 크다.

## 7.1.1 XML 설정을 이용한 분리

가장 쉽게 생각할 수 있는 방법은 SQL을 스프링의 XML 설정파일로 빼내는 것이다.
SQL은 문자열로 이루어져 있으니 설정파일에 프로퍼티 값으로 정의해서 DAO에 주입 가능하다.

### 개별 SQL 프로퍼티 방식

가장 간단한 add() 메서드의 SQL을 외부로 빼는 작업을 해보자.

```java
public class UserDaoJdbc implements UserDao{
    public String sqlAdd;

    public void setSqlAdd(String sqlAdd){
        this.sqlAdd = sqlAdd;
    }

        ...

    public void add(final User user) throws DuplicateKeyException {
        this.jdbcTemplate.update(this.sqlAdd,
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(),
                user.getLogin(), user.getRecommend(), user.getEmail());
    }
}
```

sqlAdd를 프로퍼티로 추가 후, add() 메서드에 기존 sql 구문이 있던 자리를 this.sqlAdd로 대체한다.
그리고 이 sqlAdd 프로퍼티에 DI를 통해 값을 넣어준다.

```xml
<bean id="userDao" class="dao.UserDaoJdbc">
    <property name="dataSource" ref="dataSource"/>
    <property name="sqlAdd" value="insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)"/>
</bean>
```

### SQL 맵 프로퍼티 방식

SQL이 점점 많아지면 그때마다 DAO에 DI용 프로퍼티를 추가하기가 상당히 귀찮다.
그래서 SQL을 하나의 컬렉션으로 담아두는 방법을 시도해보자.

```java
private Map<String, String> sqlMap;
```

```xml
<bean id="userDao" class="dao.UserDaoJdbc">
    <property name="dataSource" ref="dataSource"/>
    <property name="sqlMap">
        <map>
            <entry key="add" value="insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)"/>
            <entry key="get" value="select * from users where id = ?" />
            <entry key="getAll" value="select * from users order by id" />
            <entry key="deleteAll" value="delete from users"/>
            <entry key="getCount" value="select count(*) from users" />
            <entry key="update" value="update users set name=?, password=?, level=?, login=?, recommend = ?, email = ? where id = ?"/>
        </map>
    </property>
</bean>
```

## 7.1.2 SQL 제공 서비스

스프링 설정 파일에 SQL 을 두면 손쉽게 쿼리문을 분리해낼 수 있지만 몇 가지 문제점이 있다.

- SQL과 DI 설정정보가 섞여 있으면 보기에도 지저분하고 관리하기도 쉽지 않다.
- 스프링의 설정파일로부터 생성된 오브젝트와 정보는 애플리케이션을 다시 시작하기 전에는 변경이 매우 어렵다.
- SQL을 꼭 스프링의 빈 설정방법을 사용해 XML에 담아둘 이유가 없다.

이런 문제점을 해결하려면 DAO가 사용할 SQL을 제공해주는 기능을 독립시킬 필요가 있다.
독립적인 SQL 서비스가 필요하다는 뜻이다.

### SQL 서비스 인터페이스


가장 먼저 할 일은 SQL 서비스의 인터페이스를 설계하는 것이다.

```java
public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
```

SQL에 대한 키 값을 전달하면 그에 해당하는 SQL을 돌려주는 간단한 인터페이스이다.
그리고 이 인터페이스를 구현한 SimpleSqlService 클래스 생성 후 xml 파일도 수정해주면 끝이다.

<details>
<summary><b>sqlService를 구현한 SimpleSqlService 클래스 코드</b></summary>
<div markdown="1">

```java
public class SimpleSqlService implements SqlService{
    private Map<String, String> sqlMap;

    public void setSqlMap(Map<String, String> sqlMap) {
        this.sqlMap = sqlMap;
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다.");
        }
        else
            return sql;
    }
}
```

</div>
</details>

<details>
<summary><b>xml 설정 파일 코드</b></summary>
<div markdown="1">

```xml
<bean id="sqlService" class="sqlservice.SimpleSqlService">
    <property name="sqlMap">
        <map>
            <entry key="add" value="insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)"/>
            <entry key="get" value="select * from users where id = ?" />
            <entry key="getAll" value="select * from users order by id" />
            <entry key="deleteAll" value="delete from users"/>
            <entry key="getCount" value="select count(*) from users" />
            <entry key="update" value="update users set name=?, password=?, level=?, login=?, recommend = ?, email = ? where id = ?"/>
        </map>
    </property>
</bean>
```

</div>
</details>