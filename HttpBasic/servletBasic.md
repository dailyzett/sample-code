## 서블릿 기초

### 1. 개요

> 서블릿은 JSP 표준이 나오기 전에 만들어진 표준으로, 자바에서 웹 애플리케이션을 개발할 수 있도록 하기 위해
> 만들어졌다. 서블릿을 이용해서 자바 언어로 웹 애플리케이션을 개발할 수 있다.

다음은 서블릿 개발 과정이다.

1. 서블릿 규약에 따라 자바 코드를 작성
2. 자바 코드를 컴파일해서 클래스 파일을 생성
3. WEB-INF/classes 폴더에 클래스 파일을 패키지에 알맞게 위치시킴
4. web.xml 에 서블릿 클래스를 설정
5. 톰캣 등의 컨테이너 실행
6. 웹 브라우저에서 확인

### 2. 서블릿 구현

```java
public class HelloServlet extends HttpServlet{
    //TODO
}
```

서블릿 클래스는 HttpServlet 클래스를 상속받아서 구현한다. 그리고 사용하고자 하는 HTTP 메서드에 따라 
알맞은 매서드를 재정의해서 사용하면 된다. 예를 들어 GET 메서드에 해당하는 doGet() 메서드는 HttpServletRequest와
HttpServletResponse를 매개변수로 받는다. 이 매개변수는 각각 request와 response의 기본 객체에 해당한다.

재정의한 메서드는 request로 요청 정보를 읽어오거나 response로 응답을 전송할 수 있다. response로 전송하는
경우, response.setContentType()으로 응답의 컨텐츠 타입을 지정해야한다.

#### 2.1 web.xml 매핑

서블릿 클래스를 생성했다면 web.xml에 등록해야한다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <servlet>
        <!-- 해당 서블릿을 참조할 때 사용하는 이름 -->
        <servlet-name>now</servlet-name> 
        <!-- 서블릿으로 사용할 클래스의 완전한 이름 -->
        <servlet-class>com.demo.servletdemo.NowServlet</servlet-class>
    </servlet>
    
    <!--해당 서블릿이 어떤 URL 을 처리할 지 결정하는 태그-->
    <servlet-mapping>
        <!--매핑할 서블릿 이름 지정-->
        <servlet-name>now</servlet-name>
        <!--매핑할 URL 패턴 지정-->
        <url-pattern>/now</url-pattern>
    </servlet-mapping>
</web-app>
```
#### 2.2 @WebServlet 애노테이션

servlet 3.0 버전부터 @WebServlet 애노테이션을 이용해 매핑할 수 있다.

```java
@WebServlet(urlPatterns = "/now-servlet")
```

#### 2.3 web.xml VS @WebServlet

@WebServlet을 사용하는 경우 서블릿이 처리해야 할 URL 패턴이 변경되면, 자바 소스 코드의 urlPatterns
속성값을 변경하고 다시 컴파일해야 한다. 반면 web.xml 파일은 URL 경로가 바뀔 경우 web.xml 파일만 변경하면
된다. 따라서, 서블릿의 용도에 따라서 애노테이션을 사용할지 web.xml 파일을 사용할지를 선택하면 된다.

#### 2.5 각 방식별 구현 메서드 

HTTP는 GET, POST, HEAD, PUT, DELETE을 포함한 다양한 메서드를 지원하고 있다. 그리고 HTTP 메서드에 따른 서블릿 메서드는
다음과 같다.

![img](https://media.vlpt.us/images/dailyzett/post/7bbf847d-b1cd-4ba4-9c2e-0bd40a5dd083/image.png)

#### 2.6 서블릿 로딩과 초기화

서블릿 컨테이너는 서블릿을 최초 생성할 때 서블릿 객체를 생성한다. 이후 요청이 반복되면 앞서 생성한
서블릿 객체를 그대로 사용한다. 서블릿 객체를 생성 후 init() 메서드가 실행되는데, 이것을 **서블릿 로딩**
과정이라고 한다. init() 메서드의 기본 구현은 다음과 같다.

```java
public void init(ServletConfig config) throws ServletException{
    this.config = config;
    this.init();
}
        
