# 목차

# 1. 개요

_java.util.concurrent_ 패키지는 동시 애플리케이션을 만들기 위한 도구들을 제공한다.

# 2. 메인 컴포넌트

- Executor
- ExecutorService
- ScheduledExecutorService
- Future
- CountDownLatch
- CyclicBarrier
- Semaphore
- ThreadFactory
- BlockingQueue
- DelayQueue
- Locks
- Phaser

이외에도 많은 유틸 컴포넌트가 있다.

## 2.1 Executor

> _Executor_ 는 제공된 태스크들을 실행하는 객체를 나타내는 인터페이스이다.

- 태스크가 신규 쓰레드 또는 현재 쓰레드에서 실행되어야 하는지 여부는 특정 위치에서 _Executor_ 를 구현한 객체에 따라 다르다.
- 따라서 _Executor_ 인터페이스를 이용해서 실제 태스크 실행 메커니즘에서 태스크 실행 흐름을 분리할 수 있다.

_Executor_ 는 태스크 실행이 비동기일 것을 엄격하게 요구하지 않는다.
가장 간단한 케이스로 _Executor_ 는 호출 쓰레드에서 제출된 태스크를 즉시 호출할 수 있다.

```java
public class Main {

	public static void main(String[] args) {
		Executor executor = new Invoker();
		executor.execute(() -> {
			System.out.println("Hello");
		});
	}
}
```

