
예를 들어 ArrayIndexOutOfBoundsException 의 경우 배열 범위를 벗어나지 않으면 발생하지 않지만 벗어나는 순간 예외가 발생한다. 그리고 runtime exception은 컴파일시 체크를 하지 않는다.

대표적으로
* NullPointerException
* NumberFormatException
* ClassCastException
* IndexOutOfBoundsException
  etc...

가 있다.


### Exception과 Error 공통 부모 클래스

> java.lang.Throwable 클래스이다.

Throwable 클래스에서 대표적으로 많이 사용되는 메서드는 아래의 3가지다

* getMessage()
    * 예외메시지를 String 형태로 제공받음
* toString()
    * String 형태로 제공받는 것은 같으나 getMessage()보다 좀 더 자세하다
* printStackTrace()
    * 가장 첫 줄에는 예외 메시지를 출력하고 두 번째 줄부터는 예외가 발생하게 된 메소드들의 스택 트레이스를 출력한다.



### throw 와 throws

```java
public class Example {
    public static void main(String[] args) {

        int test = 10;

        try{
            if(test > 9){
                throw new Exception("Number is over than 10");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
```

예제에서 throw는 조건에 따라 강제로 예외를 발생시키고 catch 블록으로 보낸다.
이 예제를 실행 시 아래와 같은 exception이 발생한다.

```
java.lang.Exception: Number is over than 10
	at Example.main(Example.java:8)
```

catch 블록에 해당되는 예외가 없을 때는, 예외가 발생된 메소드를 호출한 메소드로 예외를 던진다. 이럴 때 사용하는 것이 **throws** 이다.

```java
public class Example {
    public static void main(String[] args) throws Exception {

        int test = 10;

        if (test > 9) {
            throw new Exception("Number is over than 10");
        }
        
    }
}
```

throws 를 이용하면 try-catch 블록을 없앨 수 있다.
다만 메소드 선언부에 throws 선언을 했기 때문에 해당 메소드를 호출한 메소드는 반드시 try-catch 블록으로 해당 메소드를 감싸주어야 한다.


## Exception 상속 구조

Exception 상속 구조는 다음과 같다.

```text
java.lang.Object
    java.lang.Throwable
        java.lang.Exception
```

### java.lang.Throwable

아래 내용은 해당 클래스를 Java SE 18 공식 문서에서 번역한 것이다.

The Throwable class is the superclass of all errors and exceptions in the Java language. Only objects that are instances of this class (or one of its subclasses) are thrown by the Java Virtual Machine or can be thrown by the Java throw statement. Similarly, only this class or one of its subclasses can be the argument type in a catch clause. For the purposes of compile-time checking of exceptions, Throwable and any subclass of Throwable that is not also a subclass of either RuntimeException or Error are regarded as checked exceptions.
Instances of two subclasses, Error and Exception, are conventionally used to indicate that exceptional situations have occurred. Typically, these instances are freshly created in the context of the exceptional situation so as to include relevant information (such as stack trace data).

> Throwable 클래스는 자바에서 모든 에러와 예외의 상위 클래스이다.
> 이 클래스의 인스턴스 객체 혹은 이 클래스의 하위 클래스 중 하나는 JVM에 의해 던져지거나 자바 `throw` 구문에 의해 던져질 수 있다.
> 그리고 이 클래스 또는 이 클래스의 서브 클래스는 `catch` 구문의 인자(argument)가 될 수 있다.
> 컴파일 시간의 예외 검사 목적으로 `Throwable`과 RuntimeException 혹은 에러의 하위 클래스가 아닌 `Throwable`의 모든 하위 클래스는 checked exception 으로 간주한다.

Instances of two subclasses, Error and Exception, are conventionally used to indicate that exceptional situations have occurred. Typically, these instances are freshly created in the context of the exceptional situation so as to include relevant information (such as stack trace data).

> 에러 및 예외 서브 클래스의 인스턴스는 일반적으로 예외적인 상황이 발생했음을 나타내는 데 사용된다.
> 일반적으로 이러한 인스턴스는 관련 정보(ex. 스택 추적 데이터)를 포함하기 위해 예외 상황의 컨텍스트에서 새롭게 생성된다.

A throwable contains a snapshot of the execution stack of its thread at the time it was created. It can also contain a message string that gives more information about the error. Over time, a throwable can suppress other throwables from being propagated. Finally, the throwable can also contain a cause: another throwable that caused this throwable to be constructed. The recording of this causal information is referred to as the chained exception facility, as the cause can, itself, have a cause, and so on, leading to a "chain" of exceptions, each caused by another.

> `Throwable`은 생성될 때 쓰레드 실행 스택의 스냅샷을 포함한다.
> 또한 에러에 관해서 추가 정보를 주는 string 메시지를 포함한다.
> 시간이 지나면서 `Throwable`은 다른 `Throwable`이 전파되는 것을 막을 수 있다.
> 그리고 `Throwable`은 다음과 같은 원인을 포함할 수 있다 : 이 `Throwable`이 생성한 또다른 `Throwable` 클래스.
> 이렇게 예외가 발생하는 원인 자체에 다른 원인이 있을 수 있고 기타 등등에 의해 발생하는 예외의 연쇄(chain)으로 이어질 수 있기 때문에
> 이것을 **연결된 예외 기능(chained exception facility)** 라고 한다.

