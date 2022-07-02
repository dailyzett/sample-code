## 전략 패턴

### 1. 개요

전략 패턴을 구성하는 요소는 세 개가 있다.

- 전략 메서드를 가진 전략 객체
- 전략 객체를 사용하는 컨텍스트(전략 객체의 사용자/소비자)
- 전략 객체를 생성해 컨텍스트에 주입하는 클라이언트

클라이언트는 다양한 전략들 중 하나를 선택해 컨텍스트에 **전략 객체를 주입**한다.

### 2. 예제

군인이 있다고 가정한다.
군인은 전쟁을 할 때 무기로 총, 칼, 그리고 활을 사용한다.
군인에게 명령을 내릴 장교가 있는데, 이 장교는 군인이 전쟁을 할 때 사용할 무기를 선택하도록 명령한다.

군인, 장교 및 무기와 전략 요소들을 매핑시키면 다음과 같다.

- 무기 : 전략
- 군인 : 컨텍스트
- 장교 : 클라이언트

코드를 구현해보자.
먼저 공용 인터페이스를 생성하고 인터페이스를 구현하는 각 무기 클래스들을 생성한다.
```java
public interface Strategy {
    public abstract void runStrategy();
}
```

StrategyBow 클래스와 마찬가지로 칼, 총 클래스도 구현은 동일하다.

```java
public class StrategyBow implements Strategy{
    @Override
    public void runStrategy() {
        System.out.println("활을 쏜다: 슝.. 쐐액..");
    }
}
```

그리고 그 무기를 사용할 군인 클래스를 생성한다.
runContext 메서드에서 Strategy 타입을 주입받는다.

```java
public class Soldier {
    void runContext(Strategy strategy) {
        System.out.println("전투 시작");
        strategy.runStrategy();
        System.out.println("전투 종료");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Strategy strategy = null;
        Soldier kim = new Soldier();

        strategy = new StrategyGun();
        kim.runContext(strategy);

        System.out.println();

        strategy = new StrategySword();
        kim.runContext(strategy);

        System.out.println();

        strategy = new StrategyBow();
        kim.runContext(strategy);

    }
}
```
출력 결과:
```java
전투 시작
총을 쏜다: 탕, 타당, 타다당
전투 종료

전투 시작
칼을 쓴다: 챙.. 채채챙 챙
전투 종료

전투 시작
활을 쏜다: 슝.. 쐐액..
전투 종료
```

위 코드처럼 전략을 다양하게 변경하면서 컨텍스트를 실행할 수 있다.
전략 패턴은 상위 클래스에 공통 로직을 심어놓고 각 구현의 세부사항은 하위 클래스에게 맡기는 템플릿 메서드 패턴과 유사하다.
하지만 템플릿 메서드 패턴은 상속을 이용하고, 전략 패턴은 객체 주입을 통해 이를 구현한다.
자바는 다중 상속을 지원하지 않기 때문에 상속이라는 제한이 있는 템플릿 메서드 패턴보다는 전략 패턴이 더 많이 활용된다.

### 3. 정리

전략 패턴을 한 줄로 정리하면 다음과 같다.

> 클라이언트가 전략을 생성해 실행할 컨텍스트에 주입하는 패턴

참고로, 전략 패턴은 객체 지향의 5원칙 중 개방 폐쇄 원칙(OCP)와 의존 역전 원칙(DIP)가 적용되어 있다.