## Socket 클래스

> TCP 통신을 사용하려면 Socket 클래스 이용

- 클라이언트 쪽에서 객체를 생성하여 사용
- 서버에서 클라이언트의 요청을 받으면 해당 Socket 객체 생성 후 데이터 처리
- 서버에서는 ServerSocket이라는 클래스를 사용


```java
//ServerSocket의 생성자
ServerSocket()
ServerSocket(int port)
ServerSocket(int port, int backlog)
ServerSocket(int port, int backlog, InetAddress bindAddr)
```

매개변수 설명
- port : 포트 번호
- backlog : 클라이언트 요청을 대기시킬 수 있는 최대 큐(Queue)의 수
- bindAddr : 특정 주소에서만 접근 가능하도록 지정할 때 사용


```java
//클라이언트 Socket의 생성자
Socket()
Socket(Proxy proxy)
...
Socket(String host, int port)
```

ServerSocket 과 Socket 클래스 모두 다음 두가지 메소드로 상태관리를 한다.
- accept() : 새로운 소켓 연결 대기, 연결이 되면 Socket 객체 리턴
- close() : 소켓 연결 종료


