# 목차

- [목차](#목차)
- [1. 개요](#1-개요)
- [2. 메인 컴포넌트](#2-메인-컴포넌트)
    - [2.1 Executor](#21-executor)
    - [2.2 ExecutorService](#22-executorservice)
    - [2.3 Future](#23-future)

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
public class Invoker implements Executor {
    @Override
    public void execute(Runnable command) {
	command.run();
    }
}
```

```java
public void execute() {
    Executor executor = new Invoker();
    executor.execute(() -> {
        System.out.println("Hello");
    });
}
```

## 2.2 ExecutorService

- _ExecutorService_ 는 비동기 처리를 위해 사용한다.
- 메모리 내 대기열을 관리하고 쓰레드 가용성에 따라 제출된 작업을 실행한다.

1. _ExecutorService_ 를 생성하려면 _Runnable_ 인터페이스를 구현해야 한다.
2. _ExecutorService_ 인스턴스를 만들고 생성 시 쓰레드 풀 크기를 할당한다.

```java
public class Task implements Runnable{

    @Override
    public void run() {
        //TODO
    }
}
```

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

executor.submit(new Task()); // 1
executor.submit(() -> {
    new Task();
}); // 2
```

_ExecutorService_ 는 두 가지 기본 실행 종료 방법을 제공한다.

1. shutdown() : 실행중인 모든 태스크가 완료될 때까지 기다린다.
2. shutdownNow() : 현재 실행 중인 모든 태스크를 종료하고 대기 중인 태스크도 중지시킨다.

## 2.3 Future

> 비동기 작업의 결과를 나타내는데 사용된다.

비동기 작업이 완료됐는지 여부를 확인하고 계산 결과를 얻는 메서드가 함께 제공된다.

```java
public void invoke() {
    ExecutorService executor = Executors.newFixedThreadPool(10);
    
    Future<String> future = executor.submit(() -> {
    Thread.sleep(1000);
    return "Hello world!";
    });   	
}
```
아래 예제 코드처럼 _Future_ 결과가 준비됐는지 확인하고 계산이 완료되면 데이터를 가져올 수 있다.

```java
if (future.isDone() && !future.isCancelled()) {
    try {
        str = future.get();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }
}
```