One reason that a throwable may have a cause is that the class that throws it is built atop a lower layered abstraction, and an operation on the upper layer fails due to a failure in the lower layer. It would be bad design to let the throwable thrown by the lower layer propagate outward, as it is generally unrelated to the abstraction provided by the upper layer. Further, doing so would tie the API of the upper layer to the details of its implementation, assuming the lower layer's exception was a checked exception. Throwing a "wrapped exception" (i.e., an exception containing a cause) allows the upper layer to communicate the details of the failure to its caller without incurring either of these shortcomings. It preserves the flexibility to change the implementation of the upper layer without changing its API (in particular, the set of exceptions thrown by its methods).

> `Throwable`에 원인이 있을 수 있는 이유는 `Throwable`을 던지는 클래스가 하위 계층 추상화 위에 구축되고 하위 계층의 실패(failure)
> 로 인해 상위 계층의 작업이 실패하기 때문이다.
> 일반적으로 상위 계층에서 제공하는 추상화와 관련이 없기 때문에 하위 계층에서 던진 `Throwable`이 외부로 전파되도록 하는 것은 좋지 않은 설계이다.
> 그리고 이러한 설계는 하위 계층의 예외가 checked exception이라고 가정하고 상위 계층의 API를 상위 계층의 구현 정보와 연결한다.
> "래핑된 예외(wrapped exception)" 즉 원인이 포함된 예외를 발생시키면 상위 계층이 이러한 결점을 일으키지 않은 채로 호출자에게
> 실패 세부 정보를 전달할 수 있다. 이러한 점은 API(특히 메서드에서 발생하는 예외들)를 변경하지 않고 상위 계층의 구현을 변경할 수 있는
> 유연성을 유지한다.

A second reason that a throwable may have a cause is that the method that throws it must conform to a general-purpose interface that does not permit the method to throw the cause directly. For example, suppose a persistent collection conforms to the Collection interface, and that its persistence is implemented atop java.io. Suppose the internals of the add method can throw an IOException. The implementation can communicate the details of the IOException to its caller while conforming to the Collection interface by wrapping the IOException in an appropriate unchecked exception. (The specification for the persistent collection should indicate that it is capable of throwing such exceptions.)


> `Throwable`이 원인을 가질 수 있는 두 번째 이유는 `Throwable`을 던지는 메서드가 원인을 직접 던지는 것을 허용하지 않는
> 범용 인터페이스의 규칙을 따라야하기 때문이다.
> 예를 들어 영속성 컬렉션이 `Collection` 인터페이스로 구현되어있고 영속성(persistence)이 java.io 위에 구현되어 있다고 가정해보자.
> 그리고 `add()` 메서드의 내부가 `IOException`을 던질 수 있다고 가정한다.
> 구현된 클래스는 `IOException`이 적절한 unchecked exception에 래핑하여 `Collection` 인터페이스의 규칙을 지키면서 `IOException`
> 의 세부 정보를 호출자에게 전달할 수 있다. (영속성 컬렉션에 대한 사양은 이러한 예외를 던질 수 있음을 나타내야 한다.)

A cause can be associated with a throwable in two ways: via a constructor that takes the cause as an argument, or via the initCause(Throwable) method. New throwable classes that wish to allow causes to be associated with them should provide constructors that take a cause and delegate (perhaps indirectly) to one of the Throwable constructors that takes a cause. Because the initCause method is public, it allows a cause to be associated with any throwable, even a "legacy throwable" whose implementation predates the addition of the exception chaining mechanism to Throwable.

> 원인은 두 가지 방법으로 `Throwable`과 설정할 수 있다.
> 원인을 인자로 사용하는 생성자 또는 `initCause(Throwable)` 메서드를 통해서 설정 가능하다.
> 원인을 설정하려는 새로운 `Throwable` 클래스는 원인을 제공하는 생성자를 제공하고 원인을 제공하는 `Throwable` 생성자 중 하나에
> (간접적으로) 위임해야 한다.
> `initCause` 메서드는 public 이라서 원인이 모든 `Throwable`과 연관될 수 있다.
> 그리고 이것은 예외 연걸 메커니즘이 추가되기 이전에 구현된 `legacy Throwable` 도 마찬가지다.

By convention, class Throwable and its subclasses have two constructors, one that takes no arguments and one that takes a String argument that can be used to produce a detail message. Further, those subclasses that might likely have a cause associated with them should have two more constructors, one that takes a Throwable (the cause), and one that takes a String (the detail message) and a Throwable (the cause).

> 규칙에 따라 `Throwable` 클래스와 그 하위 클래스에는 두 개의 생성자가 있다.
> 하나는 인자를 사용하지 않는 것이고 다른 하나는 세부 메시지를 생성하는 데 사용할 수 있는 String 인자를 사용하는 것이다.
> 더 나아가서 이 클래스들과 관련된 원인이 있을 수 있는 하위 클래스에는 두 개의 생성자가 더 있어야 한다.
> 하나는 `Throwable(원인)`을 사용하고 다른 하나는 `String(세부 메시지) 및` `Throwable(원인)`을 사용한다.






