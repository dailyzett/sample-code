## 템플릿 메서드 패턴

### 1. 개요

> 템플릿 메서드 패턴은 상위 클래스에 공통 로직을 가지는 템플릿 메서드와 하위 클래스에 오버라이딩을 강제하는 추상 메서드 또는
> 선택적으로 오버라이딩할 수 있는 훅(Hook) 메서드를 두는 패턴이다.

### 2. 예제

주문 프로세스를 구현하는 추상클래스를 생성한다.
클래스 내엔 반드시 구현해야하는 추상 메서드가 존재한다.

```java
public abstract class OrderProcessTemplate {
    public boolean isGift;
    public abstract void doSelect();
    public abstract void doPayment();
    public final void giftWrap(){
        try {
            System.out.println("Gift wrap successful");
        } catch (Exception e) {
            System.out.println("Gift wrap unsuccessful");
        }
    }

    public abstract void doDelivery();

    public final void processOrder(boolean isGift) {
        doSelect();
        doPayment();
        if (isGift) {
            giftWrap();
        }
        doDelivery();
    }
}
```


```java
public class StoreOrder extends OrderProcessTemplate{
    @Override
    public void doSelect() {
        System.out.println("고객은 물건을 선택할 수 있다.");
    }

    @Override
    public void doPayment() {
        System.out.println("포스기로 물건에 대한 값을 지불한다.");
    }

    @Override
    public void doDelivery() {
        System.out.println("물건을 카운터로 배달한다.");
    }

    public static void main(String[] args) {
        OrderProcessTemplate storeOrder = new StoreOrder();
        storeOrder.processOrder(true);
    }
}
```
출력값:
```java
고객은 물건을 선택할 수 있다.
포스기로 물건에 대한 값을 지불한다.
Gift wrap successful
물건을 카운터로 배달한다.
```

processOrder() 메서드는 프로세스 과정을 포함하는 메서드이다. 하위 클래스에서 이 메서드의 구조는 모두 같지만, 주문을 처리하기 위해
사용되는 자세한 세부 내용은 하위 클래스에서 정의되는 내용마다 다르다.

### 3. 템플릿 메서드 패턴의 특징

- 하위 클래스가 다양한 동작을 구현하도록 허용
- 일반적인 메서드의 작업 절차 구조는 추상 클래스에서 한 번 구현되며 필요한 변형은 하위 클래스에서 구현된다.

### 4. 참고 자료

> (서적) 스프링 입문을 위한 자바 객체 지향의 원리와 이해 - 06. 스프링이 사랑한 디자인 패턴

> https://www.geeksforgeeks.org/template-method-design-pattern/