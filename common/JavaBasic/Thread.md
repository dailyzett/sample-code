## 멀티 쓰레드

---

> 멀티 쓰레딩은 CPU 활용을 최대화하기 위해 프로그램의 두 개 이상의 부분을 동시에 실행할 수 있도록 하는 Java의 기능이다.
> 이러한 프로그램의 각 부분을 스레드(Thread)라고 한다.

스레드는 두 가지 구현 방식이 있다.

- Thread class 상속
- Runnable Interface 구현

---

### Thread class extends

Thread 클래스에서 사용할 수 있는 run() 메소드를 재정의한다. 그리고 해당 클래스의 객체 생성 후, start() 메소드를 호출하여 스레드를 실행.

```java
public class Example extends Thread {
    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Example ex = new Example();
            ex.start();
        }
    }
}
```

[Output]

```java
Thread 23 is running
Thread 25 is running
Thread 21 is running
Thread 22 is running
Thread 24 is running
Thread 18 is running
Thread 17 is running
Thread 19 is running
Thread 16 is running
Thread 20 is running
```

---

### Runnable Interface Implements

코드는 다음과 같다.

```java
public class Example implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Example());
            thread.start();
        }
    }
}
```

Runnable 인터페이스를 실행하려면 Thread 객체 인스턴스를 생성 후 이 객체에서 start() 메소드를 호출해야한다.

---
### Thread 클래스와 Runnable 인터페이스의 차이점

| 종류                                  | Thread | Runnable |
|:------------------------------------|:-------|:---------|
| 다중 상속 지원 여부                         | X      | O        |
| 내장 메소드 제공(yield(), interrupt() 등..) | O      | X        |


---

### deaemonThread

```java
Thread thread = new Thread(new Example());
thread.setDaemon(true);
```

다음 선언으로 데몬 쓰레드를 선언할 수 있다. <br>
데몬 쓰레드는 쓰레드가 수행 되기 전에 선언 되어야 데몬 쓰레드로 인식된다.

일반 쓰레드는 JVM이 해당 쓰레드가 끝날 때 까지 기다리지만, 데몬 쓰레드는 수행되고 있든 되지 않고 있든 상관 없이 JVM이 끝날 수 있다.

다시말해, 데몬 쓰레드는 해당 쓰레드가 종료되지 않아도 다른 일반 쓰레드가 실행중이 아니라면 멈춰버린다.

#### 데몬 쓰레드의 존재 이유?

예를 들어 모니터링 하는 쓰레드를 별도로 띄워 모니터링하다가, 주요 쓰레드가 종료되면 관련 쓰레드가 종료되어야 프로세스가 종료될 수 있다.
그런데 모니터링 쓰레드를 데몬 쓰레드로 만들어 놓지 않으면 프로세스를 종료할 수가 없게 된다.

이런식으로 부가적인 작업을 수행하는 쓰레드를 선언할 때 데몬 스레드가 사용된다.





