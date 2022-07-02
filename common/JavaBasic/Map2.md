## Map 의 종류

>모든 Map 컬렉션은 java.util.AbstractMap<K,V> 를 상속받는다. 그리고 Java 8 API 공식문서 기준으로
> 그 종류는 총 7개이다.


- [HashMap](https://velog.io/@dailyzett/HashMap-%EC%9D%80-%EC%96%B4%EB%96%BB%EA%B2%8C-%EA%B5%AC%ED%98%84%EB%90%98%EC%96%B4%EC%9E%88%EA%B8%B8%EB%9E%98-%ED%82%A4%EC%99%80-%EA%B0%92%EC%9D%84-%EB%B9%A0%EB%A5%B4%EA%B2%8C-%EB%A7%A4%ED%95%91%ED%95%A0-%EC%88%98-%EC%9E%88%EC%9D%84%EA%B9%8C)
  - HashMap 의 내용은 개인 블로그에 정리 완료


### ConcurrentHashMap

기존 Hashtable 은 Thread-safe 했지만 효율이 떨어졌다. 동시성(concurrency)과 처리량에 있어 퍼포먼스를
보여주기에 좋지 않은 클래스였기 때문

ConcurrentHashMap 은 더 나은 성능을 위해 테이블 버킷으로 노드 배열을 구성하고 업데이트 작업은
CAS(Compare And Swap) 작업을 이용한다

> synchronize(동기화) 달성을 위해 멀티쓰레딩에서 사용되는 원자적 명령어이다.
> Java에서는 이를 위해 Atomic 변수를 지원한다.

테이블 버킷에 데이터를 삽입할 때 지연된 초기화(Lazy initialization)를 통해 삽입한다.

> Lazy initialization 은 객체 생성, 값 계산, 기타 비용이 많이 드는 과정을 처음 필요한 시점까지
> 지연시키는 기법을 말한다.

버킷 안의 첫번째 노드를 잠금(locking)으로써 각각의 버킷들을 독립적으로 lock을 걸 수 있다.
읽기 작업은 block 되지 않기 때문에 update 작업이 일어날 때 비용이 최소화 된다.

필요한 세그먼트 수는 테이블에 접근하려는 스레드 수와 연관이 있다. 따라서 대부분의 업데이트 작업 시
할당되는 세그먼트 수는 하나를 넘지 않는다.

> 세그먼트란?
> > 컴퓨터의 주기억장치를 효율적으로 관리하기 위해 데이터를 일정한 크기의 논리적 단위로 나뉘어서 관리한다.
> > 이 때 논리적 단위를 세그먼트(Segment)라고 한다

ConcurrentHashMap 의 생성자에는 사용할 예상 쓰레드 수를 제어하기 위해 concurrencyLevel 파라미터를
제공하는데, 쓰레드 수를 미리 알고 있으면 필요한 세그먼트 수를 빠르게 계산할 수 있기 때문이다.

```java
public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel)
```

하지만 Java 8 이후에 이러한 생성자는 하위 호환성을 위해서만 존재한다. 파라미터들은 단지 Map 의 초기 크기에만
영향을 준다.

ConcurrentMap (ConcurrentHashMap 클래스의 상위 인터페이스) 은 멀티 쓰레드 환경에서 키/값에 대한
일관성을 보장한다. ConcurrentMap의 키 또는 값을 배치하는 쓰레드 작업은
다른 쓰레드에서 해당 객체에 접근하거나 제거하는 작업이 발생하기 바로 직전에 수행한다.


#### Null 여부

HashMap 과 다르게 ConcurrentHashMap 은 Null 키 / 값을 허용하지 않는다.
<br>다음 코드를 실행하면 NullPointerException 발생
```java
ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<>();
map.put(null, new Object());
```

#### 주의 사항

ConcurrentHashMap 은 검색 작업을 차단하지 않는다. <br>
가장 최근에 완료된 업데이트 작업의 결과만 반영하기 때문.

- size, isEmpty, containsValue 같은 집계 메서드들은 Map 이 다른 쓰레드에서 동시 업데이트가 진행되지 않을 때만 쓸모 있다.
- ConcurrentMap 의 크기를 알고 싶을 때는 size() 메서드 대신 mappingCount() 메서드를 사용해야한다.
> 멀티 쓰레딩 환경에서 ConcurrentMap 의 매핑 갯수는 Integer.MAX_VALUE 보다 높을 수 있다.
> 따라서 long 변수로 리턴하는 mappingCount() 메서드를 사용하는 것이 맞음.
> mappingCount의 리턴값은 정확한 값이 아니라 추정치이다. 동시 삽입이나 제거가 있는 경우 실제 갯수가
> 다르게 리턴 될 수 있음.

- iterators 는 fail-fast 를 발생시키는 것 보다 weak consistency 를 제공하기 때문에 ConcurrentModificationException 을 발생시키지 않는다.
- 디폴트 테이블 용량은 16이고 concurrency level 에 따라 조정 된다.


### ConcurrentSkipListMap

ConcurrentNavigableMap 인터페이스를 구현한 클래스이다. 순서가 있는 Thread-safe 한 버전이고,
기본적으로 오름차순으로 정렬된다. 기존 이진 트리에서 발전된 red-black 트리 형태로 구현되어있기 때문에
최악의 상황에서도 O(logN)의 시간복잡도를 보여준다.


### EnumMap

Enum type 의 키을 사용할 수 있는 Map 이다. element 순서는 Enum 상수가 선언된 순서를 따른다.
<br> null 키는 허용되지 않고, weak consistency 이기 때문에 ConcurrentModification 예외가 발생하지 않는다

EnumMap 은 자동으로 동기화가 되지 않기 때문에 여러 쓰레드에서 액세스하는 상황이라면 다음과 같이 동기화를 해야한다.

```java
Map<EnumKey, V> m = Collections.synchronizedMap(new EnumMap<EnumKey, V>(...));
```

### IdentityHashMap

키를 비교할 때 equals() 로 비교하는 게 아니라 == 를 통해 비교한다. 마찬가지로 자체 클래스에서
동기화를 지원하지 않는다.

> JavaDoc에서 이 클래스는 Map의 일반적인 구현이 아니라고 한다. Map 인터페이스를 구현한 클래스이지만
>  객체를 비교할 때 equals 메서드를 사용해야하는 Map의 특징을 위반하기 때문이다.
> 해당 클래스는 참조 동등이 필요한 드문 경우에 사용하도록 설계됨


### TreeMap

Red-Black 트리 기반의 NavigableMap 을 구현한 클래스
순서가 있으며 Comparator Interface 로 구현된 여러 메서드를 사용해 정렬이 가능하다.

시간 복잡도는 O(logN) 이다.

여러 쓰레드에서 동시 접근을 할 때 데이터가 서로 다른 경우 ConcurrentModificationException 을 던진다.
**(fail-fast)**


### WeakHashMap

weak key 를 가지고 있는 HashMap 이다.

weak key일 때 키가 null 값이 되면 GC 에서 자동으로 해당 항목을 Map 에서 자동으로 삭제한다.
Weak Reference 를 한다고 해서 WeakHashMap 이다.

일반 HashMap 의 경우 키가 null 이 된다고 GC가 마음대로 삭제하지 못한다.

요약해서 힘을 따졌을 때 **HashMap > GC > WeakHashMap** 이라고 볼 수 있다. 









