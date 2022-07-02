## Map

> Key 와 value 값으로 이루어져 있는 자바 컬렉션

### 구현 클래스

```java
Interface Map<K,V>
Class AbstractMap<K,V>
```

AbstractMap 추상 클래스를 구현한 Map 클래스들은 다음과 같다.

- ConcurrentHashMap
- ConcurrentSkipListMap
- EnumMap
- HashMap
- IdentityHashMap
- TreeMap
- WeakHashmap

이 중에서 주로 사용하는 Map은 HashMap, TreeMap, LinkedHashMap 이다.

Map 인터페이스를 구현한 클래스로 Hashtable<K,V> 도 있다.<br>
그리고 HashMap과의 차이점은 다음과 같다.

| No  | HashMap                              | Hashtable                                                  |
|:----|:-------------------------------------|:-----------------------------------------------------------|
| 1   | 메소드 동기화 X                            | 모든 메소드가 동기화                                                |
| 2   | 여러 스레드가 접근할 수 있으므로 Thread-safe 하지 못함 | 하나의 thread만 접근 가능하므로 thread-safe                           |
| 3   | 스레드가 대기할 필요가 없으므로 성능은 높다             | 스레드가 대기해야하므로 성능은 낮다                                        |
| 4   | key 와 value 값에 null이 가능하다            | key 와 value 모두 null 불가능, null일 때 null pointer exception 발생 |

### HashTable은 왜 null을 허용하지 않을까?

> HashTable로부터 객체를 온전히 저장 및 검색하려면 equals method 와 hashCode method 를 구현해야한다.<br>
> 하지만 null 은 이 메소드 재정의가 불가능하기 때문. <br>
> hashmap은 hashtable 보다 향상된 버전이고, HashMap이 나중에 만들어졌다.(JDK 1.2)

### 그러면 HashMap은 왜 null 을 허용하게 되었을까?

> HashMap 의 키 값을 null로 설정하면 해당 요소는 배열의 0번 인덱스로 가게 된다. 키 값으로 null 로 설정했을 때 null 자체도 고유한 키
> 값이기 때문에 여러 번 키 설정은 불가능하다. <br>
> JDK 1.0 이후에 값 자체에 null 이 들어가는 상황도 있을거라 생각하고 내부 구현을 null 도 허용하도록 했다.

예를 들면, 어떤 사용자의 생일을 묻는 경우라고 한다면 사용자는 key, 생일은 value 가 된다. <br>
여기서 값을 찾을 때 다음과 같은 상황이 발생할 수 있다.
- 사용자 자체가 없는 경우
- 사용자가 존재하지만 생일을 입력하지 않은 사용자를 구별해야하는 경우

1번은 Hashtable 로도 구현이 가능하지만 2번은 Hashtable 로의 구현이 불가능하다. <br>
이럴때는 어쩔 수 없이 HashMap 을 사용해야만 한다.

----

### 생성자

```java
HashMap()
HashMap(init initialcapacity)
HashMap(int initialCapacity, float loadFactor)
HashMap(Map<? extends K, ? extends V> m)
```

HashMap에 객체가 들어가면 hashCode() 메소드의 결과 값에 따라 bucket이라는 목록 형태의 바구니가 만들어진다.
서로 다른 키 인데 hashCode 리턴 값이 같다면 bucket에 여러가지 값이 들어갈 수 있다.

따라서 get 메소드가 호출되면 hashCode()의 결과를 확인하고, bucket에 들어간 목록에 데이터가 여러개인 경우
equals() 를 호출하여 동일한 값을 찾게 된다.

따라서 사용자가 키가 되는 객체를 직접 작성할 때에는 equals()와 hashCode() 를 꼭 재정의 해야한다.

---

### HashMap 에서 객체의 값을 확인하는 방법

```java
HashMap<Integer, String> hashMap = new HashMap<>();

hashMap.put(1, "A");
hashMap.put(2, "B");
hashMap.put(3, "C");

Set<Map.Entry<Integer, String>> entries = hashMap.entrySet();
for (Map.Entry<Integer, String> tempEntry : entries) {
    System.out.println(tempEntry.getKey() + " " + tempEntry.getValue());
}
```

entrySet() 메소드를 이용해 Entry 객체를 리턴받아 Set 컬렉션에 넣고,
getKey()와 getValue()로 키와 값을 모두 가져 올 수 있다.

---

### Properties 클래스

> 이 클래스는 Hashtable를 확장한 클래스로 Map 인터페이스에서 제공하는 모든 메소드를 사용할 수 있다.

```java
Properties prop = System.getProperties();

Set<Map.Entry<Object, Object>> entries = prop.entrySet();
for (Object o : entries) {
    System.out.println(o);
}
```

Properties 를 이용해 시스템의 속성값을 쉽게 구할 수 있다.
<br> 실행결과 : 

```
java.specification.version=17
sun.cpu.isalist=amd64
sun.jnu.encoding=MS949
java.class.path=D:\practiceFolder\out\production\practiceFolder
java.vm.vendor=Oracle Corporation
sun.arch.data.model=64
user.variant=
java.vendor.url=https://java.oracle.com/
java.vm.specification.version=17
os.name=Windows 10
...
...
```






