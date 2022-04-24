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