public void init() throws ServletException{
}
```
서블릿 컨테이너는 서블릿을 초기화하기 위해 ServletConfig 매개변수를 갖는 init() 메서드를 실행하고, 이
메서드는 매개변수가 없는 init() 메서드를 다시 호출한다. 초기화 작업은 시간이 오래 걸리기 때문에 서블릿을
처음 사용하는 시점보다는 웹 컨테이너를 처음 구동하는 시점에 초기화하는 것이 좋다. 이를 위한 설정이 &lt;load-on-startup&gt;이다.
 예를 들어, DBCP를 초기화하기 위해 web.xml에 다음 태그를 추가할 수 있다.

```xml
<servlet>
    <servlet-name>DBCPInit</servlet-name>
    <servlet-class>jdbc.DBCPInit</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
```

해당 태그를 사용하면 웹 애플리케이션을 시작할 때 서블릿을 로딩한다. 즉 위의 코드는 톰캣을 구동하는 시점에
DBCPInit 서블릿 객체를 생성하고 init() 메서드를 실행한다. 참고로 load-on-startup은 값을 기준으로 오름차순으로
서블릿을 로딩한다.

@WebServlet 애노테이션은 다음과 같이 loadOnStartup 속성값을 이용해 설정한다.
```java
@WebServlet(name = "nowServlet", value = "/now-servlet", loadOnStartup = 1)
```

#### 2.7 초기화 매개변수

JDBC를 사용할 때 jdbcUrl이나 username, pw 등등의 필드가 자바 소스 코드로 작성되어 있으면,
해당 값이 변경될 때마다 소스 코드를 직접 변경하고 컴파일해야 한다. 서블릿은 코드를 직접 변경하지 않고
사용할 값을 변경할 수 있는 방법을 제공하는데 그 방법은 초기화 매개변수를 이용하는 것이다.

```xml
<servlet>
    <servlet-name>now</servlet-name>
    <servlet-class>com.demo.servletdemo.NowServlet</servlet-class>
    <init-param>
        <param-name>jdbcDriver</param-name> <!--이름을 설정한다-->
        <param-value>com.mysql.jdbc.Driver</param-value> <!--값을 설정한다-->
    </init-param>
</servlet>
```

서블릿 클래스에서 초기화 매개변수에 접근하려면 getInitParameter() 메서드를 사용하면 된다.

```java
String url = getInitParameter("jdbcUrl");
String id = getInitParameter("id");
```

web.xml에 init-param 태그를 이용해서 초기화하는 것의 이점은 혹시나 DB접속에 필요한 url, id 및 pw가
변경되어도 서블릿을 변경할 필요 없이 web.xml 파일의 매개변수 값만 변경해주면 된다는 점이다. 예를 들어 패스워드가
변경되었을 때 pw의 초기화 매개변수 값만 수정 후, 톰캣을 재시작하면 된다.

getInitParameter() 메서드는 지정한 초기화 매개변수가 존재하면 해당 값(param-value)를 리턴하고 아니면
null을 리턴하므로, null 여부를 확인하는 것은 필수이다. @WebServlet 애노테이션에도 initParams 속성이 있지만, 애노테이션은 자바 소스 코드 파일을 직접 수정해야한다는 점에서
사용하는 의미가 퇴색된다. 따라서 웹 애플리케이션 실행 시 반드시 초기화 해야하는 컨테이너가 있다면, web.xml 파일에
작성하는 것이 유연성을 증대시킨다.

### 3. URL 패턴 매핑 규칙

서블릿 규약에 따르면 URL 패턴은 다음 규칙에 따라 서블릿을 매핑한다.

- '/'로 시작하고 '/*'로 끝나는 url-pattern은 **경로 매핑**을 위해서 사용
- '*.'로 시작하는 url-pattern은 **확장자에 대한 매핑**을 할 때 사용
- 오직 '/'만 포함하는 경우 애플리케이션의 기본 서블릿으로 매핑
- 이 규칙 외, 나머지 다른 문자열은 정확한 매핑을 위해서 사용






