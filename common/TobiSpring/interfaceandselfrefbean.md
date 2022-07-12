# 목차

- [목차](#목차)
- [7.2 인터페이스의 분리와 자기참조 빈](#72-인터페이스의-분리와-자기참조-빈)
  - [7.2.1 XML 파일 매핑](#721-xml-파일-매핑)
    - [JAXB](#jaxb)
    - [언마샬링](#언마샬링)
    - [XML SQL 서비스](#xml-sql-서비스)
  - [7.2.3 빈의 초기화 작업](#723-빈의-초기화-작업)
  - [7.2.4 변화를 위한 준비: 인터페이스 분리](#724-변화를-위한-준비-인터페이스-분리)
    - [책임에 따른 인터페이스 정의](#책임에-따른-인터페이스-정의)
    - [SqlRegistry 인터페이스](#sqlregistry-인터페이스)
    - [SqlReader 인터페이스](#sqlreader-인터페이스)
  - [7.2.5 자기참조 빈으로 시작하기](#725-자기참조-빈으로-시작하기)
    - [다중 인터페이스 구현과 간접 참조](#다중-인터페이스-구현과-간접-참조)
    - [인터페이스를 이용한 분리](#인터페이스를-이용한-분리)
    - [자기 참조 빈 설정](#자기-참조-빈-설정)
    - [자기참조 빈 설정](#자기참조-빈-설정)
  - [7.2.6 디폴트 의존관계](#726-디폴트-의존관계)
    - [디폴트 의존관계를 갖는 빈 만들기](#디폴트-의존관계를-갖는-빈-만들기)
      - [디폴트 의존 오브젝트의 단점](#디폴트-의존-오브젝트의-단점)

# 7.2 인터페이스의 분리와 자기참조 빈

## 7.2.1 XML 파일 매핑

빈 설정파일이 아닌 SQL을 저장해두는 전용 포맷을 가진 독립적인 파일을 이용해보자.

### JAXB

XML과 비교했을 때 JAXB의 장점

- DOM은 XML 정보를 마치 자바의 리플렉션 API를 사용해서 오브젝트를 조작하는 것처럼 간접적으로 접근해야하는 불편함이 있다.
그에 비해 JAXB는 XML의 정보를 그대로 담고 있는 오브젝트 트리 구조로 만들어주기 때문에 XML 정보를 오브젝트처럼 다룰 수 있어 편리하다.
- JAXB는 XML 문서의 구조를 정의한 스키마를 이용해서 매핑할 오브젝트의 클래스까지 자동으로 만들어주는 컴파일러도 제공해준다.


### 언마샬링

XML 문서를 읽어서 자바의 오브젝트로 변환하는 것을 JAXB에서는 **언마샬링**이라고 부른다.

반대로 바인딩 오브젝트를 XML 문서로 변환하는 것을 **마샬링**이라고 한다.

### XML SQL 서비스

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<sqlmap xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <sql key="userAdd">insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)</sql>
    <sql key="userGet">select * from users where id = ?</sql>
    <sql key="userGetAll">select * from users order by id</sql>
    <sql key="userDeleteAll">delete from users</sql>
    <sql key="userGetCount">select count(*) from users</sql>
    <sql key="userUpdate">update users set name=?, password=?, level=?, login=?, recommend = ?, email = ? where id = ?</sql>
</sqlmap>
```

이제 sqlmap.xml에 있는 SQL을 가져와 DAO에 제공해주는 SqlService 인터페이스의 구현 클래스를 만들면 된다.

그런데 DAO가 SQL을 요청할 때마다 XML 파일을 매번 읽는 것은 너무 비효율인 방식이다.
그래서 XML 파일로부터 읽은 내용은 어딘가에 저장해두고 DAO에서 요청이 올 때 사용해야 한다.

SQlService를 구현한 클래스는 스프링이 관리하는 빈으로 등록이 될 것이다.
일단 간단히 생성자에서 SQL을 읽어와 내부에 저장해두는 초기 작업을 하자.

JAXB로 XML 문서를 언마샬링하면 SQL 문장 하나는 Sql 클래스의 오브젝트에 하나씩 담길 것이다.
하지만 이 Sql 오브젝트를 리스트에 저장해뒀다가 사용하는 방법은 별로 좋지 않다.
매번 검색을 위해 리스트를 검사해야하기 때문이다.
차라리 상대적으로 검색 속도가 빠른 Map 타입 오브젝트에 저장해두는게 낫다.


<details>
<summary><b>XmlSqlService 구현 코드</b></summary>
<div markdown="1">

```java
public class XmlSqlService implements SqlService{
    private Map<String, String> sqlMap = new HashMap<>();

    public XmlSqlService() {
        try{
            JAXBContext context = JAXBContext.newInstance(Sqlmap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream("/sqlmap.xml");
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for (SqlType sql : sqlmap.getSql()) {
                sqlMap.put(sql.getKey(), sql.getValue());
            }
        }catch (JAXBException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        String sql = sqlMap.get(key);
        if (sql == null) {
            throw new SqlRetrievalFailureException(key = "를 이용해서 SQL을 찾을 수 없습니다.");
        }else{
            return sql;
        }
    }
}
```

</div>
</details>

## 7.2.3 빈의 초기화 작업

XmlSqlService 코드를 보면 몇 가지 개선 사항이 눈에 띈다.

- 생성자에서 예외가 발생할 수도 있는 복잡한 초기화 작업을 다루는 것은 좋지 않다.
오브젝트를 생성하는 중에 생성자에서 발생하는 예외는 다루기 힘들고, 상속하기 힘들며, 보안에도 문제가 생긴다.
- 읽어들일 파일의 위치와 이름이 코드에 고정되어 있다.
SQL을 담은 XML 파일의 위치와 이름이 코드에 고정되어 있는 것은 좋지 않다.
코드의 로직과 여타 이유로 바뀔 가능성이 있는 내용은 외부에서 DI로 설정해줘야 한다.

```java
private String sqlmapFile;

public void setSqlmapFile(String sqlmapFile) {
        this.sqlmapFile = sqlmapFile;
}
```

값을 외부에서 주입해주기 위해 sqlmapFile 프로퍼티를 추가한다. 그리고 기존 생성자에서 초기화하던 코드 전부를
loadSql() 메서드를 만들어 거기로 밀어넣는다. getResourcesAsStream으로 전달되는 매개변수는 당연히 **this.sqlmapFile**로 변경돼야 한다.

문제는 XmlSqlService 오브젝트는 빈이기 때문에 제어권이 스프링에 있다는 점이다.
이것은 XmlSqlService의 loadSql() 같은 초기화 메서드를 스프링에게 맡긴다는 것을 의미한다.
물론 스프링은 빈 오브젝트를 생성하고 DI 작업을 수행해서 프로퍼티를 주입해준 뒤 미리 저장된 초기화 메서드를 호출해주는 기능을 갖고 있다.
바로 **빈 후처리기**다.

빈 후처리기 설정 시 xml 파일에 context 스키마를 이용하면 편하다.(Java 9 미만일 때)

```xml
<beans xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation=
            "http://www.springframework.org/schema/context
             http://www.springframework.org/schema/spring-context-3.0.xsd">
```

<context:annotation-config /> 태그에 의해 등록되는 빈 후처리기는 몇 가지 특별한 빈 설정에 사용되는 애노테이션을 제공한다.
바로 **@PostConstruct**다.
이 애노테이션은 빈 오브젝트의 초기화 메서드를 지정하는 데 사용한다.

> **참고.**<br/>
> @PostConstruct는 Java 9부터 Deprecated 가 됐다.
> 그러므로 Java 9 이상부터는 maven이나 gradle 파일에 Javax Annotation API를 빌드해줘야 한다.<br/>
> https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api/1.3.2

![img.png](img.png)

## 7.2.4 변화를 위한 준비: 인터페이스 분리

현재 <code>XmlSqlService</code>는 특정 포맷의 XML에서 SQL을 가져오고, 이를 <code>HashMap</code> 타입의 맵 오브젝트에 저장해둔다.
하지만 SQL을 가져오는 것과 보관해두고 사용하는 것은 단일 책임 원칙에 의거하여 충분히 분리할 수 있다.

### 책임에 따른 인터페이스 정의

<code>XmlSqlService</code> 구현을 참고해서 독립적으로 변경 가능한 책임을 뽑아보자.

- SQL 정보를 외부의 리소스로부터 읽어온다.
- SQL을 보관해두고 있다가 필요할 때 제공해준다.

기본적으로 <code>SqlService</code>를 구현해서 DAO에 서비스를 제공해주는 오브젝트가 이 두 가지 책임을 가진 오브젝트와 협력해서
동작하도록 만들어야 한다.

![img_1.png](img_1.png)

<code>SqlReader</code>가 읽어오는 SQL 정보는 <code>SqlRegistry</code>에 전달해서 등록돼야 한다.
이 때 전달되는 형식을 JAXB에서 만들어준 Sql 클래스를 사용하는 건 좋지 않다.
이러면 특정 기술에 종속되기 때문이다.

그러므로 <code>SqlReader</code>가 가져오는 정보를 SQL과 키를 쌍으로 하는 배열을 만들고, 이를 다시 리스트에 담아서 가져오거나
맵을 이용할 수 있다.

```java
Map<String, String> sqls = sqlReader.readSql();
sqlRegistry.addSqls(sqls);
```

그런데 이 둘 사이에 정보를 전달하기 위한 목적으로 Map 타입의 형식이 필요하다는 것은 번거롭다.
그리고 전달 과정에서 일정한 포맷으로 변환하도록 강제하면 리스트 타입으로 데이터를 받을 때 이것을 또 변경시켜줘야 한다.

위의 방식보다는 `SqlReader`에게 `SqlRegistry` 전략을 제공해주면서 이를 이용해 SQL 정보를 `SqlRegistry`에 저장하라고
요청하는 편이 낫다.

```java
sqlReader.readSql(sqlRegistry)
```

```java
interface SqlRegistry{
    void registerSql(String key, String sql);
}
```

이렇게 하면 `SqlReader`가 직접 `SqlRegistry`에 SQL 정보를 등록할 수 있다.

![img_2.png](img_2.png)

`SqlReader`는 내부에 갖고 있는 SQL 정보를 `SqlRegistry` 에게 필요에 따라 등록을 요청할 때만 활용하면 된다.


### SqlRegistry 인터페이스

```java
public interface SqlRegistry {
    void registerSql(String key, String sql);
    String findSql(String key) throws SqlNotFoundException;
}
```

### SqlReader 인터페이스

`SqlReader` 는 `SqlRegistry` 오브젝트를 메서드 파라미터로 DI 받아서 읽어들인 SQL을 등록하는 데 사용하도록 만들어야 한다.

```java
public interface SqlReader {
    void read(SqlRegistry sqlRegistry);
}
```

## 7.2.5 자기참조 빈으로 시작하기

### 다중 인터페이스 구현과 간접 참조

`SqlService`의 구현 클래스는 이제 `SqlReader`와 `SqlRegistry` 두 개의 프로퍼티를 DI 받을 수 있는 구조로 만들어야 한다.
아래 사진처럼 말이다.

![img_3.png](img_3.png)

기존 `XmlSqlService` 클래스는 이 세 가지 책임을 구분 없이 하나의 클래스에 뭉뚱그려서 만들어놓았다.
이제 이것을 각 책임에 따라 분리해야 한다.

위 그림의 세 개의 인터페이스를 하나의 클래스가 전부 구현한다면 어떻게 될까?
정답은 "크게 상관없다"이다. 어차피 기존 `XmlSqlService`의 코드는 세분화해서 인터페이스를 정의하지 않았을 뿐이지,
이 세 가지 책임을 모두 갖고 있는 클래스였으니 이 세 개의 인터페이스를 구현하도록 만드는 것은 어렵지 않다.

![img_4.png](img_4.png)

### 인터페이스를 이용한 분리

일단 `XmlSqlService`는 `SqlService`만을 구현한 독립적인 클래스라고 생각한다.
그렇다면 `SqlRegistry`와 `SqlReader` 두 개의 인터페이스 타입 오브젝트에 의존하는 구조로 만들어야 한다.


### 자기 참조 빈 설정

<details>
<summary><b>XmlSqlService SqlReader 구현 코드 </b></summary>
<div markdown="1">

```java
public class XmlSqlService implements SqlService, SqlRegistry, SqlReader{
    private String sqlMapFile;
    public void setSqlMapFile(String sqlMapFile) {
        this.sqlMapFile = sqlMapFile;
    }

    @Override
    public void read(SqlRegistry sqlRegistry) {
        try{
            JAXBContext context = JAXBContext.newInstance(Sqlmap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStream is = UserDao.class.getResourceAsStream(this.sqlMapFile);
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

            for (SqlType sql : sqlmap.getSql()) {
                sqlRegistry.registerSql(sql.getKey(), sql.getValue());
            }
        }catch (JAXBException e){
            throw new RuntimeException(e);
        }
    }
}
```

</div>
</details>


<details>
<summary><b>XmlSqlService SqlRegistry 구현 코드 </b></summary>
<div markdown="1">

```java
public class XmlSqlService implements SqlService, SqlRegistry, SqlReader{
    private Map<String, String> sqlMap = new HashMap<>();

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlNotFoundException {
        String sql = sqlMap.get(key);
        if(sql == null) throw new SqlNotFoundException(key + "에 대한 SQL을 찾을 수 없습니다.");
        return null;
    }
}
```

</div>
</details>

### 자기참조 빈 설정

`XmlSqlService` 클래스 안에 혼재되어 있던 성격이 다른 코드를 세 가지 인터페이스를 구현하는 방법을 통해 서로 깔끔하게 분리해냈다.

- SQL을 읽을 때 : `SqlReader` 인터페이스
- SQL을 찾을 때 : `SqlRegistry` 인터페이스

이제 애플리케이션 컨텍스트에 다음과 같이 설정한다.

```xml
<bean id="sqlService" class="sqlservice.XmlSqlService">
    <property name="sqlRegistry" ref="sqlService" />
    <property name="sqlReader" ref="sqlService"/>
    <property name="sqlMapFile" value="sqlmap.xml"/>
</bean>
```

빈은 `sqlService` 하나만 선언했으므로 실제 빈 오브젝트도 한 개만 만들어진다.
스프링은 프로퍼티의 ref 항목에 자기 자신을 넣는 것을 허용한다.
이를 통해, `sqlService`를 구현한 메서드와 초기화 메서드는 외부에서 DI된 오브젝트라고 생각하고 결국 자신의 메서드에 접근한다.
인터페이스를 사용하고 DI를 이용하면 이렇게 특별한 구조까지도 유연하게 구성할 수 있다.

자기참조 빈은 흔히 쓰이는 방법은 아니다.
다만 책임과 관심사가 복잡하게 얽혀 있어서 확장이 힘들고 변경에 취약한 구조의 클래스를 유연한 구조로 만들려고 했을 때
처음 시도해볼 수 있는 방법이다.

## 7.2.6 디폴트 의존관계

### 디폴트 의존관계를 갖는 빈 만들기

특정 의존 오브젝트가 대부분의 환경에서 거의 "디폴트"라고 해도 좋을 만큼 기본적으로 사용될 가능성이 있다면,
디폴트 의존관계를 갖는 빈을 만드는 것을 고려해볼 필요가 있다.

디폴트 의존관계란 외부에서 DI 받지 않는 경우 기본적으로 자동 적용되는 의존관계를 말한다.

```java
public class DefaultSqlService extends BaseSqlService{
    public DefaultSqlService(){
        setSqlReader(new JaxbXmlSqlReader());
        setSqlRegistry(new HashMapSqlRegistry());
    }
}
```

```xml
<bean id="sqlService" class="sqlservice.DefaultSqlService"/>
```

`DefaultSqlService` 클래스를 생성해 `SqlReader`와 `SqlRegistry` 을 수동 DI 해줌으로써 빈 설정 파일의 코드 수는 줄어들게 됐다.
하지만 현재 `DefaultSqlService` 내부의 `JaxbXmlSqlReader` 의 `sqlMapFile` 프로퍼티는 비어있는 상태다.
문제는 `JaxbXmlSqlReader`를 디폴트 의존 오브젝트를 직접 넣어줄 때는 프로퍼티를 외부에서 직접 지정할 수 없다는 점이다.

이 문제를 해결할 수 있는 방법이 몇 가지 있다.

1. `sqlMapFile`을 `DefaultSqlService`의 프로퍼티로 정의한다.
하지만 디폴트라는 건 다른 명시적인 설정이 없는 경우에 기본적으로 사용하겠다는 의미다.
**따라서 이 방법은 좋지 않다.**
2. `JaxbXmlSqlReader`의 `sqlMapFile` 프로퍼티에 **디폴트 파일 이름을 넣어준다.**
아래 코드처럼 말이다.

```java
private static final String DEFAULT_SQLMAP_FILE = "/sqlmap.xml";
private String sqlMapFile = DEFAULT_SQLMAP_FILE;

public void setSqlMapFile(String sqlMapFile) {
    this.sqlMapFile = sqlMapFile;
}
```

`DefaultSqlService`는 `BaseSqlService`를 상속했기 때문에 언제든지 일부 또는 모든 프로퍼티를 변경할 수 있다.

#### 디폴트 의존 오브젝트의 단점

디폴트 의존 오브젝트를 사용하는 방법의 단점은 설정을 통해 다른 구현 오브젝트를 사용하게 해도
`DefaultSqlService`는 생성자에서 일단 디폴트 의존 오브젝트를 다 만들어버린다는 점이다.

```xml
<bean id="sqlService" class="sqlservice.DefaultSqlService">
    <property name="sqlRegistry" ref="ultraSuperFastSqlRegistry" />
</bean>
```

위의 xml 파일처럼 프로퍼티를 설정한다고 해도 이미 생성자에서 `HashMapSqlRegistry` 오브젝트가 만들어진다.
물론 프로퍼티로 설정한 빈 오브젝트로 바로 대체되긴 하겠지만 사용되지 않는 오브젝트가 만들어진다는 점이 꺼림칙하다.

만약 디폴트로 만드는 오브젝트가 매우 복잡하고 많은 리소스를 소모한다면 디폴트 의존 오브젝트가 아예 만들어지지 않게 하는 방법을 쓸 수 있다.
그 방법은 `@PostConstruct` 초기화 메서드를 이용해 프로퍼티가 설정됐는지 확인하고 없는 경우에만 디폴트 오브젝트를 만드는 방법이다.