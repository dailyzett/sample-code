## Thread Lifecycle

---

Java의 쓰레드는 다음 상태 중 하나를 가진다.

- New
- Runnable
- Blocked
- Waiting
- Timed Waiting
- Terminated

![lifecycle](https://media.geeksforgeeks.org/wp-content/uploads/threadLifeCycle.jpg)

---

**1. New Thread**

> Thread 가 새로 생성되면 New 상태가 된다. Thread가 이 상태에 있을 때는 아무것도 실행되지 않은 상태이다.

**2. Runnable State**

> 실행할 준비가 된 Thread가 실행 가능 상태(Runnable State)로 변경된다. 이 상태에서는 스레드가
> 실행 중이거나 언제든지 실행할 준비가 되어있다는 것을 뜻한다.

- 스레드의 실행 시간을 담당하는 것은 스레드 스케줄러
- 멀티 스레드는 개별 스레드에 고정된 시간을 할당

**3. Block/Waiting State**

> 스레드가 일시적으로 비활성화 되는 상태. 다음 두가지 상태가 있다.
- Block
- Waiting

**4. Timed Waiting**

> 지정된 대기시간까지 다른 스레드가 작업을 수행하기를 기다리고 있는 상태이다.

**5. Terminated**

> 정상적으로 실행이 끝났을 때 또는 Exception 이 발생해서 비정상적인 이벤트가 발생했을 때 스레드는 종료상태가 된다.




