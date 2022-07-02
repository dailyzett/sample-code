## ServletContextListener

### 1. 개요

서블릿은 다양한 시점에서 발생하는 이벤트와 이벤트를 처리하기 위한 인터페이스를 정의하고 있다.
이들의 이벤트와 인터페이스를 이용하면 데이터 초기화나 요청 처리 등을 추적할 수 있다. 서블릿 규약은
다양한 이벤트 처리할 수 있는 인터페이스를 정의하고 있는데, 그 중 하나가 ServletContextListener이다.

### 2. ServletContextListener를 이용한 이벤트 처리

**웹 컨테이너**는 웹 애플리케이션이 시작되거나 종료되는 시점에 특정 클래스의 메서드를 실행할 수 있는 기능을
제공하고 있다. 이 기능을 사용해서 웹 애플리케이션을 실행할 때 필요한 **초기화**작업이나 웹 애플리케이션이 종료된 후
**사용된 자원을 반환**하는 등의 작업을 할 수 있다.

구현 순서는 다음과 같다.
1. ServletContextListener 인터페이스를 구현한 클래스를 작성한다.
2. web.xml 파일에 1번에 작성한 클래스를 등록한다.

ServletContextListener 인터페이스는 다음과 같은 메서드가 정의되어 있다.
참고로 default 메서드로 선언되어 있기 때문에 인터페이스를 구현한 클래스에서 상속하지 않아도 컴파일 에러는 나오지 않는다.
```java
public interface ServletContextListener extends EventListener {
    default public void contextInitialized(ServletContextEvent sce) {}
    default public void contextDestroyed(ServletContextEvent sce) {}
}
```

그리고 웹 애플리케이션이 실행되거나 종료될 때, 이 인터페이스를 구현한 클래스를 실행하려면 다음과 같이 web.xml 등록이 필요하다.
```xml
<listener>
    <listener-class>jdbc.DBCPInitListener</listener-class>
</listener>

<listener>
    <listener-class>Ex.CodeInitListener</listener-class>
</listener>
```

listener 태그는 한 개 이상 등록할 수 있고, 반드시 listener-class 태그를 자식 태그로 가져야한다.
listener-class는 이벤트를 처리할 리스너 클래스의 완전한 이름값을 갖는다. ServletContextListener
인터페이스 메서드는 **ServletContextEvent** 파라미터를 받는다. 이 클래스는 웹 애플리케이션 컨텍스트를
구할 수 있는 getServletContext() 메서드를 제공한다.

> 컨텍스트 관련 설명 링크
> <br>[컨텍스트가 뭐죠?](https://pflb.tistory.com/entry/Context%EA%B0%80-%EB%AD%90%EC%A3%A0)

getServletContext() 메서드를 이용해서 web.xml 파일의 설정된 컨텍스트 초기화 파라미터를 구할 수 있다.

```xml
<context-param>
    <param-name>poolConfig</param-name>
    <param-value>
        jdbcDriver=com.mysql.jdbc.Driver
        jdbcUrl=jdbc:mysql://localhost:3306/ex?characterEncoding=utf8
        dbUser=admin
        dbPass=1234
        poolName=guestBook
    </param-value>
</context-param>
```
&lt;param-value&gt;태그를 보면 **키=값** 형식의 문자열로 구성되어 있다.
web.xml 파일에 이렇게 설정되어 있을 때, 다음과 같은 방법으로 해당 값을 얻어올 수 있다.

```java
public class ExListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String poolConfig = sce.getServletContext().getInitParameter("poolConfig");
        Properties props = new Properties();
        try{
            props.load(new StringReader(poolConfig));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadJDBCDriver(props);
        initConnectionPool(props);
    }

    private void initConnectionPool(Properties props) {
        String jdbcUrl = props.getProperty("jdbcUrl");
        String username = props.getProperty("dbUser");
        String pw = props.getProperty("dbPass");
        
        // TODO...
    }

    private void loadJDBCDriver(Properties props) {
        String driverClass = props.getProperty("jdbcDriver");
        try{
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
```
web.xml에 있는 &lt;param-name&gt; 태그값을 읽어와서 Properties 객체에 로드한다. 그리고 Properties의 인스턴스인
props를 **loadJDBCDriver**, **initConnectionPool** 메서드의 파라미터로 넘긴다. 각 메서드는 Properties 객체를 파라미터로 받아서
web.xml의 &lt;param-value&gt; 태그값을 읽어와 역할에 맞는 행동을 한다. initConnectionPool 메서드의 **TODO** 부분에는 커넥션 풀의 초기 설정을 해야하는 코드가 더 들어가야한다.

#### 2.1 리스너의 실행 순서

웹 애플리케이션에는 한 개 이상의 리스너를 web.xml 에 등록할 수도 있다. 만약 한 개 이상의 리스너가 등록됐을 경우,
contextInitialized() 메서드는 순서대로 실행되고 contextDestroyed() 메서드는 반대로 실행된다.

#### 2.2 리스너에서의 예외 처리

contextInitialized() 메서드 정의에는 throws가 없다. 이 말은, 이 메서드는 발생시킬 수 있는
checkedException이 존재하지 않는다는 것을 뜻한다. 따라서 예외를 발생시키려면 RuntimeException이나
그 하위 타입의 예외를 발생시켜야한다.

#### 2.3 애노테이션을 이용한 리스너 등록

서블릿 3.0버전 부터는 리스너도 마찬가지로 web.xml 설정 대신, 
**@WebListener** 애노테이션을 이용해서 등록이 가능하다.

```java
@WebListener
public class AnnotationListener implements ServletContextListener{
    //TODO
}
```