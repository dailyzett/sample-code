## Java 7 변경점

### 달라진 숫자 표현법

1. 숫자 앞에 "0b" 를 붙임으로써 2진수 표현 추가
2. 돈이나 숫자 단위에 1000단위마다 "_" 표시 가능
```java
int a = 1_000_000;
```


### Switch 문장 확장

> Java 7부터 Switch 문장에 정수형 뿐만이 아니라 String 사용 가능

- String이 null 객체의 경우 nullPointerException 이 나오므로 주의


### 제네릭

```java
// 자바7 부터 오른쪽에 있는 <> 은 생략 가능
HashMap<String, Integer> map = new HashMap<>();
```

---


### Non reifiable varargs Type

> 제네릭을 사용하지 않는 버전과의 호환성을 위해서 제공했던 기능 때문에 발생

- reifiable
  - 실행시에도 타입 정보가 남아있는 타입을 의미
- non reifiable
  - 컴파일 시 타입 정보가 손실되는 타입을 의미

```java
ArrayList<ArrayList<String>> list = new ArrayList<>();
ArrayList<String> newDataList = new ArrayList<>();
ArrayList<String> newDataList2 = new ArrayList<>();

newDataList.add("A");
newDataList2.add("A");
Collections.addAll(list, newDataList, newDataList2);
System.out.println(list);
```

addAll 메소드는 가변 매개변수를 이용한다. 이 경우 내부적으로 Object 배열로 처리된다.

```java
public static boolean addAll(java.util.Collection c, java.lang.Object[] elements)
```

Object 배열로 처리되면 잠재적으로 문제가 발생할 수 있기 때문에 Java 7 이전 버전에서는 @SafeVarargs 라는
어노테이션을 메소드 선언부에 추가해줘야 됐다.

Java 7 부터는 addAll() 메소드에 자동 추가되어 있음.<br>
해당 어노테이션은 다음과 같은 조건에서 사용 가능

- 가변 매개 변수를 사용
- final 이나 static 으로 선언되어 있어야함


@SafeVarargs 어노테이션은

- 가변 매개 변수가 reifiable 타입이고,
- 메소드 내에서 매개 변수를 다른 변수에 대입하는 작업을 수행하는 경우

컴파일러에서 경고를 출력한다.

---

### 예외 보완

```java
// 기존 Exception 종류마다 길게 늘어졌던 catch 문을 이제 "|" 로 한 번에 묶는 것이 가능

try {
    Scanner sc = new Scanner(new File("filename"), "UTF-8");
    System.out.println(sc.nextLine());
} catch (FileNotFoundException | NullPointerException e) {
    e.printStackTrace();
}
```

- try-with-resource 문 추가

Java 7에는 AutoCloseable 이라는 인터페이스가 추가되었기 때문에 try-with-resources를 사용할 때에는
별도로 close()를 호출할 필요가 없다.

따라서 Socket 통신과 DatagramSocket 통신에서 finally 구문에서 close() 를 하기 위해 지저분했던 코드를
다음과 같이 변경이 가능하다.

```java
try(Scanner sc = new Scanner(new File("filename"), "UTF-8");) {
    System.out.println(sc.nextLine());
} catch (FileNotFoundException | NullPointerException e) {
    e.printStackTrace();
}
```

또한 try 구문 내부에서 변수를 선언했어야 됐지만 Java 7부터는 try() 안 쪽에서 객체 생성이 가능하다.

---

### Closeable 인터페이스

Closeable 인터페이스와 관련있는 클래스는 [공식 API 문서](https://docs.oracle.com/javase/7/docs/api/java/lang/AutoCloseable.html) 에서 찾을 수 있다.




