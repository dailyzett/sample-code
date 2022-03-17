### Generic 을 사용하는 이유

----

MemberDto.java

```java
import java.io.Serializable;

public class MemberDto implements Serializable {
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
```

Main.java

```java
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.castingMemberDto();
    }

    private void castingMemberDto() {
        MemberDto dto1 = new MemberDto();
        dto1.setObject(new String());

        MemberDto dto2 = new MemberDto();
        dto2.setObject(new StringBuffer());

        MemberDto dto3 = new MemberDto();
        dto3.setObject(new StringBuilder());
    }
}
```

Object 클래스는 모든 클래스의 부모 클래스이므로, 매개 변수로 어떤 참조 자료형을 넘겨도 상관 없다.
예제 코드는 코드가 흩어져 있지 않아서 해당 자료형을 단번에 파악할 수 있지만 객체지향언어 특성상 코드가 여기저기 흩어져 있는 경우 어떤 자료형을 써야하는 지 모호한 경우가 많다.

```java
String temp1 = (String)dto1.getObject();
```

getObject() 메서드로 값을 가져올 때마다 입력된 참조 자료형과 동일하게 값을 가져와야 하는데, 변수 타입을 혼동해서 적을 여지가 상당히 많다.

```java
public boolean checkDto(MemberDto dto){
        Object tempObject = dto.getObject();
        if(tempObject instanceof String){
            return true;
        }
        return false;
    }
```

instanceof 를 사용해서 타입체크를 하면 해결되지만 매번 값을 가져올 때마다 타입을 점검하는 것은 매우 귀찮다.

> 즉 Generic은 타입 형 변환에서 발생할 수 있는 문제점을 사전에 없애기 위해서 만들어졌다. (Java 5 이후) 여기서 사전이란 컴파일 때 체크하는 것을 의미한다.

제네릭으로 MemberDto를 바꿀 수 있다.

```java
public class MemberDto<T> implements Serializable {
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
```

Generic 을 온전하게 사용하려면 이 클래스를 사용하게 되는 메소드도 일부 바꿔줘야한다.

```java
private void castingMemberDto() {
    MemberDto<String> dto1 = new MemberDto();
    dto1.setObject(new String());

    MemberDto<StringBuffer> dto2 = new MemberDto();
    dto2.setObject(new StringBuffer());

    MemberDto<StringBuilder> dto3 = new MemberDto();
    dto3.setObject(new StringBuilder());

    String temp1 = dto1.getObject();
}
```

temp1 을 보면 앞과 같이 (String)으로 강제 형변환을 해줄 필요가 없다. 만약 사용자가 잘못된 타입으로 치환했을 경우 컴파일 에러가 나오므로, **실행시**에 다른타입으로 잘못 형 변환하여 생길 수 있는 예외를 원천 차단 시킨다.

---
### Generic 타입의 이름 규칙

앞서 사용한 T 에는 사용자가 정의한 모든 문자가 올 수 있지만 Java에서 정의한 기본 규칙에 따르는 것이 좋다.

- E: 요소
- K: 키
- N: 숫자
- T: 타입
- V: 값
- S, U, V: 두 번째, 세 번째, 네 번째에 선언된 타입


---
### Generic<?>

```java
public class ExGeneric<G> {
    G generic;

    public G getGeneric() {
        return generic;
    }

    public void setGeneric(G generic) {
        this.generic = generic;
    }
}
```

다음과 같은 제네릭 클래스를 선언했을 때,

```java
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.call();
    }

    public void call(){
        ExGeneric<String> exGeneric = new ExGeneric<>();
        exGeneric.setGeneric("Hello Generic!");
        callStringMethod(exGeneric);
    }

    public void callStringMethod(ExGeneric<String> exParam){
        String value = exParam.getGeneric();
        System.out.println(value);
    }
}
```

**callStringMethod** 에서 만약 한 가지 자료형이 아닌 다른 객체 자료형을 받기 위해 <?> 를 사용할 수 있다.

```java
public void callGenericMethod(ExGeneric<?> exParam){
        Object value = exParam.getGeneric();
        if(value instanceof String){
            System.out.println(value);
        }
    }
```

다만 해당 함수로 넘어오는 타입이 두 가지 이상일 경우에는 instanceof 를 사용해서 해당 타입 확인 후 코드를 실행시키면 된다.

주의할 점은 callGenericMethod에서 매개변수로 그 객체의 값을 얻어올 수는 있지만 <?> 로 객체를 선언했을 때에는 특정 타입으로 값을 지정하는 것이 불가능하다.

```java
ExGeneric<?> exGeneric = new ExGeneric<String>();
```
이렇게 선언하면 아래의 컴파일 에러가 나온다. 알 수 없는 타입에 String을 지정할 수 없다는 뜻이다.

```
java: incompatible types: java.lang.String cannot be converted to capture#1 of ?
```

---

### <? extends 타입>

제네릭 타입을 부모 클래스를 상속받은 클래스만 사용할 수 있도록 제한하는 예약어이다.

---

Generic Method

앞의 Generic은 매개 변수로 사용된 객체에 값을 추가할 수 없다는 단점이 있다.
이 단점을 보완하려면 다음과 같이 진행한다.

```java
public class Car {
    protected String name;

    public Car(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

다음과 같이 Car 클래스를 선언한다.

```java
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.call();
    }

    public void call(){
        ExGeneric<Car> exGeneric1 = new ExGeneric<>();
        callGenericMethod(exGeneric1, new Car("car"));
    }

    public <T> void callGenericMethod(ExGeneric<T> ex, T addValue){
        ex.setGeneric(addValue);
        T value = ex.getGeneric();
        System.out.println(value);
    }
}
```

callGenericMethod를 보면 기존 파라미터에서 추가하고자 하는 value를 Generic으로 선언했고
메소드 선언부의 앞에 <T> 타입을 선언해놓은 것을 볼 수 있다.

결과값으로 Car{name='car'} 가 출력된다. 
이처럼 메소드 선언시 리턴 타입 앞에 제네릭한 타입을 선언해 주고, 그 타입을 매개 변수에서 사용하면 컴파일 에러가 발생하지 않는다.

