# 0. 목차

- [0. 목차](#0-목차)
- [1. RESTful API](#1-restful-api)
  - [1.1 REST VS HTTP](#11-rest-vs-http)
  - [1.2 RESTful 하게 API를 디자인한다는 것의 의미](#12-restful-하게-api를-디자인한다는-것의-의미)


# 1. RESTful API

- _REST_ 는 Representatinal state transfer의 약어다.
- 한글말로 직역하면 **표현된 상태의 전송**이 된다.

> **Note.**  
> 클라이언트가 서버와 통신할 때 _리소스를 서버로부터 가져온다_ 라는 말은 틀린 말이다. 통신 과정에서 리소스는 항상 변한다. 그렇기 때문에 _계속해서 변하는 리소스의 현재 상태를 전송한다는 것_ 이 REST의 진짜 의미이다.

## 1.1 REST VS HTTP

- 사실 비교 자체가 틀린 전제다.
- HTTP는 _Hypertext Transfer Protocol_ 의 약자로 파일을 전송하는 방법을 뜻한다.
- REST는 확장 가능한 시스템을 보장하는 일련의 _제약 조건_ 이다.

> **Note.**  
> REST의 제약 조건에는 _HTTP를 전송 프로토콜로 사용해야 한다_ 라는 조건은 없다. SNMP, SMTP, FTP 같은 프로토콜을 사용해도 REST의 제약 조건을 준수하기만 하면 그 API는 RESTful API이다.

## 1.2 RESTful 하게 API를 디자인한다는 것의 의미

1. **리소스와 행위를 명시적이고 직관적으로 분리한다.**
     - 리소스는 URI로 표시되는데 리소스가 가리키는 것은 명사로 표현돼야 한다.
     - 행위는 HTTP Method로 표현하고 GET, POST, PUT, PATCH, DELETE를 분명한 목적으로 사용한다.

2. **Message는 Header와 Body를 명확하게 분리해서 사용한다.**
    - Entity에 대한 내용은 body에 담는다.
    - 서버가 판단할 정보의 근거가 되는 컨트롤 정보는 header에 담는다.

3. **API 버전을 관리한다.**
    - 특정 API를 변경할 때는 항상 하위호환성을 염두해둔다.

4. **서버와 클라이언트가 같은 방식을 사용해서 요청하도록 한다.**
    - 클라이언트가 JSON 형식으로 데이터를 보내고 받는다면 서버도 JSON 형식으로 보내고 받는다.




