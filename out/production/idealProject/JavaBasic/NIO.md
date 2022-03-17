## NIO

---

### IO 와 NIO 의 차이

> 자바 IO는 스트림 지향 / 자바 NIO는 버퍼 지향이다.

- 자바 IO
  - 한 번에 하나 이상의 바이트를 읽을 수 있다.
  - 단방향 데이터 전송
  ![img](https://media.geeksforgeeks.org/wp-content/uploads/20200519194033/Javaio-2.png)

- 자바 NIO
  - 데이터가 채널을 사용하여 버퍼로 읽거나 쓴다.
  - 버퍼에 데이터를 읽으라고 요청하는 쓰레드와 채널이 버퍼를 이용해 데이터를 동시에 읽는 동안, 스레드는 다른 작업을 수행할 수 있다.
  - 데이터를 버퍼로 읽으면 쓰레드는 나머지 작업들을 계속 처리할 수 있다.
  - 따라서 NIO 는 양방향 전송이다.
  ![img](https://media.geeksforgeeks.org/wp-content/uploads/20200519194302/javanio-2.png)



> 자바 IO는 Blocking / 자바 NIO 는 Non-Blocking 이다.

- 자바 IO
  - 쓰레드가 read() write() 작업을 호출하는 경우 읽을 데이터가 있거나 데이터가 완전히 쓰여질 때 까지 해당 쓰레드가 Blocking 된다.

- 자바 NIO
  - read() 또는 write() 작업을 호출해도, 쓰레드가 다른 작업을 수행할 수 있다.


> NIO 에서 채널은 버퍼들 간의 효율적인 데이터 전송을 위한 중간 다리이다.
> 게이트웨이 역할을 한다.
---

### NIO 사용방법

---
```java
//readFile
private void readFile(String fileName) throws Exception{
        FileChannel channel = new FileInputStream(fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        channel.close();
}

//writeFile
private void writeFile(String fileName, String data) throws Exception{
    FileChannel channel = new FileOutputStream(fileName).getChannel();
    byte[] byteData = data.getBytes();
    ByteBuffer buffer = ByteBuffer.wrap(byteData);
    channel.write(buffer);
    channel.close();
}
```



---

### NIO 의 Buffer 클래스들

- capacity()
- limit()
- position()

> 관계 : 0 <= position <= limit <= capacity

| 리턴 타입   | 메소드            | 설명                                              |
|:--------|:---------------|:------------------------------------------------|
| Buffer  | flip()         | limit 값을 현재 position으로 지정, position 0으로 이동      |
| Buffer  | mark()         | 현재 position 에 mark                              |
| Buffer  | reset()        | 버퍼의 position을 mark 한 곳으로 이동                     |
| Buffer  | rewind()       | 현재 버퍼의 position을 0으로 이동                         |
| int     | remaining()    | limit-poistion 계산 결과 리턴                         |
| boolean | hasRemaining() | position 와 limit 값에 차이가 있을 경우 true 리턴           |
| Buffer  | clear()        | 버퍼를 지우고 현재 position 0으로 이동, limit 값을 버퍼의 크기로 변경 |


