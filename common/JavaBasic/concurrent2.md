# 목차

# 1. Future 생성

> _Future_ 클래스는 비동기 연산의 **미래 결과**를 나타낸다. 그래서 클래스 이름도 _Future_ 이다.

- 긴 시간동안 실행되는 메서드는 _Future_ 에 캡슐화된 작업이 완료되는 동안 다른 프로세스를 실행할 수 있으므로 비동기 처리와
_Future_ 인터페이스 사용에 대해서 학습할 때 좋은 예가 된다.
- _Future_ 의 비동기 특성을 활용하는 작업의 몇 가지 예:
  - 계산 프로세스
  - 빅 데이터 구조 조작
  - 원격 메서드 호출(파일 다운로드, HTML 스크랩, 웹 서비스)

# 1.1 _FutureTask_ 로 _Future_ 구현하기

```java
public class SquareCalculator {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    
    public Future<Integer> calculate(Integer input) {
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }
}
```

- 실제로 계산을 수행하는 코드 비트는 call() 메서드에 포함되어 있으며 람다 식으로 제공된다.
- _Callable_ 메서드는 반환 값이 있는 인터페이스이며 단일 _call()_ 메서드가 있다.
- _Callable_ 인스턴스는 _Future_ 객체를 돌려줄 _Executor_ 에게 전달해야 한다.
- _ExecutorService_ 객체가 있으면 _submit_ 을 호출하고 _Callable_ 로 인자를 전달하기만 하면 된다.
그런 다음 _submit()_ 은 작업을 시작하고 _Future_ 인터페이스의 구현체인 _FutureTask_ 객체를 반환한다.

# 2. Future Consuming

## 2.1 isDone() 및 get() 을 사용하여 결과 얻기

- _Future.isDone()_ 은 작업 처리를 완료했는지 알려준다. 작업이 완료되면 true, 그렇지 않으면 false 를 반환한다.
- _Future.get()_ 은 실제 결과를 반환한다. 이 작업이 완료될 때 까지 다른 _executor_ 를 차단한다.

```java
public static void main(String[] args) throws InterruptedException, ExecutionException {
    Future<Integer> futureCalculate = new SquareCalculator().calculate(10);

    while (!futureCalculate.isDone()) {
        System.out.println("계산중...");
        Thread.sleep(300);
    }

    Integer result = futureCalculate.get();
    System.out.println("result = " + result);
}
```

> **출력 결과:**

```text
계산중...
계산중...
계산중...
계산중...
result = 100
```

_SquareCalculator_ 객체에서 계산하기전 1000ms 대기하도록 되어 있으므로 "계산중..."이 4번이 출력되고 결과값을 출력된다.

## 2.2 cancel() 로 Future 취소

작업을 트리거했지만 더 이상 결과가 필요없다고 가정한다. _Future.cancel(boolean)_ 을 사용하여 _executor_ 에게
작업을 중지시키고 기본 쓰레드를 중단하도록 명령할 수 있다.

```java
Future<Integer> future = new SquareCalculator().calculate(4);

boolean canceled = future.cancel(true);
```

# 3. 쓰레드풀로 멀티 쓰레딩 구현

현재 _ExecutorService_ 는 단일 쓰레드다.
아래는 단일 쓰레드로 두 개의 계산을 동시에 실행하는 예제이다.

```java
public class Main {

  public static void main(String[] args)
          throws InterruptedException, ExecutionException {
    SquareCalculator squareCalculator = new SquareCalculator();
    Future<Integer> futureCalculate1 = squareCalculator.calculate(10);
    Future<Integer> futureCalculate2 = squareCalculator.calculate(100);

    while (!(futureCalculate1.isDone() && futureCalculate2.isDone())) {
      System.out.println(
          String.format(
                  "future is %s and future is %s",
                  futureCalculate1.isDone() ? "done" : "not done",
                  futureCalculate2.isDone() ? "done" : "not done"
          )
      );
      Thread.sleep(300);
    }

    Integer result1 = futureCalculate1.get();
    Integer result2 = futureCalculate2.get();

    System.out.println(String.format("%d and %d", result1, result2));
  }
}
```

