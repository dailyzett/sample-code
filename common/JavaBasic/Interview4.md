## String Literal 과 String object

```java
String str = "Hello";
```

- 문자열 리터럴로 선언 시 내부적으로 intern() 메소드 호출
- 이 메소드는 String 객체의 내부 풀(pool)을 참조한다.
- 이미 Hello 값이 있는 경우 str 은 해당 값을 참조하고 새로운 String 객체는 생성되지 않는다.

```java
String str = new String("Hello")
```

- 문자열 객체를 new 로 신규 생성.
- JVM은 Hello 가 참조되는 pool 에 있더라도 새로운 String 객체를 생성


> String 객체의 new 선언은 실행될 때마다 새로운 문자열을 생성하기 때문에 String Literal 선언보다
> 실행하는 데 많은 시간이 걸린다. <br>

