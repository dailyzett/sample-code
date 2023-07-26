## 스프링 삼각형

### 1. 개요

스프링을 이루는 핵심 요소 3개를 스프링의 삼각형이라고 부른다.
3개 요소는 IoC/DI, AOP, PSA 이다.

### 2. IoC/DI

IoC의 약어는 Inversion Of Control로 직역하면 제어의 역전이다.
DI의 약어는 Dependency Injection으로 직역하면 의존성 주입이다.
IoC와 DI는 서로 같은 개념을 가졌지만, 그 개념을 담는 의미로써 DI가 더 정확하기 때문에 DI라는 약어가 더 자주 사용된다.

#### 2.1 의존성이란?

> 운전자가 자동차를 생산한다.<br>
> 자동차는 내부적으로 타이어를 생산한다.

의사 코드가 이렇게 있을 때, Java 코드로 다음과 같이 표현할 수 있다.

```java
new Car();
Car 객체 생성자에서 new Tire();
```

위 코드에서 Car 객체가 Tire 객체에 의존하고 있다.
단순하게 보면 의존성은 **new**이다. new를 실행하는 Car와 Tire 사이에서 Car가 Tire에 의존하고 있기 때문이다.
결론적으로 전체가 부분을 의존한다고 표현할 수 있다. 더 깊이 들어가면 의존하는 객체와 의존되는 객체 사이의 집합(Aggregation) 관계와
구성(Composition) 관계로 구분할 수 있다.

> 집합 관계 : 부분이 전체와 다른 생명 주기를 가진다.<br>
> 예) 집 vs 냉장고

> 구성 관계 : 부분이 전체와 같은 생명 주기를 가진다. <br>
> 예) 사람 vs 심장

#### 2.2 의존성 주입이란?

주입이라는 말은 외부에서라는 뜻을 내포하고 있다.

```java
//Tire 은 인터페이스이다.
Tire tire = new KoreanTire();
Car car = new Car(tire);
```

위 코드를 보면 car 객체를 생성하고 생성자를 통해 tire 객체를 주입하고 있다.
주입이라는 말은 외부에서라는 뜻을 내포하고 있는 단어이다.
정리하면, **자동차 내부에서 타이어를 생산하는게 아니라 외부에서 생산된 타이어를 자동차에 장착하는 작업**이 주입이다.

##### 2.2.1 의존성 주입의 장점

의존성 주입을 사용하면 Tire 인터페이스를 구현한 어떤 객체이든 들어오기만 하면 작동되기 때문에 확장성이 좋아진다.
예를 들어, KoreanTire() 대신 ChinaTire()와 AmericanTire()가 새롭게 구현되어도 Tire 인터페이스를 구현하기만 하면 Car.java 코드를
변경할 필요 없이 사용할 수 있기 때문이다. 추가로 xml 설정 파일에 주입할 객체의 id와 type을 설정하고 스프링의 **@Autowired** 나 자바 표준
어노테이션인 **@Resource** 를 사용하면 xml 파일 변경만으로도 쉽게 코드 확장이 가능하다.

### 3. AOP

Aspect-Oriented Programming 의 약자이다. 직역하면 관점 지향 프로그래밍인데 이 말로는 AOP 개념 이해가 난해하다.
AOP는 OOP와 별개로 생각해야할 것이 아니다. 코드를 보다 더 객체 지향적으로 작성하기 위해 만들어진 보조 장치라고 생각해야한다.

AOP는 핵심 관심사와 횡단 관심사로 구성된다.
코드에서 항상 반복적으로 실행되는 부분을 횡단 관심사라고 하고 모듈별로 동작이 다른 부분을 핵심 관심사라 칭한다.

```java
DB 커넥션 준비
Statement 객체 준비

try{
    DB 커넥션 연결
    Statement 객체 세팅
    insert / update / delete / select 실행
}catch ...{
    예외 처리
}finally{
    DB 자원 반납
}
```

예를 들어 JDBC를 사용할 때 **insert / update / delete / select 실행** 부분을 제외하고는 항상 반복되는 부분이다.
여기서 insert / update / delete / select 실행 부분은 핵심 관심사고 나머지는 횡단 관심사이다.

코드의 한 부분이 계속해서 반복된다면 분리해서 한 곳에서 관리해야 한다. 여기에서 AOP의 진가가 드러난다.
AOP를 사용하면 중복되는 코드를 한 곳에 몰아넣고 각 메서드가 실행될 때마다 중복 코드를 주입시킬 수 있다.
즉, DI가 의존성에 대한 주입이라면 AOP는 **로직 주입**이다. 그렇다면 로직을 주입한다면 어디에서 할 수 있을까?
객체 지향에서 로직이 있는 곳은 메서드의 안쪽인데 메서드에서 주입할 수 있는 곳은 5가지 밖에 없다.

- Around : 메서드 전 구역
- Before : 메서드 시작 전
- After : 메서드 종료 후
  - AfterReturning : 메서드 정상 종료 후
  - AfterThrowing : 메서드에서 예외가 발생하면서 종료된 후
  
