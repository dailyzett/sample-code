## Datagram 클래스

> UDP 통신을 할 때 사용

```java
DatagramSocket()
DatagramSocket(DatagramSocketImpl impl)
DatagramSocket(int port)
DatagramSocket(int port, InetAddress address)
DatagramSocket(SocketAddress address)
```

데이터를 보낼 때,
```java
receive(DatagramPacket packet)
send(DatagramPacket packet)
```

이 있다.



