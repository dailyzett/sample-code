## 템플릿 콜백 패턴

### 1. 개요

템플릿 콜백 패턴은 전략 패턴과 모든 것이 동일하지만 전략을 익명 클래스로 정의해서 사용한다.
바로 예제로 넘어가자.

### 2. 예제

전략 패턴과 동일하지만 단지 객체를 주입받던 것을 익명 내부 클래스를 생성하는 것으로 코드가 변경되었다.

```java
public class Main {
    public static void main(String[] args) {
        Strategy strategy = null;
        Soldier kim = new Soldier();

        kim.runContext(new Strategy(){
            @Override
            public void runStrategy() {
                System.out.println("총을 쏜다");
            }
        });

        System.out.println();

        kim.runContext(new Strategy(){
            @Override
            public void runStrategy() {
                System.out.println("칼을 휘두른다");
            }
        });

        System.out.println();

        kim.runContext(new Strategy(){
            @Override
            public void runStrategy() {
                System.out.println("활을 쏜다");
            }
        });
    }
}
```

하지만 코드를 보면 중복된 부분을 볼 수 있다.
new Strategy 라는 익명 클래스의 몸통 부분이 반복되므로 이 몸통 부분을 따로 뺄 수 있다.

```java
public class Soldier {
    void runContext(String weaponSelect) {
        System.out.println("전투 시작");
        execute(weaponSelect).runStrategy();
        System.out.println("전투 종료");
    }

    private Strategy execute(final String weaponSelect){
        return new Strategy() {
            @Override
            public void runStrategy() {
                System.out.println(weaponSelect);
            }
        };
    }
}
```

Strategy 인터페이스를 리턴받는 메서드는 파라미터로 String 객체를 받는다.
Soldier 클래스에 execute 메서드가 추가됨으로써 메인 메서드는 훨씬 간단해진다.

```java
public class Main {
    public static void main(String[] args) {
        Strategy strategy = null;
        Soldier kim = new Soldier();

        kim.runContext("총을 쓴다");

        System.out.println();

        kim.runContext("칼을 휘두른다");

        System.out.println();

        kim.runContext("활을 쏜다");
    }
}
```

코드를 보면 객체를 생성하는 부분도, 익명 클래스를 생성하는 부분도 없기 때문에 코드 가독성이 상당히 좋아졌다.

### 3. 정리

템플릿 콜백 디자인 패턴을 한마디로 정의하면 이렇다.

> 전략을 익명 클래스로 구현한 전략 패턴

스프링은 템플릿 콜백 패턴을 DI(Dependency Injection)에 적극 활용하고 있다.
따라서 스프링을 잘 활용하기 위해 전략 패턴, 템플릿 콜백 패턴, 리팩터링된 템플릿 콜백 패턴을 잘 알아야한다.
템플릿 콜백 패턴 또한 전략 패턴의 일종이므로 개방 폐쇄 원칙(OCP)와 의존 역전 원칙(DIP)를 만족한다.