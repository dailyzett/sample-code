# 1. Optional


> null 체크를 하지 않고 NullPointerException 을 피하기는 거의 불가능한 일이지만, 너무 많은 null 체킹이 일어나면 코드가 번잡해진다.
> <br>이를 극복하기 위해 Java 8에서 java.util 패키지에 새로운 클래스 Optional 도입
> <br>Optional 을 이용하면 null 체크 코드를 많이 작성하지 않아도 깔끔한 코드를 작성하는데 도움을 준다.

선언은 다음과 같이 되어있다.

```java
public final class Optional<T> extends Object
```
특징:
- final class 로 선언되었기 때문에 객체의 new 로 재생성이 불가능하다.
- Optional 을 사용하여 리턴할 대체 값이나 실행할 대체 코드를 지정할 수 있다.

아래 코드는 NullPointerException이 발생한다.

```java
public class OptionalDemo {
    public static void main(String[] args) {
        String[] words = new String[10];
        String word = words[0].toLowerCase();
        System.out.println(word);
    }
}
```

```java
Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.toLowerCase()" because "words[0]" is null
	at OptionalDemo.main(OptionalDemo.java:4)
```

이것을 Optional 클래스를 사용하면 다음과 같이 고칠 수 있다.

```java
public class OptionalDemo {
    public static void main(String[] args) {
        String[] words = new String[10];

        Optional<String> checkNull = Optional.ofNullable(words[0]);
        
        //isPresent()는 값이 있을 때 true 를 반환, 반대는 false 를 반환
        if (checkNull.isPresent()) {
            String word = words[0].toLowerCase();
            System.out.println(word);
        } else {
            System.out.println("word is null");
        }
    }
}
```


Optional 클래스는 아래의 static 메소드를 지원한다.

| Method | Description                                   |
|:-------|:----------------------------------------------|
|empty()| 비어있는 Optional instance를 리턴                    |
|of(T value)| 현재 null 값이 아닌 지정된 Optinal 을 반환                |
|ofNullable(T value)| null 이면 빈 Optional 반환, 아니라면 값이 담긴 Optional 반환 |

사용예시:

```java
import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        String[] words = new String[10];

        words[1] = "banana";

        Optional<String> empty = Optional.empty();
        System.out.println(empty);

        Optional<String> value = Optional.of(words[1]);
        System.out.println(value);
    }
}

//출력값 : 
//Optional.empty
//Optional[banana]
```

또한, Optional 은 static 메소드 뿐만 아니라 인스턴스 메소드도 제공한다.<br>
[지원하는 모든 메소드](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)

[※ Java api 에서 Static Methods, Instance Methods, Concrete Methods 차이](https://docs.oracle.com/javase/8/docs/api/java/util/Methods.html)

다음 코드는 Optional 의 static method 와 instance method 를 사용한 예시이다.
```java
String[] words = new String[10];
words[1] = "banana";

Optional<String> value = Optional.of(words[1]);
Optional<String> value2 = Optional.ofNullable(words[0]);

System.out.println(value.get());
System.out.println(value2.isPresent());

//출력값:
//banana
//false
```

## 1.1 Optional 사용 시 주의점

<code>Optional</code>은 NullPointerException을 방지하기 위해 Java 8부터 지원하는 객체이다.
메서드가 반환할 결과값이 '없음'을 명확하게 표현할 필요가 있고 <code>null</code>을 반환하면 에러를 유발할 가능성이 높은
상황에서 메서드의 반환 타입으로 <code>Optional</code>을 사용하자는 것이 <code>Optional</code>을 만든 주 목적이다.

그런데 <code>Optional</code>은 모든 NullPointerException을 처리하기 위해 만들어진 것이 아니기 때문에
사용할 때 주의할 점이 있다.

<code>Optional</code>을 사용하면 안 되는 상황은 아래와 같다.

- 도메인 모델 계층(<code>Optional</code>은 직렬화 인터페이스 미구현)
- DTO(도메인 모델 계층과 같은 이유)
- 메서드의 입력 매개 변수
- 생성자 매개 변수

### Serializable 미구현

<code>Optional</code>는 설계할 때부터 필드 사용을 염두해두지 않았다. 따라서 직렬화 인터페이스를 구현하지 않았다.
만약 필드에 <code>Optional</code>이 등장하는 경우, 직렬화를 사용하는 라이브러리나 프레임워크에서 문제가 생길 수 있다.

### 생성자와 메서드 매개 변수

<code>Optional</code>을 입력 매개 변수로 사용하는 경우 호출할 때마다 <code>Optional</code>을 생성해서
인자로 전달해줘야 한다. 하지만 호출되는 쪽에서는 인자가 <code>Optional</code>이든 <code>null</code>이든
값을 체크해주는게 중요하다. 그리고 null 타입 체크에 비해 <code>Optional</code> 은 생성 비용이 크다.
따라서 이런 경우, <code>null</code> 체크만 진행하는 것이 옳다.

