## 필터

### 1. 필터란 무엇인가?

> HTTP 요청과 응답을 변경할 수 있는 재사용 가능한 클래스

필터는 클라이언트와 리소스 사이에 위치한다. 그래서 실제 리소스가 받는 요청 정보는 필터가
변경한 요청 정보가 되며, 클라이언트가 보는 응답 정보는 필터가 변경한 응답 정보가 된다.

그림으로 표현하면 다음과 같다.

![img1](https://media.vlpt.us/images/dailyzett/post/2b638b45-24f8-4609-82a0-5d15e7f1d1df/Untitled%20Diagram.jpg)

한 개의 필터만 존재할 수 있는게 아니며, 여러 개의 필터가 모여 필터 체인을 형성할 수도 있다.
이런 경우, 첫 번째 필터가 받는 정보는 클라이언트의 요청 정보가 되지만 두 번째 필터가 변경하는 요청
정보는 첫 번째 필터를 통해서 변경된 요청 정보가 된다.

![img2](https://media.vlpt.us/images/dailyzett/post/8989245b-0678-4c64-8636-41c52d3dd1a4/Untitled%20Diagram%20(1).jpg)

필터의 이러한 기능은 사용자 인증이나 권한 검사와 같은 기능을 구현할 때 용이하게 사용할 수 있다.

### 2. 필터 구현

필터를 구현하는 데 있어서 핵심은 다음의 3개 타입이다.

- javax.servlet.Filter
- javax.servlet.ServletRequestWrapper
- javax.servlet.ServletResponseWrapper

#### 2.1 필터 인터페이스

javax.servlet.Filter 인터페이스를 구현하면 다음과 같은 메서드를 재정의해야한다.

```java
import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) 
            throws IOException, ServletException {
        
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
```

클라이언트가 요청한 리소스가 필터를 거치는 경우 doFilter() 메서드는 항상 실행된다. doFilter 메서드의 세 번째 파라미터로 FilterChain이 있는데,
FilterChain을 이용해서 체인에 있는 다음 필터에 변경한 요청과 응답을 전달할 수 있다. Filter 메서드 내부의 실행 순서는 다음과 같다.

- request 파라미터를 이용해서 클라이언트의 요청을 필터링한다.
- chain.doFilter()를 호출한다.
- response 파라미터를 이용해서 클라이언트의 응답을 필터링한다.

init() 메서드에 전달되는 FilterConfig는 필터의 초기화 파라미터를 읽어올 때 사용한다.

#### 2.2 필터 설정하기 : web.xml

필터를 적용하기 위해 web.xml의 **&lt;filter&gt; 태그**와 **&lt;filter-mapping&gt;** 태그를 사용하면 된다.

```xml
<filter>
    <filter-name>FilterName</filter-name>
    <filter-class>javaFilter.filter.FileClass</filter-class>
    <init-param>
        <param-value>paramName</param-value>
        <param-value>value</param-value>
    </init-param>
</filter>

<filter-mapping>
    <filter-name>FilterName</filter-name>
    <url-pattern>*.jsp</url-pattern>
</filter-mapping>
```

- &lt;filter&gt; 태그는 웹 애플리케이션에서 사용할 필터를 지정한다.
  - &lt;filter&gt; 태그의 &lt;init-param&gt; 태그는 init() 메서드를 호출할 때 전달할 파라미터를 설정한다.
- &lt;filter-mapping&gt; 태그는 특정 리소스에 대해 어떤 필터를 적용할 지를 지정한다.
  - &lt;url-pattern&gt; 태그는 클라이언트가 요청한 특정 URI에 대해서 필터링할 때 사용된다. 서블릿 url-pattern과 동일한 규칙이다.

&lt;url-pattern&gt; 태그를 사용하지 않고 &lt;servlet-name&gt; 태그를 사용하면 특정 서블릿에 대한 요청에 대해서
필터를 적용한다.

```xml
<filter-mapping>
    <filter-name>AutoCheckFilter</filter-name>
    <servlet-name>FileDownload</servlet-name>
</filter-mapping>
```

이름이 FileDownload인 서블릿에 대해서 AutoCheckFilter를 필터로 사용하도록 할 수 있다.

&lt;dispatcher&gt; 태그를 이용해서 필터가 적용되는 시점을 설정할 수 있다. 이 태그는
&lt;filter-mapping&gt; 태그의 자식 태그로 사용된다. &lt;dispatcher&gt; 태그가 가질 수 있는 값은
세 가지다.

- REQUEST : 클라이언트의 요청인 경우 필터 적용(기본값)
- FORWARD : forward()를 통해서 제어 흐름을 이동하는 경우 필터 적용
- INCLUDE : include()를 통해서 포함되는 경우 필터 적용

#### 2.3 필터 설정하기 : @WebFilter 애노테이션

web.xml에 등록하지 않아도 @WebFilter 애노테이션이 붙은 클래스는 자동으로 필터로 등록된다.

```java
@WebFilter(filterName = "xsltFilter", urlPatterns = {"/xml/*"})
public class XSLTFilter implements Filter{
    //TODO
}
```

@WebFilter 애노테이션의 주요 속성은 다음과 같으며 web.xml에서 사용했던 태그의 내용과 같다.

- urlPattern
- servletNames
- filterName
- initParams
- dispatcherTypes

#### 2.4 요청 및 응답 래퍼 클래스

ServletRequestWrapper 와 ServletResponseWrapper 클래스를 말한다.
필터가 제 기능을 하려면 클라이언트의 요청을 변경하고 클라이언트로 가는 응답을 변경할 수 있어야 한다.
서블릿의 요청 래퍼와 응답 래퍼를 만들려면 javax.servlet에 정의되어 있는 클래스를 상속받아야 한다.
대부분 필터는 http 프로토콜에 대한 요청과 응답을 필터링하기 때문에 이 두 클래스를 상속받아 알맞게 구현한
HttpServletRequestWrapper와 HttpServletResponseWrapper 클래스를 상속받아 구현하는 것이 좋다.

필터를 통해서 변경하고 싶은 요청 정보가 있다면 HttpServletRequestWrapper 클래스를 상속받은 클래스를
만든 뒤, 그 정보를 추출하는 메서드를 알맞게 재정의해서 변경한 정보를 제공하도록 구현한다. 그리고 구현한
래퍼 클래스의 객체를 doFilter() 메서드에 전달하면 된다.

