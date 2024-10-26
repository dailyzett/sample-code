## 어댑터 패턴

### 1. 개요

어댑터는 실생활에서도 많이 적용되기 때문에 이해하기 쉽다.
예를 들어 우리가 스마트폰을 충전하려고할 때, 스마트폰을 직접 전원 콘센트에 연결해서 충전하는 것은 불가능하다.
그래서 우리는 충전하기 위해 **충전기라는 어댑터**를 사용한다.
소프트웨어도 마찬가지로, 기존 호환되지 않는 인터페이스의 중간에 매개체를 두어 작동 가능하도록 만드는 디자인 패턴이 어댑터 패턴이다.

어댑터 패턴의 순서는 다음과 같다.
- 클라이언트는 대상 인터페이스의 메서드를 호출하여 어댑터에 요청한다.
- 어댑터는 어댑터 인터페이스를 이용해서 해당 요청을 변환한다.
- 클라이언트는 호출 결과를 수신한다. 하지만 어댑터의 존재는 알지 못한다.

순서를 그림으로 표현하면 밑의 그림처럼 된다.

![](https://media.geeksforgeeks.org/wp-content/uploads/classDiagram.jpg)

그림에서 Adaptee 라는 단어가 있는데, 동일한 기능을 제공하지만 다른 인터페이스를 제공하는 객체를 Adaptee라고 한다.


### 2. 예제

먼저 날 수 있고, 울음소리를 내는 새(Bird) 인터페이스와 울음소리를 내는 장난감 오리(ToyDuck) 인터페이스를 생성한다.
```java
public interface Bird {
    public void fly();
    public void makeSound();
}
```
```java

public interface ToyDuck {
    public void squeak();
}
```

이후 Bird 인터페이스와 ToyDuck 인터페이스를 구현한 Sparrow(참새)와 PlasticToyDuck 클래스를 생성한다.
```java
public class Sparrow implements Bird {

    @Override
    public void fly() {
        System.out.println("참새가 날아다닌다");
    }

    @Override
    public void makeSound() {
        System.out.println("짹짹");
    }
}
```

```java
public class PlasticToyDuck implements ToyDuck{
    @Override
    public void squeak() {
        System.out.println("플라스틱 장난감 오리가 운다");
    }
}
```

여기서 개발자가 toyDuck 객체에도 참새가 우는 소리를 내고 싶어한다고 가정해보자.
Sparrow 클래스와 toyDuck 클래스에 비슷한 기능을 하는 메서드가 존재하지만 각각 다른 인터페이스를 구현하고 있기 때문에 직접 사용은 불가능하다.
이럴때 중간에 어댑터용으로 사용할 어댑터 클래스를 하나 생성한다.

```java
public class BirdAdapter implements ToyDuck{
    Bird bird;

    public BirdAdapter(Bird bird){
        this.bird = bird;
    }

    @Override
    public void squeak() {
        bird.makeSound();
    }
}
```

새(bird) 객체를 속성값으로 만든 BirdAdapter 클래스이고 ToyDuck 인터페이스를 구현한다.
이 클래스를 만들고 메인 메서드에서는 다음과 같이 사용 가능하다.

```java
public class Main {
    public static void main(String[] args) {
        Sparrow sparrow = new Sparrow();
        ToyDuck toyDuck = new PlasticToyDuck();

        ToyDuck birdAdapter = new BirdAdapter(sparrow);

        System.out.println("참새: ");
        sparrow.fly();
        sparrow.makeSound();

        System.out.println("장난감 오리: ");
        toyDuck.squeak();

        System.out.println("새 어댑터: ");
        birdAdapter.squeak();
    }
}
```

birdAdapter에 sparrow 인스턴스를 넣으면, 어댑터 클래스 생성자에서 형변환을 통해 sparrow 객체를 속성값으로 넣는다.
그 결과로 main 메서드에서 실행시킨 squeak() 메서드의 이름은 동일하지만 birdAdapter는 sparrow 객체를 참조값으로 넣은 후, 그것에 해당되는 메서드가 실행되기 때문에
기존의 **플라스틱 장난감 오리가 우는**행동에서 **참새가 우는**행동으로 변환가능하다.

#### 2.1 객체 어댑터(Object Adapter) vs 클래스 어댑터(Class Adapter)

위에서 설명한 코드는 객체 어댑터 패턴이라고 불린다. 왜냐하면 어댑터(BirdAdapter)가 Adaptee(Bird)의 인스턴스를 가지고 있기 때문이다. 
클래스 어댑터 패턴이라고 불리는 것은 합성(Composition)을 사용하는 것이 아니라 상속(Inheritance)을 이용한다.
하지만 상속을 이용해 어댑터 패턴을 이용하려면 다중 상속이 필수인데, 자바는 다중 상속을 지원하지 않으므로 알 필요 없다.

### 3. 장점과 단점

장점: 
- 재사용성과 코드 유지보수에 있어 유리하다.
- 클라이언트 클래스는 오직 원하는 인터페이스의 행동만 선택하면 되기 때문에 복잡하지 않고,
다른 인터페이스를 상속받는 클래스라도 합성(Composition)을 이용해 메서드를 넘겨받을 수 있기 때문에 코드가 유연해진다.

단점:
- 모든 요청이 전달되기 때문에 오버헤드가 증가한다.
- 가끔씩 필요한 어댑터에 도달하기 위해 어댑터 체인을 이룰 때가 있는데, 이럴 때 많은 설계 조정이 필요하다.


### 4. 참고 자료

> (서적) 스프링 입문을 위한 자바 객체 지향의 원리와 이해 - 06. 스프링이 사랑한 디자인 패턴

> https://www.geeksforgeeks.org/adapter-pattern/