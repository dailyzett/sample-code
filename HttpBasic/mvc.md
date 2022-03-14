## MVC 패턴

### 1. 모델 2 구조와 MVC 패턴

JSP 기준으로 웹 애플리케이션은 모델 1 구조와 모델 2 구조로 나뉜다. JSP에서 모든 로직을 처리하는 방식을
모델 1이라고 부르고 단순히 출력만 처리하는 방식을 모델 2라고 부른다.

#### 1.1 모델 1 및 모델 2 구조

[모델1 그림]

![Model 1](https://media.vlpt.us/images/jsj3282/post/98b1ff34-0a41-4c2e-aaba-81912eea9aee/245FDF385833E3DF17.png)

모델 1 그림을 보면 웹 브라우저의 요청과 응답을 전부 JSP에서 담당하고 있다. 웹 브라우저의 요청을 받은 JSP는
자바빈이나 서비스 클래스를 이용해서 응답을 전송한다. 즉, 모델 1은 비즈니스 로직과 출력하는 코드가 뒤섞여
있다.

[모델2 그림]

![Model 2](https://media.vlpt.us/images/jsj3282/post/486656f0-2c3e-438a-aed1-c5b77be1c78a/2756B2425833E71205.png)

모델 2는 웹 브라우저의 요청을 하나의 서블릿이 받아 처리한다. 서블릿은 요청을 알맞게 처리해서 JSP 페이지로
포워딩 작업을 거친다. 포워딩을 통해 요청 흐름을 받은 JSP는 결과 화면을 클라이언트에게 전송한다.

하나의 서블릿이 웹 브라우저의 모든 요청을 받기 때문에, 서블릿은 웹 브라우저의 요청을 구분할 방법이 필요하다.
서블릿은 웹 브라우저의 요청을 받은 후 응답 화면을 생성할 JSP 페이지를 선택한다. 모델 2 구조의 이런 특징 때문에,
MVC 패턴을 이용해서 웹 애플리케이션을 구현할 때 모델 2 구조를 사용한다.

#### 1.2 MVC 패턴

MVC는 웹 애플리케이션을 구현할 때 모델, 뷰 및 컨트롤러 세 가지 영역으로 나뉜 것을 뜻한다. 각
영역이 하는 일은 다음과 같다.

- 모델 : 비즈니스 로직 처리를 담당한다.
- 뷰 : 비즈니스 영역에 대한 사용자가 보게 될 화면을 담당한다.
- 컨트롤러 : 사용자의 입력 처리와 흐름 제어를 담당한다.

![MVC](https://media.vlpt.us/images/jsj3282/post/0793800e-323c-40e4-bf97-a4f9f80fbbc7/257CD4435833E8B513.png)

사진을 보면 모델 2와 명칭이 다른 것 빼곤 구조는 매우 흡사하다. 클라이언트가 요청을 보내면 그것을 컨트롤러에서
받아 요청에 따라 모델에서 비즈니스 로직을 처리하고, 알맞은 뷰를 선택해 최종적으로 결과 화면을 사용자에게 보여준다.
MVC 패턴은 중요 역할을 하는 부분이 나뉘어져 있기 때문에, 필요 부분만 변경하면 되고 코드의 가독성도 증가하기 때문에
대규모 웹 애플리케이션 구축 시 유지 보수와 협업이 상당히 쉬워진다는 점이 장점이다.

#### 1.3 MVC 패턴과 모델 2 패턴

MVC 패턴과 모델 2 패턴을 매핑시키면 다음과 같다.

- 컨트롤러 = 서블릿
- 모델 = 로직 처리 클래스, 자바빈
- 뷰 = JSP
- 사용자 = 웹 브라우저 혹은 스마트폰 같은 다양한 기기

#### 1.4 MVC 컨트롤러 : 서블릿

컨트롤러에 해당하는 서블릿의 작업 순서를 상세히 살펴보자.

1. 먼저 웹 브라우저가 전송한 HTTP 요청을 받는다. HTTP 메서드에 따라 서블릿의 doGet() 혹은 doPost() 메서드가 호출된다.(다른 HTTP 메서드도 동일)
2. 웹 브라우저가 어떤 기능을 요청했는지 분석한다.
3. 모델을 사용하여 기능에 따른 로직을 수행한다.
4. 모델로부터 전달받은 결과를 받아, request 나 session의 setAttribute() 메서드를 이용해서 결괏값을 속성에 저장한다.
5. 웹 브라우저에 결과를 전송할 JSP를 선택한 후, 해당 JSP로 포워딩한다. 경우에 따라 리다이렉트를 하기도 한다.

#### 1.5 MVC 뷰  : JSP

뷰는 웹 브라우저가 지속적으로 컨트롤러에 요청을 보낼 수 있는 링크나 폼을 제공해서 웹 브라우저가
업무 흐름에 따라 컨트롤러에 알맞은 요청을 보낼 수 있도록 한다.

#### 1.6 MVC 모델

모델은 비즈니스 로직을 처리해주는 부분이다. 예를 들어 계좌 이체 기능을 요청했다면 모델은 계좌 이체를
진행시켜주는 기능을 제공해야 하며, 컨트롤러는 웹 브라우저의 요청이 들어오는 경우 모델의 계좌 이체 기능을
사용하게 된다.

### 2. MVC 패턴의 구현

#### 2.1 커맨드 패턴 기반의 코드

앞서 서블릿이 해야할 두번째 작업은 사용자가 어떤 기능을 요청했는지 분석하는 것이었다. 이 요청을 분석하는
가장 간단한 방법은 if.. else 문을 이용하는 것이다.

```java
String command = request.getParameter("cmd");
if(command == null){
// 명령어 오류 처리
} else if(command.eqauls("BoardList")){
// 글목록 읽기 처리
}
```

이 코드의 단점은 로직 처리가 복잡해지면 그만큼 코드도 길어지기 때문에 가독성이 상당히 떨어진다. 이런 단점을
해결하기 위해서는 커맨드 패턴을 이용해야 한다.

#### 2.2 커맨드 패턴을 이용한 명령어 처리기 분리

커맨드 패턴은 하나의 명령어를 하나의 클래스에서 처리하도록 구현하는 패턴이다. 커맨드 패턴을 이용하면 위의 코드를
다음과 같이 변경 가능하다.

```java
String command = request.getParameter("cmd");
CommandHandler handler = null;

if (command == null){
    handler = new Handler();    
}else if(command.equals("BoardList")){
    handler = new BoardListHandler();    
}
...
```

CommandHandler는 인터페이스이고 BoardListHandler는 이 인터페이스를 상속받는 구현체이다. 즉, 컨트롤러 서블릿은
명령어에 해당하는 CommandHandler 인스턴스를 생성하고, 실제 로직의 처리는 생성한 인스턴스에서 실행되는 구조가 된다.
CommandHandler 인터페이스는 다음과 같이 구현할 수 있다.

```java
public interface CommandHandler{
    public String process(HttpServletRequset request, HttpServletResponse response) throws Exception;
}
```

```java
//CommandHandler를 구현한 일반 클래스
public SomeHandler implements CommandHandler{
    public String process (HttpServletRequset request, HttpServletResponse response) throws Exception{
        // 1. 명령어와 관련된 비즈니스 로직 처리
        
        // 2. 뷰 페이지에서 사용할 정보 저장
        
        // 3. 뷰 페이지의 URI 리턴
    }
}
```

#### 2.3 설정 파일에 커맨드와 클래스의 관계 명시

로직 처리 코드를 컨트롤러 서블릿에서 핸들러 클래스로 옮겼지만 여전히 컨트롤러에서는 if-else 문을 사용하고 있다.
이 코드는 새로운 명령어가 추가되면 컨트롤러 서블릿 클래스의 코드를 직접 변경해야하는 단점이 있다. 이 단점을
해결하는 방법은 <명령어, 핸들러 클래스>의 매핑 정보를 설정 파일에 저장하는 것이다.

```java
BoardList = mvjsp.command.BoardListHandler
BoardWriteForm = mvjsp.command.BoardWriteFormHandler
```

설정 파일의 한 줄은 **명령어 = 핸들러 클래스** 이름으로 구성된다. 컨트롤러 서블릿은 설정 파일에서
명령어와 핸들러 클래스의 매핑 정보를 읽어와 명령어에 해당하는 핸들러 클래스 객체를 미리 생성해두었다가
process() 메서드에서 사용하면 된다. 컨트롤러에서 설정 파일을 읽어오기 가장 좋은 위치는 init() 메서드이다.

```java
public class ControllerUsingFile extends HttpServlet {
    private Map<String, CommandHandler> commandHandlerMap = new HashMap<>();

    public void init() throws ServletException{
        //설정 파일로부터 configFile 을 불러와 Properties 객체에 저장한다.
        String configFile = getInitParameter("configFile");
        Properties prop = new Properties();
        String configFilePath = getServletContext().getRealPath(configFile);
        try (FileInputStream fis = new FileReader(configFilePath)) {
               prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Properties 에 저장된 각 프로퍼티 키를 순회한다.
        Iterator<Object> keyIter = prop.keySet().iterator();
        while (keyIter.hasNext()) {
            String command = (String) keyIter.next();
            //프로퍼티 이름을 커맨드 이름으로 사용한다.
            String handlerClassName = prop.getProperty(command);
            try{
                //핸들러 클래스 이름을 통해 Class 객체를 구한다.
                Class<?> handlerClass = Class.forName(handlerClassName);
                // Class 로부터 핸들러 객체를 생성한다.
                CommandHandler handlerInstance = (CommandHandler) handlerClass.newInstance();
                // 핸들러 객체를 commandHandlerMap에 저장한다.
                commandHandlerMap.put(command, handlerInstance);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) {
        // 클라이언트가 요청한 명령어를 구한다.
        String command = request.getParameter("cmd");
        CommandHandler handler = commandHandlerMap.get(command);
        if (handler == null) {
            //TODO
        }
        String viewPage = null;
        try{
            viewPage = handler.process(request, response);
        }catch(...){
            ...
        }
        if(viewPage != null){
            RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
            dispatcher.forward(request, response);
        }
    }
}
```

위 코드는 핸들러 객체를 구하기 위해 if-else 블록을 사용하지 않는다. if-else 문은 다음의 코드로
간단하게 변경되었다.
```java
CommandHandler handler = commandHandlerMap.get(command);
```

그리고 handler.process(request, response)를 보면 각 로직 코드는 핸들러 객체에서 처리한다.
configFile 초기화 파라미터를 사용하기 때문에 web.xml 에 **init-param** 태그를 통해 설정 파일 경로를
지정해야한다.

```xml
<servlet>
    <init-param>
        <param-name>configFile</param-name>
        <param-value>WEB-INF/commandHandler.properties</param-value>
    </init-param>
</servlet>
```

그리고 commandHadler.properties 파일은 다음과 같이 작성하면 된다.

```properties
hello=mvc.hello.HelloHandler
#someCommand=any.SomeHandler
```
properties 파일에서 '#'이 앞에 붙으면 해당 라인은 주석으로 처리한다.
그리고 mvc.hello.HelloHandler는 패키지를 포함한 클래스 파일이다. 당연히 없으면 안되므로
작성이 필수이다.

```java
public class HelloHandler implements CommandHandler {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response){
        ...
    }
}
```

#### 요청 URI를 명령어로 사용하기

명령의 기반의 파라미터는 URL이 사용자에게 그대로 노출된다는 것이 단점이다.
예를 들어 "http://localhost:8080/chap18/controllerUsingFile?cmd=hello"
이런 식인데, 사용자는 이 명령어를 수동으로 변경해서 컨트롤러에 요청을 시도할 수 있다. 이런 불필요한
것들을 방지하려면 요청 URI 자체를 명령어로 사용하는 것이 좋다.

위의 process() 메서드에서 request.getParameter("cmd") 코드를 다음과 같이 변경하면 된다.

```java
String command = request.getParameterURI();
if(command.indexOf(request.getContextPath()) == 0){
    command = command.substring(request.getContextPath().length());
}
```

request.getContextPath 부분을 제거하는 이유는 웹 애플리케이션 내에서의 요청 URI만을 사용하기 위함이다.
예를 들어, http://localhost:8080/example/guest/list.do 가 있다고 가정해보자.
해당 전체 요쳥 URI는 "/example/guest/list.do" 이지만, 웹 애플리케이션 경로를 제외한 나머지 URI는
"/guest/list.do" 가 된다.

여기서 *.do 확장자를 이용해 처리했는데, 특정 확장자를 가진 요청을 컨트롤러 서블릿에서 처리하도록 하려면
web.xml에 servlet-mapping 정보를 추가해주어야 한다.

```xml
<servlet-mapping>
    <servlet-name>ControllerUsingURI</servlet-name>
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```

이제 *.do로 오는 요청은 servlet-name 컨트롤러 서블릿으로 전달된다. 요청 URI를 명령어로 사용하는
ControllerUsingURI에 알맞는 설정 파일은 다음과 같이 작성하면 된다.

```properties
/hello.do=mvc.hello.HelloHandler
```

