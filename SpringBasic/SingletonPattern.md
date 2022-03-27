## 싱글톤 패턴

### 1. 개요

싱글톤 패턴은 인스턴스를 하나만 만들어 사용하는 패턴이다.
커넥션 풀 또는 쓰레드 풀과 같이 생성에 비용이 많이 드는 객체라면 인스턴스를 여러 개 만들 때 그만큼 불필요한 자원을 사용하게 된다.
그래서 이런 경우 싱글톤 패턴을 이용해 오직 하나의 인스턴스만 만들고 그것을 계속해서 재사용한다.

싱글톤 패턴을 적용하면 두 개 이상의 객체가 존재할 수 없다. 이를 위해 여러 개의 제약 조건이 필요하다.
- new 연산자를 제한하기 위해 생성자에 private 접근 제어자를 사용한다.
- 유일한 단일 객체를 반환할 수 있는 스태틱 메서드가 필요하다.
- 유일한 단일 객체를 참조할 스태틱 참조 변수가 필요하다.

그래서 싱글톤 패턴의 코드는 모두 이런식이다.

```java
public class Singleton{
    static Singleton singletonObject;
    
    private Singleton(){};

    public static Singleton getInstance() {
        if (singletonObject == null) {
            singletonObject = new Singleton()
        }
        
        return singletonObject;
    }
}
```

생성자의 접근 제어자가 private이기 때문에 인스턴스를 생성할 때 사용하는 new 연산자는 사용할 수 없다.
싱글톤 클래스를 이용하려면 반드시 위 코드의 getInstance() 메서드를 사용해야한다.
```java
Singleton s1 = Singleton.getInstance();
Singleton s2 = Singleton.getInstance();
Singleton s3 = Singleton.getInstance();
```

또한 한 객체를 공유 메모리에서 사용하는 것이기 때문에 toString() 메서드를 실행했을 때 가리키는 객체 주소값은 모두 동일하다.
```java
s1.toString();
s2.toString();
s3.toString();
```

싱글톤 객체는 단일 객체이기 때문에 속성값을 갖지 않아야 한다. 단일 객체가 속성값을 갖게 되면 **하나의 참조 변수가 변경한 단일 객체의 속성**이
다른 참조 변수에 영향을 미치기 때문이다. 다만 읽기 전용 속성을 갖는 것은 문제가 되지 않는다. 그리고 단일 객체가 다른 단일 객체에 대한 참조를
가진 것 또한 문제가 되지 않는다.

### 2. 싱글톤 패턴의 구현 방법

싱글톤 클래스를 생성하는 방식은 두 가지로 나뉜다.
- Eager Initialization : 클래스의 객체는 애플리케이션이 시작할 때 JVM에 의해 생성된다. 참조 인스턴스를 직접 할당하여 수행한다.
- Lazy Initialization : 필요한 경우에만 객체를 생성하는 방법이다.

#### 2.1 Eager Initialization

프로그램이 항상 이 클래스를 사용하거나 인스턴스를 생성하는 비용이 시간과 공간을 너무 잡아먹지 않는 경우 사용할 수 있다.

```java
public class Singleton{
    public static final Singleton instance = new Singleton();
    
    private Singleton(){}

    private static Singleton getInstance() {
        return instance;
    }
}
```

장점:
1. 구현이 간단하다

단점:
1. 리소스 낭비로 이어질 수 있다. 클래스의 인스턴스가 사용 여부에 상관없이 항상 생성되기 때문이다.
2. 예외 처리가 불가능하다.

#### 2.2 Lazy Initialization

자원 낭비를 방지하기 위해 필요할 때만 인스턴스를 생성하는 방법이다.

```java
public class Singleton{
    static Singleton singletonObject;
    
    private Singleton(){};

    public static Singleton getInstance() {
        if (singletonObject == null) {
            singletonObject = new Singleton()
        }
        
        return singletonObject;
    }
}
```

**1. 개요**에서 나온 코드와 동일하다.

장점:
1. 필요한 경우에만 객체가 생성되기 때문에 리소스 낭비가 적다.
2. 메서드에서 예외처리가 가능하다.

