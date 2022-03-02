## RESTful API

Restful API 를 이해하기 이전에 REST API 가 무엇인지 알아야한다.

REST 는 Representational state transfer 의 약자이다.

해당 어원은 2000년 Roy Fielding 이 박사 학위 논문에서 처음으로 소개했다. 이 용어는 잘 설계된 웹 애플리케이션이
어떻게 동작하는지에 대한 이미지를 불러일으키기 위한 것이다.

그리고 REST 는 Resource, Verb, Representations 세 개로 나뉜다.
- Resource : URI
- Verb : HTTP Method
- Representations

#### 역사

웹은 1993-4년에 일상적으로 사용되기 시작했다. 이 때 당시에는 웹 아키텍처에 대해 기준점이 없었기 때문에 표준을
만드는 것이 필요했다.

그래서 W3C 및 IETF 작업 그룹과 함께 웹의 세 가지 기본 표준인 URI, HTTP 및 HTML 에 대한 표준을 세웠다.
Roy Fielding 박사가 이러한 표준을 만드는 데 참여를 했으며 6년 동안 웹 프로토콜에 대한 제약 조건을 테스트하고
이를 정의하는 수단으로 사용하여 REST 아키텍처 스타일을 개발했다.



#### 제약 조건

REST API 는 다음과 같은 제약 조건이 있다.

- 클라이언트-서버 : 
  - 항상 클라이언트와 서버가 있으며 이 두 시스템의 작동 방식은 달라야한다. 서버는 호출되고, 클라이언트는 요청을 한다
- Stateless :
  - 서버는 수신한 메시지를 처리할 수 있어야한다. 이를 위해 서버가 수신하는 서버가 작동하는 데 필요한 필수 정보가 필요하다.
- 균일한 인터페이스 : 
  - 유사한 용어와 리소스를 사용하면 API 표준화에 도움이 된다. 따라서 GET,POST,PUT,DELETE 와 같은
  HTTP verb 가 사용된다. 리소스는 항상 URI 를 참조한다. HTTP 응답은 항상 status(상태) 및 body(본문)와 함께 제공된다.
- 캐시 사용이 가능해야한다.


#### RESTful API

REST 는 제약 조건의 집합이고 RESTful 은 이런 제약 조건을 준수하는 API 를 뜻한다.

