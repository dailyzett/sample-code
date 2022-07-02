## 팩토리 메서드 패턴

### 1. 개요

팩토리 메서드 패턴은 자바에서 가장 많이 사용되는 디자인 패턴이다.
GoF에서 정의하고 있는 내용은 다음과 같다.

> 객체를 생성하는 인터페이스를 정의한다. 하지만 클래스의 인스턴스화는 하위 클래스에게 맡긴다.

팩토리 메서드 패턴은 가상 생성자를 생성하여 클라이언트에서 팩토리 클래스로 클래스를 초기화하는 책임을 위임한다.
이것을 달성하기 위해 실제 구현 세부 사항을 숨기고 객체를 제공하는 팩토리에 의존한다.
생성된 객체는 공용 인터페이스를 통해 접근할 수 있다.

### 2. 예제

PolygonFactory 라는 클래스에서 생성된 객체를 가져오는 예제이다.
그림으로 표현하면 다음과 같다.

![](https://www.baeldung.com/wp-content/uploads/2017/11/Factory_Method_Design_Pattern.png)

팩토리 클레스를 참조할 공용 인터페이스를 선언한다.

```java
public interface Polygon {
    String getType();
}
```

PolygonFactory 클래스에서 numberOfSides 속성에 따라서 그에 맞는 객체들을 리턴한다.

```java
public class PolygonFactory {
    public Polygon getPolygon(int numberOfSides){
        if (numberOfSides == 3) {
            return new Triangle();
        }
        if(numberOfSides == 4){
            return new Square();
        }
        if(numberOfSides == 5){
            return new Pentagon();
        }
        return null;
    }
}
```

각 도형은 Polygon 인터페이스를 구현하고 있으며 Square과 Pentagon도 클래스명만 다르지 구현하는 방법은 같다.

```java
public class Triangle implements Polygon{
    @Override
    public String getType() {
        return "삼각형";
    }
}
```

일반적으로 삼각형, 사각형, 오각형 객체를 생성할 때는 new 생성자를 이용해야 하지만,
객체를 생성하는 역할은 팩토리 클래스에게 위임했기 때문에 코드는 다음과 같이 변경된다.

```java
public class Main {
    public static void main(String[] args) {
        Polygon polygon;
        PolygonFactory factory = new PolygonFactory();

        polygon = factory.getPolygon(3);
        System.out.println("모양 3은 " + polygon.getType() + "이다");
    }
}
```

출력결과:
```java
모양 3은 삼각형이다
```

### 3. 팩토리 메서드 디자인 패턴을 사용하는 경우

- 인터페이스 또는 추상 클래스를 상속받는 클래스가 자주 변경될 것이 예상될 때
- 초기화 과정이 간단하고 생성자 매개변수의 개수가 많지 않을 때


### 4. 참고 자료

> (서적) 스프링 입문을 위한 자바 객체 지향의 원리와 이해 - 06. 스프링이 사랑한 디자인 패턴

> https://www.baeldung.com/creational-design-patterns