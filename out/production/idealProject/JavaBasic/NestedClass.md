## Nested Class
> Java는 클래스 안에 클래스가 들어갈 수 있는데 이것을 Nested Class 라고 부른다.

Nested Class는 선언 방법에 따라 두가지로 나뉜다.

* Static nested class
* **inner class**

그리고 **inner class** 는 다시 두개로 나뉘는데 이름의 유무에 따라서
* Local inner class
* Anonymous inner class

로 나뉜다.



### Static nested class 의 특징

> 내부 클래스는 감싸고 있는 외부 클래스의 어떤 변수도 접근할 수 있다. 하지만, Static nested 클래스를 그렇게 사용하는 것은 불가능하다.

그렇다면, 사용하는 이유는 뭘까?
* 겉으로 보기에는 유사하지만, 내부적으로 구현이 달라야할 때 static nested class 를 사용한다.


### 내부 클래스와 익명 클래스

```java
public class OuterInner {
    class Inner{
        private int value = 0;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
```

다음과 같은 Inner Class 가 있을 때 이걸 불러오려면

```java
   private void makeInnerObject() {
        OuterInner outer = new OuterInner();
        OuterInner.Inner inner = outer.new Inner();

        inner.setValue(30);
        System.out.println(inner.getValue());
    }
```
먼저 Inner 클래스를 감싸고 있는 OuterInner 클래스의 객체를 생성하고 (여기서 outer)
이 outer 객체를 통해서 inner 클래스의 객체를 만들어 낼 수 있다.


아무리 생각해도 복잡한 데 왜 사용할까?

>하나의 클래스에서 어떤 공통적인 작업을 수행하는 클래스가 필요한데 다른 클래스에서는 그 클래스가 전혀 필요 없을 때 inner class를 사용한다.

대표적으로는 이벤트 리스너를 처리할 때 이다. 버튼을 클릭하거나 키보드를 입력하는 등 이벤트가 발생할 때 각 컴포넌트마다 수행해야 하는 작업은 다를 수 밖에 없다.

이럴 땐 별도의 클래스를 새로 생성하는 것보다 내부 클래스를 만드는 것이 훨씬 편하다.

그리고 내부 클래스보다 더 간단한 방법은 익명 클래스를 만드는 것이다.
아래는 예시 코드이다

```java
public interface EventListener {
    public void onClick();
}
```

```java
public class UserButton {
    public UserButton() {
    }

    private EventListener listener;

    public EventListener getListener() {
        return listener;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public void onClickProcess() {
        if (listener != null)
            System.out.println("click process");
    }
}
```


```java
public class AnonymousMain {
    public static void main(String[] args) {
        AnonymousMain app = new AnonymousMain();
        app.setButtonListener();
    }

    private void setButtonListener() {
        UserButton button = new UserButton();
        button.setListener("???");
        button.onClickProcess();
    }
}

```

이러면 setListener 내부에 EventListener 인터페이스 메소드를 구현해야하는데 앞에서 설명했던 것처럼 이벤트는 각 컴포넌트마다 동작이 달라 클래스를 매번 생성하기는 귀찮고, 메모리 낭비도 있다.

```java
button.setListener(new EventListener() {
            @Override
            public void onClick() {
                System.out.println("Button click!");
            }
        });
```

따라서 다음과 같이 익명 클래스로 메소드를 구현하면 된다
(요즘 IntelliJ IDE 성능이 워낙 좋아서 괄호 안에 new Event~만 입력해도 알아서 저렇게 틀을 만들어준다). 다만, 클래스 이름과 객체 이름이 없기 때문에 다른 곳에서는 재사용이 불가능하다.