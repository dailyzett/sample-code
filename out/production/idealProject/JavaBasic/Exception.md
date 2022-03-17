
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

catch 블록에 해당되는 예외가 없을 때는, 예외가 발생된 메소드를 호출한 메소드로 예외를 던진다. 이럴 때 사용하는 것이 ** throws ** 이다.

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