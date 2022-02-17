## 동기화

---

> 멀티 스레드 프로그램에서 다중 스레드가 동일한 리소스에 접근한다면 예측하지 못한 결과나 예외가 발생할 수 있다.
> 따라서 특정 시점에 하나의 스레드만 리소스에 접근할 수 있도록 해야하는데, 이것이 동기화이다.


다음은 동기화 블록의 일반적인 형식이다

```java
synchronized(sync_object)
{
   //TODO
}
```


synchronized 는 모니터라는 개념으로 Java에서 구현된다.

- 할당된 시간에 하나의 스레드만 모니터를 소유한다.
- 스레드가 lock 상태가 되면 모니터에 들어갔다고 표현
- 다른 스레드가 모니터에 접근하려면 스레드가 모니터를 종료해야 가능


synchronized 블록을 항상 전체 메소드로 지정할 필요는 없다. 전체 메소드를 동기화하는 경우,
동기화가 필요없는 나머지 부분이 실행될 때 까지 다른 스레드가 접근하지 못하므로 성능이 떨어질 수 있다.
따라서 경우에 따라 메소드의 일부만 동기화 하는 것이 좋다.

---

### 동기화 메소드

동기화에 사용되는 중요한 메소드

- wait()
- notifyAll()
- notify()

```java
synchronized(instance)
```
만약 instance 가 null 이면 NullPointerException 발생

> **Tip** 자바 synchronized 키워드는 변수에 적용할 수 없다.


----

### Java 에서의 Thread Safety

- synchronized
- Volatile
- Atomic 변수
- Final Keyword


2번부터 설명하면, volatile 은 다음과 같이 선언한다

```java
static volatile int a = 0;, b = 0;
```

Atomic은 다음과 같다.

```java
AtomicInteger count = new AtomicInteger();
    
public void increment(){
    count.incrementAndGet();
}
```

final 키워드는 한 번 선언해 놓으면 변경이 불가능하다.<br>
따라서 쓰레드가 접근하려할 때 컴파일 에러가 나오게 되기 때문에 thread-safe 하게 이용 가능하다.

```java
 final String str = new String("final");
```