## 쓰레드풀

---

### 개요

>DB 및 웹 서버는 여러 클라이언트의 요청을 반복적으로 실행하며 많은 작업을 짧게 처리하는데 중점을 둔다

- 요청이 도착할 때마다 새로운 쓰레드를 만들고 그 쓰레드에서 작업을 처리하면 되지만 쓰레드의 생성과 소멸에
상당한 리소스를 낭비하게 됨
- 따라서 쓰레드의 개수는 제한할 필요가 있으며 그것이 Thread Pool 이다.


---

### 쓰레드풀이란?

> 이전에 생성된 쓰레드를 재사용하는 것.<br>
> 요청이 도착할 때 이미 쓰레드가 존재하므로 쓰레드 생성으로 인한 지연이 없다.


쓰레드풀은 ExcutorService 객체를 만들고 사용할 수 있다.

```java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Task implements Runnable {
    private String name;

    public Task(String s) {
        name = s;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i <= 5; i++) {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
                System.out.println("초기화 시간 작업 목록 - " + 
                        name + " = " + simpleDateFormat.format(date));
                Thread.sleep(1000);
            }
            System.out.println(name + " 완료됨");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static final int MAX_T = 3;

    public static void main(String[] args) {
        Runnable r1 = new Task("task 1");
        Runnable r2 = new Task("task 2");
        Runnable r3 = new Task("task 3");
        Runnable r4 = new Task("task 4");
        Runnable r5 = new Task("task 5");
        Runnable r6 = new Task("task 6");

        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);

        pool.execute(r1);
        pool.execute(r2);
        pool.execute(r3);
        pool.execute(r4);
        pool.execute(r5);
        pool.execute(r6);

        pool.shutdown();
    }
}
```

---

### 주의사항

- 쓰레드 풀을 너무 적게 할당하면 현재 실행 중인 쓰레드에 진입할 수 없기 때문에 교착상태가 발생할 수 있음
- 쓰레드에 예외가 발생해서 그 예외를 catch 하지 못한다면, 쓰레드는 그냥 종료되어 쓰레드 풀의 크기가 1 감소한다. 이것이 여러번 반복되면 쓰레드 풀이 결국 비게됨
- 쓰레드 풀의 크기가 너무 커도 리소스를 많이 잡아먹기 때문에 문제가 된다

