## 상속과 조합

### 1. 상속(Inheritance)의 정의

> 상속은 한 객체가 부모 객체의 모든 속성과 동작을 가져올 수 있게 하는 기술이다. 즉 하위 클래스는 상위 클래스의
> 메소드 및 필드를 상속받는다.

#### 1.1 상속의 목적

상속을 사용하는 이유는 효율성 때문이다. 구현하고자 하는 코드가 다른 코드에 중복되어서 나타날 경우, 이것을
다시 작성하는 것은 비효율적인 일이다. 하지만 상속을 사용해서 코드 중복 작성을 막을 수 있고 메서드 재정의를 통해
기능 확장도 쉬워지기 때문에 효율성과 가독성이 증가한다.


#### 1.2 상속의 단점

하지만 상속에도 단점이 있다. 첫번째는 상위 클래스에 종속적이라는 것이다.

```java
public class Parent {
    public int plus(int a, int b){
        return a + b;
    }
}
```
```java
public class Child extends Parent {
    public static void main(String[] args) {
        Parent parent = new Parent();
        System.out.println(parent.plus(1, 2));
    }
}
```

다음 코드의 출력 값은 3이 된다. 하지만 Parent 클래스 메서드의 구현을 다음과 같이 변경하면 출력값은 3이 아닌
9가 된다.

```java
public class Parent {
    public int plus(int a, int b){
        a = 7;
        return a + b;
    }
}
```

같은 패키지 내의 같은 개발자가 상위 클래스와 하위 클래스를 전부 통제하는 상황이라면, 위 방식은 단점이 아니지만
하위 클래스를 구현하는 사람이 다른 사람이라면 결괏값을 예상할 수 없다. 즉 상위 클래스의 작은 변경점 하나만으로
그것을 상속받은 모든 하위 클래스가 예상치 못한 동작을 할 수도 있다는 뜻이다.

그리고 상위 클래스 메서드 리턴 타입이 바뀔 경우 다음과 같이 충돌이 일어난다.

```java
//부모 클래스가 리턴 타입을 int 에서 String 으로 변경했다.
public class Parent {
    public String plus(int a, int b){
        return String.valueOf(a + b);
    }
}
```

![img1](https://media.vlpt.us/images/dailyzett/post/f7534489-b086-44a1-add7-a0a6b024f3fa/image.png)

하위 클래스에서 재정의된 메서드가 많아질수록, 사용자는 메서드의 리턴 타입을 일일이 수작업으로 변경해야한다.
이러한 문제점을 보완해줄 수 있는 방법이 조합(Composition)이다.

### 2. 조합(Composition)의 정의

> 조합은 has-a 관계를 구현하기 위한 Java의 설계 기술이다.

상속의 경우 extends 키워드를 이용하지만 조합(Composition)은 참조 변수를 선언해서 이용한다.
조합을 사용하면 다음과 같은 장점이 있다.

- 조합을 통해 코드를 재사용할 수 있다.
- Java는 다중 상속을 지원하지 않지만 조합을 이용하면 다중 상속이 가능하다.
- 조합은 테스트 하기 편하다.


예시)
```java
public class Parent {
    public int a = 3;
    public int b = 5;

    public Parent(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int plus(){
        return a + b;
    }
}
```

```java
public class Child{
    private Parent parent;

    public Child(Parent parent) {
        this.parent = parent;
    }

    public String getChildPlus(){
        return String.valueOf(parent.a + parent.b + 10);
    }
}
```
```java
public class Main {
    public static void main(String[] args) {
        Parent parent = new Parent(5,7);
        System.out.println("parent plus : " + parent.plus());

        Child child = new Child(parent);
        System.out.println("child plus : " + child.getChildPlus());
    }
}
```
