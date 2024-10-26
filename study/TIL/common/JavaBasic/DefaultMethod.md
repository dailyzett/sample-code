## Default methods

> Java 8 이전에는 인터페이스에 추상 메소드만 있을 수 있었다. <br>
> 해당 메소드는 별도의 클래스에서 구현되어야 했고, 인터페이스에 새로운 메소드를 추가하려면 해당 구현 코드를
> 인터페이스를 구현하는 클래스에도 똑같이 제공해야하는 불편한 점이 있었다.<br>
>> 이 문제를 극복하기 위해 Java 8에서 인터페이스를 구현하는 클래스에 영향을 주지 않고 인터페이스가 구현된 메소드를 가질 수 있도록 Default methods를 도입

요약하면, 하위 호환성을 위해서 Default method 를 도입했다.

예를 들어 다음과 같은 인터페이스가 있을 때,
```java
public interface OldInterface {
    public void square(int a);

    default void show() {
        System.out.println("Default Method Executed");
    }
}
```
인터페이스를 사용하려는 클래스에서 굳이 show() 메소드를 재정의 하지 않아도 컴파일 에러가 발생하지 않는다.
```java
public class Demo implements OldInterface{
    public static void main(String[] args) {
        Demo d = new Demo();
        d.square(3);
    }

    @Override
    public void square(int a) {
        System.out.println(a*a);
    }
}
```

만약 Interface 다중 상속으로 인해 default method 이름이 같은 경우, 아래 처럼 사용할 기본 메소드를 명시적으로
지정하거나 Default 메소드를 재정의해야한다.

```java
interface TestInterface1
{
    default void show()
    {
        System.out.println("Default TestInterface1");
    }
}
  
interface TestInterface2
{
    default void show()
    {
        System.out.println("Default TestInterface2");
    }
}
  
// Implementation class code
class TestClass implements TestInterface1, TestInterface2
{
    public void show()
    {
        TestInterface1.super.show();
        TestInterface2.super.show();
    }
  
    public static void main(String args[])
    {
        TestClass d = new TestClass();
        d.show();
    }
}
```