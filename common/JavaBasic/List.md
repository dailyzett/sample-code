## List

### List 인터페이스

- Vector (Thread safe)
  - Stack (Vector 클래스를 확장하여 만듦)
- ArrayList (Thread unsafe)
- LinkedList (목록에도 속하지만 큐에도 속한다)
---


### Class ArrayList<E>

#### 상속 관계

- java.lang.Object
  - java.util.AbstractCollection<E>
    - java.util.AbstractList<E>
      - java.util.ArrayList<E>

#### All implemented interface

Serializable, Cloneable, Iterable<E>, Collection<E>, List<E>, RandomAccess

#### 선언 방법

```java
ArrayList<String> list = new ArrayList<String>();
```

자바 7부터는 new 뒤의 	&#60;String&#62;은 생략이 가능하다.

```java
ArrayList<String> list = new ArrayList<>();
```

#### 생성자

ArrayList() 기본 생성자는 객체를 저장할 공간이 10인 ArrayList 를 만든다.
10개 이상의 데이터가 들어가면 크기를 늘이는 작업이 내부에서 자동으로 수행된다.
따라서 데이터 크기가 한정적인 상황이라면 생성자 매개변수를 지정해 초기 크기를 지정가능하다.

```java
ArrayList<String> list = new ArrayList<>(200);
```

#### 주의 사항

```java
ArrayList<String> list1 = new ArrayList<>();
ArrayList<String> list2 = new ArrayList<>();

list1.add("first");
list2.add("second");

list1 = list2;

System.out.println(list1.get(0));
}
```

다음 코드 실행 시 결과 값은 first가 아닌 second가 출력된다.

= 연산자를 사용해서 ArrayList 를 복사하면 그 객체를 참조하고 있는 주소값 까지
사용하겠다는 뜻이 되기 때문이다.

따라서 Collection 객체를 복사할 일이 있을 때는 생성자를 이용하거나 addAll() 메서드를 사용하는 것이 좋다.

---

### toArray()

| 리턴 타입    | 메소드 이름 및 매개 변수 | 설명                                           |
|:---------|:---------------|:---------------------------------------------|
| Object[] | toArray()      | ArrayList 객체에 있는 값들을 Object[] 타입의 배열로 생성     |
| <T> T[]  | toArray(T[] a) | ArrayList 객체에 있는 값들을 매개 변수로 넘어온 T 타입의 배열로 생성 |


> toArray() 메서드는 Object 타입의 배열로만 리턴을 하므로 <br>제네릭을 사용한 ArrayList 는
toArray(T[] a) 를 사용하는 것이 더 좋다.


```java
ArrayList<String> stringArrayList = new ArrayList<>();
stringArrayList.add("1");
stringArrayList.add("2");

stringArrayList.toArray(new String[0]); // new String[0]은 의미없이 타입만을 지정하기 위해 사용
for (String x : temp) {
System.out.println(x);
}
```




