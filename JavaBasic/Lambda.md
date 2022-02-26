## 람다 표현식

앞서 람다는 인터페이스 메소드가 하나인 것들만 적용이 가능하다.(ex. Runnable, Comparator...)

직접 사용자가 인터페이스를 만드는 것도 가능하다.

람다 표현식은 다음과 같이 3 부분으로 구성되어 있다.

| 매개 변수          | 화살표 토큰 |처리 식|
|:---------------|:-------|:----|
| (int x, int y) | ->     |x + y|

해석하면 매개 변수 x, y를 받아 x+y 를 리턴해준다는 의미이다.

인터페이스를 하나 선언하고 해당 인터페이스의 구현 부분을 람다 표현식을 이용해 처리하는 코드이다.
```java
public interface Calculate {
    int operation(int a, int b);
}
```

```java
public class Demo{
    public static void main(String[] args) {
        Calculate calAdd = (a, b) -> a + b;
        System.out.println(calAdd.operation(3, 5));
    }
}

//Output:
//8
```

Calculate 인터페이스에 메소드를 하나 더 추가하면 람다 표현식을 사용할 수 없는데, Interface에 하나의
메소드만 선언해놓은 상태라면 다른 사람의 입장에서 메소드를 추가해도 되는지 안되는지 모호할 수 있다.

```java
java: incompatible types: Calculate is not a functional interface
    multiple non-overriding abstract methods found in interface Calculate
```

그래서 자바는 @FunctionalInterface 라는 어노테이션을 사용할 수 있다.
이 어노테이션은 다른 사람이 메서드를 추가했을 때 쉽게 알아볼 수 있도록 다음과 같이 컴파일 에러를 발생시킨다.

```java
java: Unexpected @FunctionalInterface annotation
  Calculate is not a functional interface
    multiple non-overriding abstract methods found in interface Calculate
```


다음은 Thread 의 Runnable 을 사용한 코드이다.

```java
public class Demo{
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        new Thread(runnable).start();
    }
}
```

람다식으로 간단하게 다음과 같이 표현이 가능하다

```java
public class Demo{
    public static void main(String[] args) {
        new Thread(()-> System.out.println(Thread.currentThread().getName())).start();
    }
}
```

## Stream

Stream 은 연속된 자료를 처리하기 위해 Java 8 에서 추가된 기능이다.

```java
list.stream().filter(x-> x>10).count()
```

stream은 세 개의 구조를 가진다.

- 스트림 생성(stream)
  - 컬렉션의 목록을 스트림 객체로 변환
- 중개 연산(filter)
  - 스트림 객체를 사용하여 중개 연산 처리, 하지만 결과 리턴은 불가능
- 종단 연산(count)
  - 작업된 내용을 바탕으로 결과 리턴

여기서 중개 연산은 있을 수도 있고 없을 수도 있다.

배열을 순회하면서 출력하려면 이전에는 for 문으로 접근했지만 foreach 를 이용하면 한 줄 출력이 가능하다.

```java
Arrays.stream(arr).forEach((int x)-> System.out.print(x + " "));
```

#### map()

map() 메서드는 데이터를 특정 데이터로 변환하는 역할을 한다.

예를 들어서 DTO 클래스 중에서 내부에 있는 String 값만 뽑아서 출력하고 싶다고 할 때
map() 메서드를 중개 연산으로 넣으면 편리하다.

```java
public class Demo{
    public static void main(String[] args) {
        ArrayList<MemberDTO> list = new ArrayList<>();
        list.add(new MemberDTO("kim", 12));
        list.add(new MemberDTO("park", 24));

        list.stream().map(member -> member.getName()).forEach(x -> System.out.println(x));
    }
}

```
출력값:
```java
kim
park
```

이름과 나이를 입력받는 MemberDTO ArrayList를 생성해 stream 객체로 변환한다.
이 stream 객체는 map() 메서드를 거치면서 MemberDTO 내부의 name 필드값을 가져오도록 설정한다.

만약 map() 이 빠진 상태라면 MemberDTO 객체 정보를 불러오므로 출력값은 객체 주소가 된다.
map 메서드나 forEach 내부의 매개변수는 무엇으로 하든 사용자 마음이다.

---

#### 메소드 참조 (::)

메소드 참조는 Java 8 에서 처음 나왔고 종류는 4가지이다.

|종류| 예                                 |
|:---|:----------------------------------|
|static 메소드 참조| ContainingClass::staticMethodName |
|특정 객체의 인스턴스 메소드 참조|containingObject::instanceMethodName|
|특정 유형의 임의의 객체에 대한 인스턴스 메소드 참조|containingType::methodName|
|생성자 참조|ClassName::New|

메소드 참조를 이용하면 다음과 같이 변경이 가능하다.
```java
list.stream().map(MemberDTO::getName).forEach(System.out::println);
```

System.out::println은 System 클래스에 선언된 out 변수의 println() 메서드를 호출하는
것이므로 변수에 선언된 메서드 호출을 의미한다.

즉 System.out::println 은 **특정 객체의 인스턴스 메소드 참조**이다.

MemberDTO::getName 은 static 클래스가 아님에도 :: 를 통해 불러오는데 이 경우는 **특정
유형의 임의의 객체에 대한 인스턴스 메소드 참조** 유형이라고 볼 수 있다.

---

#### stream map()

> map() 을 사용해 스트림에서 처리하는 값들을 중간에 변경하는 것이 가능하다.

각 원소의 값을 3배 곱하고 그 값들을 더한 결과값을 출력하는 코드를 map 메서드를 이용해서
간단하게 작성할 수 있다.

```java
List<Integer> integerList = intList.stream().map(x -> x * 3).toList();
int result = 0;
for(Integer x : integerList) result += x;
System.out.println(result);
```

#### stream filter()

DTO 필드 정보들 중에 사용자가 원하는 정보를 선별하고 싶을 때 filter() 메서드를 이용한다

다음은 DTO 중에서 age(나이) 가 24이상인 사람의 이름을 출력하는 코드이다

```java
public class Demo{
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<MemberDTO> memberDTOList = new ArrayList<>();

        memberDTOList.add(new MemberDTO("kim", 12));
        memberDTOList.add(new MemberDTO("park", 24));

        memberDTOList.stream().filter(member -> member.getAge() >= 24).forEach(member -> System.out.println(member.getName()));
    }
}

// 출력값 : park
```


---

#### stream 정리

스트림은 Collection 과 같이 목록을 처리할 때 유용하게 사용된다.

- 스트림 생성 - 중간 연산 - 종단 연산으로 구분된다
- 중간 연산은 결과 값으로 Stream 타입을 리턴하므로 여러개의 중간 연산을 연결할 수 있다.
- 종단 연산은 숫자값을 리턴하거나 목록형 데이터를 리턴한다.