> **출력 결과:**

```text
future1 is not done and future2 is not done
future1 is not done and future2 is not done
future1 is not done and future2 is not done
future1 is not done and future2 is not done
future1 is done and future2 is not done
future1 is done and future2 is not done
future1 is done and future2 is not done
100 and 10000
```

출력 결과에서 알 수 있는 점은 병렬로 실행되지 않는다는 점이다.
병렬 실행으로 바꾸려면 다른 형태의 _ExecutorService_ 를 사용해야 한다.

```java
private ExecutorService executor = Executors.newFixedThreadPool(10);
```

> **출력 결과:**

```text
future1 is not done and future2 is not done
future1 is not done and future2 is not done
future1 is not done and future2 is not done
future1 is not done and future2 is not done
100 and 10000
```

2개의 작업이 동시에 시작하고 종료되며 전체 프로세스 완료 시간도 단축됐다.

# 4. ForkJoinTask 개요

> _ForkJoinTask_ 는 _Future_ 를 구현하는 추상 클래스이다.

**특징: **

- _ForkJoinTask_ 는 기본 작업을 완료하는 데 필요한 작업의 일부로 새 하위 작업을 생성한다.
- _fork()_ 를 호출하여 새 작업을 생성하고 _join()_ 으로 모든 결과를 수집해서 _ForkJoinTask_ 이다.
- 병렬 처리 속도를 높이기 위해 **분할 정복 방식**을 이용한다.

## 4.1 ForkJoinPool

> 워커 쓰레드를 관리하고 쓰레드 풀 상태 및 성능에 대한 정보를 얻을 수 있는 기능을 제공한다.
> _ExecutorService_ 를 구현한 클래스이다.

워커 쓰레드는 한 번에 하나의 작업만 실행 가능하다. 하지만 _ForkJoinPool_ 은 모든 단일 하위 작업에 대해
별도의 쓰레드를 생성하지 않는다. 대신 풀의 각 쓰레드에는 작업을 저장하는 **deque**가 존재한다.

_ForkJoinPool_ 은 work-stealing 알고리즘으로 쓰레드의 작업 비중을 조절한다.

### 4.1.1 work-stealing 알고리즘

_work-stealing_ 은 하나의 쓰레드가 사용 중인 쓰레드의 deque 에서 작업을 훔치는 알고리즘이다.

- 기본적으로 워커 쓰레드는 자체 deque 헤드에서 작업을 가져온다.
- 워커 쓰레드에 작업이 없을 때, 작업 완료 시간이 가장 오래걸릴만한 전역 엔트리 큐 또는 다른 사용 중인 쓰레드의 deque 꼬리로부터 작업을
가져온다.
- 이 방식은 쓰레드가 작업을 위해 경쟁할 가능성을 최소화한다. 또한 작업 시간이 오래 걸리는 것을 먼저 작업하므로
쓰레드가 작업을 찾는 횟수도 줄어든다.

### 4.1.2 ForkJoinTask 추상 클래스

_ForkJoinTask_ 를 구현하는 두 가지 추상 클래스가 있다.

- RecursiveTask : 완료 시 값을 반환한다.
- RecursiveAction : 반환 값이 없다.

```java
public class FactorialSquareCalculator extends RecursiveTask<Integer> {

    private Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        FactorialSquareCalculator factorialSquareCalculator = new FactorialSquareCalculator(n - 1);
        factorialSquareCalculator.fork();
        return n * n + factorialSquareCalculator.join();
    }
}
```

- _fork()_ 를 호출하여 _ForkJoinPool_ 에 하위 작업의 실행을 시작하도록 요청한다.
- _join()_ 메서드는 계산 결과를 반환하고 현재 진행 중인 숫자의 제곱을 더한다.

이제 실행 및 쓰레드 관리를 하기 위해 _ForkJoinPool_ 을 생성한다.

```java
public static void main(String[] args) {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

    forkJoinPool.execute(calculator);

    System.out.println("calculator = " + calculator.join().intValue());
}

// 출력결과: 385
```