로직을 주입하기 위해 AOP는 프록시를 사용한다. 하지만 호출하는 쪽과 호출되는 쪽 모두 중간에 프록시가 존재한다는 것은 알지 못한다.
오직 스프링 프레임워크만이 프록시가 존재한다는 것을 알고 있다.

#### 3.1 스프링 AOP의 핵심

스프링 AOP의 핵심은 다음 3가지이다.

- AOP는 인터페이스 기반이다.
- AOP는 프록시 기반이다.
- AOP는 런타임 기반이다.

AOP를 인터페이스없이 CGLiB 라이브러리를 이용해서 적용할 수도 있다. 하지만 CGLiB 라이브러리를 사용해야하는경우는 코드 변경이 불가능한
서드 파티 모듈안에 final로 선언된 클래스에 AOP를 적용해야 하는 경우 정도이다. 횡단 관심사를 핵심 로직에 주입하기 위해 프록시의 존재는 필수이고,
스프링이 로직을 주입하는 행동은 런타임 때 결정되므로 스프링 AOP는 당연히 런타임 기반이다.

#### 3.2 스프링 AOP 용어

스프링 AOP에는 여러가지 용어가 있다.

- Aspect
- Advisor
- Advice
- JoinPoint
- Pointcut

그리고 다음 코드는 횡단 관심사를 구현한 코드이다.

```java
@Aspect
public class MyAspect{
    @Before("execution(* runSomething())")
    public void before(JoinPoint joinPoint){
        //TODO
    }
}
```

##### 3.2.1 Pointcut

Pointcut은 횡단 관심사를 적용할 타깃 메서드를 선택하는 지시자이다. 예제 코드에서는 (* runSomething())이 Pointcut이다.
Pointcut을 작성할 때의 양식은 다음과 같다.

> [접근제한자패턴]**리턴타입패턴**[패키지&클래스패턴.]**메서드이름패턴(파라미터패턴)**[throws 예외패턴]

굵은 글씨 이외의 대괄호는 생략이 가능하다는 의미이다. 필수 요소는 리턴 타입 패턴, 메서드 이름 패턴, 파라미터 패턴 뿐이다.

##### 3.2.2 JoinPoint

Pointcut은 JoinPoint의 부분 집합이다. Pointcut의 후보가 되는 모든 메서드들이 JoinPoint, 즉 Aspect 적용이 가능한 지점이 된다.
그래서 JoinPoint는 **Aspect 적용이 가능한 모든 지점**이라고 할 수 있다. 스프링 AOP에서 JoinPoint는 스프링 프레임워크가 관리하는 빈의 모든
메서드에 해당한다.

예제 코드 메서드의 before 매개변수로 있는 JoinPoint는 무슨 의미일까?
그건 그때마다 다르다. 만약 A.runSomething() 메서드를 호출한 것이라면 JoinPoint는 A 객체의 runSomething() 메서드가 된다.
B.runSomething() 메서드를 호출한 것이라면 JoinPoint는 B 객체의 runSomething() 메서드가 된다.

##### 3.2.3 Advice

Advice는 **Pointcut에 적용할 로직에 "언제"라는 개념까지 포함**하고 있다.
예제 코드에서는 @Before 과 public void before(JoinPoint joinPoint) 메서드에 해당한다.

##### 3.2.4 Aspect

Aspect는 여러 개의 Advice와 여러 개의 Pointcut의 결합체를 의미하는 용어이다.
Advice는 [언제 + 무엇을]를 의미하는 단어이고, Pointcut은 [어디에]를 의미하는 단어다.
즉 Aspect는 [언제 + 어디에 + 무엇을]를 의미하는 단어가 된다.

##### 3.2.5 Advisor

Advisor은 하나의 Advice와 하나의 Pointcut의 결합체를 의미하는 용어이다.
스프링 AOP에서만 사용하고 있는 단어이고 스프링 버전이 업데이트되면서 이제는 쓰지 말라고 권고하는 기능이기도 하다.
스프링이 발전하면서 Aspect가 나왔기 때문에 굳이 하나의 Advice와 하나의 Pointcut만을 결합하는 Advisor을 사용할 필요가 없기 때문이다.

### 4. PSA

Portable Service Abstraction 의 약어로 **일관성 있는 서비스 추상화**이다.
서비스 추상화의 예로 JDBC를 들 수 있다. JDBC라고 하는 표준이 있기 때문에 사용자가 어떤 데이터베이스를 사용하든 코드는
Connection, Statement, ResultSet을 이용해 작성한다. 데이터베이스 종류에 상관없이 같은 코드를 작성해도 되는 이유는 디자인 패턴 중 하나인
어댑터 패턴을 활용했기 때문이다. 이처럼 어탭터 패턴을 이용해 다수의 기술들을 공통의 인터페이스로 제어할 수 있게 한 것을 서비스 추상화라고 한다.

### 5. 참고 자료

> (서적) 스프링 입문을 위한 자바 객체 지향의 원리와 이해 - 07. 스프링 삼각형과 설정 정보