단점:
1. null 체크를 반드시 해줘야한다.
2. 인스턴스에 직접 접근이 불가능하다.
3. 멀티쓰레드 환경에서 싱글톤 속성을 깨뜨릴 수 있다.

위의 코드는 단점의 3번에서 getInstance() 메서드를 여러 쓰레드가 동시에 접근할 경우 막을 방법이 없다.
따라서 멀티 쓰레드 환경에서 안전하게 사용하고 싶을 경우 getInstance() 메서드에 synchronized 키워드를 붙일 수 있다.

```java
synchronized public static Singleton getInstance() {
    if (singletonObject == null) {
        singletonObject = new Singleton()
    }
    
    return singletonObject;
}
```

하지만 getInstance() 메서드가 동기화되기 때문에 여러 쓰레드가 동시 접근이 불가능하므로 성능은 저하되는 단점이 있다.
이런 성능문제를 제거하기 위해서는 synchronized 키워드를 중요 섹션에만 사용해야 한다.

#### 2.3 Double Checked Locking

```java
public static Singleton getInstance() {
    if (singletonObject == null) {
        synchronized(Singleton.class){
            if(singletoneObject == null){
                singletonObject = new Singleton()
            }
        }
    }
    return singletonObject;
}
```

getInstance() 전부를 동기화하지 않고 **인스턴스를 생성하는 블록**만 동기화하기 때문에 쓰레드가 대기해야하는 시간이 최소한으로 줄어든다.
하지만 이 코드도 멀티 쓰레드 환경에서 특정 조건이 갖춰지면 오류가 발생한다는 점이 있다. 특정 조건은 메인 메모리와 작업 쓰레드의 메모리 간
에 데이터 이동이 있기 때문에 **메인 메모리와 작업 쓰레드 간의 동기화가 진행되는 동안 빈틈** 이 생길 때 발생한다.
따라서 해당 변수의 값을 항상 메인 메모리에서 읽어올 수 있도록 **volatile** 변수를 선언해야한다.

##### 2.3.1 volatile 을 사용하지 않는 Double Checked Locking 에서 발생하는 문제

- 쓰레드 A가 인스턴스를 생성하고 synchronized 블록을 벗어남
- 쓰레드 B가 synchronized 블록에 들어와서 null 체크를 하는 지점 진입
- 쓰레드 A에서 생성한 인스턴스가 쓰레드 A에만 존재하고 메인 메모리에 존재하지 않거나 메인 메모리에 존재하지만 쓰레드 B에 존재하지 않을 때
- 즉, 메모리 간 동기화가 완벽하게 이루어지지 않은 상태라면 쓰레드 B는 인스턴스를 또 생성하게 된다.

#### 2.4 Bill Pugh Singleton

volatile 외의 해결 방법으로 나온 것은 Bill Pugh Singleton 이라고 불리는 방법론이다.
코드 구현은 다음과 같다.

```java
public class Singleton{
    private Singleton{
        // private 생성자    
    }
    
    private static class BillPughSingleton{
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return BillPughSingleton.INSTANCE;
    }
}
```

- BillPughSingleton 스태틱 이너 클래스는 Singleton 클래스가 로드돼도 객체를 바로 생성하지 않는다.
- 이너 클래스는 getInstance() 메서드를 실행했을 때만 실행된다. 

따라서 얼핏 보면 Eager Initialization 같지만 사실은 Lazy Initialization 이다.
첫 번째 쓰레드가 getInstance() 메서드를 호출할 때 JVM은 BillPughSingleton 클래스를 메모리에 로드한다.
**이미 메모리에 로드되있기 때문에 JVM은 이것을 한 번만 로드한다.**
여기서 만약 두 번째 쓰레드가 getInstance() 메서드에 접근하다고 해도, JVM은 두 번 로드하지않고 첫 번째 로드에서 초기화가 끝날 때까지 대기한다.

Double Checked Locking 방식보다 구현이 간단하기 때문에 Bill Pugh Singleton 싱글톤 방식은 멀티 쓰레드 환경에서 많이 사용된다.





