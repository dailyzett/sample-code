## ConcurrentHashMap

## 1. ConcurrentHashMap 을 사용하는 이유

HashTable 은 쓰레드에 안전하지만 동기화 메서드를 이용해서 동기화를 진행한다. 이 때 쓰기와 읽기 작업
모두가 동기화가 된다. 즉 데이터를 쓸 때는 읽을 수 없다. 멀티 쓰레드에서 서로 자원을 점유하려고 할 때
잠금을 해제하기 위해 동일한 경쟁을 해야하므로 효율성이 매우 떨어진다. 따라서 JDK 1.5 에서는 이러한 문제를
해결하기 위해 ConcurrentHashMap을 만들었다.

### 1.1 ConcurrentHashMap 이 효율적인 이유

ConcurrentHashMap은 분할 잠금 기술(segmented locking technology)을 사용한다. 16개의 세그먼트로
영역을 구분해서 영역별로 잠금을 하는 방식이다. 그리고 각 영역에는 리사이징되는 해시테이블을 갖는다. HashTable
과 다르게 세그먼트별로 잠금을 하기 때문에, 멀티 쓰레드 환경에서 HashTable보다 16배 효율적이라고 볼 수 있다.

다음은 ConcurrentHashMap의 구조를 나타내는 그림이다.
![img](https://programmer.ink/images/think/5f38b9d73666c1cbcd89202cf1fea322.jpg)

### 1.2 JDK 1.8 이후 구현 변경점

ConcurrentHashMap은 Java 8로 넘어오면서, 세그먼트 잠금 방식을 없애고 CAS(Compare And Swap) 방식을 채택했다.
데이터 구조는 8버전의 HashMap, array + linked list 와 레드-블랙 이진 트리(red-black binary tree) 와 동일하다.
이 방식은 현재 링크드 리스트의 첫번째 노드 혹은 레드-블랙 트리의 첫번째 노드만 잠근다. 그래서 해시 충돌이
일어나지 않는다면, 비동시성이고 효율성은 N배 향상된다.

![img2](https://programmer.ink/images/think/a7b284d7d2c1911f82abcd079a3f58be.jpg)

코드를 보면서 확인해보자

아래의 코드를 보면,
ConcurrentHashMap의 static 중첩 클래스를 살펴보면 val과 노드 타입의 next 변수가 volatile로 선언되어있다.
```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        volatile V val;
        volatile Node<K,V> next;
```

그리고 필드에 이 Node<K,V> 를 사용한 변수가 각각 선언되어있다.

```java
transient volatile Node<K,V>[] table;
private transient volatile Node<K,V>[] nextTable;
```
다음은 삽입에 해당하는 put 메서드를 보면 HashTable과 다르게 주요 method에 synchronized 키워드가 없다.
```java
final V putVal(K key, V value, boolean onlyIfAbsent) {
        if (key == null || value == null) throw new NullPointerException();
        int hash = spread(key.hashCode());
        int binCount = 0;
        for (Node<K,V>[] tab = table;;) { // (1)
            Node<K,V> f; int n, i, fh; K fk; V fv;
            if (tab == null || (n = tab.length) == 0)
                tab = initTable(); // (2)
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { // (3)
                if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                    break;                   // no lock when adding to empty bin
            }
```

- (1)번에서 for은 무한루프이다.
- (2)번에서 table 사이즈를 초기화한다. 기본값은 16이다.
- (3)번에서 tabAt 메서드로 해당하는 버킷 값을 가져와 그 값이 비어있는지 확인하고, 비어있다면
volatile 변수(여기선 tab)에 접근해서 새로운 Node 를 생성해서 넣고, 아니라면 다시 for문으로 간다

tabAt 과 casTabAt의 구현은 다음과 같다.
```java
static final <K,V> Node<K,V> tabAt(Node<K,V>[] tab, int i) {
    return (Node<K,V>)U.getReferenceAcquire(tab, ((long)i << ASHIFT) + ABASE);
}

static final <K,V> boolean casTabAt(Node<K,V>[] tab, int i,
                                    Node<K,V> c, Node<K,V> v) {
    return U.compareAndSetReference(tab, ((long)i << ASHIFT) + ABASE, c, v);
}
```

getReferenceAcquire / compareAndSetReference 메서드는 native 메서드로써 자바에 구현되어 있지 않은 메서드이다.
```java
@IntrinsicCandidate
public final native boolean compareAndSetReference(Object o, long offset,
                                                   Object expected,
                                                   Object x);
```
이 메서드는 native 함수로 자바에 구현되어 있지 않고 C11 atomic_compare_exchange_strong 메서드와
기능이 동일하다.
위 메서드는 객체가 가리키는 값과 예상되는 값이 같은지 원자적으로 비교하고, 비교 걸과가 참이라면
이 메서드는 객체가 가리키는 값을 원하는 값으로 바꾼다. 한마디로 이 메서드가 CAS(Compare And Swap) 이다.

만약 이미 노드가 존재하는 경우 synchronized 키워드를 이용해 하나의 쓰레드만 접근할 수 있도록
제어한다. 여기서 f 변수는 앞서서 Node<K,V> 타입으로 초기화해둔 for 문 내의 지역변수이다.

```java
else {
    V oldVal = null;
    synchronized (f) {
        if (tabAt(tab, i) == f) {
            if (fh >= 0) {
                binCount = 1;
                for (Node<K,V> e = f;; ++binCount) {
                    K ek;
                    // 동일키일 때 새로운 노드로 교체한다.
                    if (e.hash == hash &&
                        ((ek = e.key) == key ||
                         (ek != null && key.equals(ek)))) {
                        oldVal = e.val;
                        if (!onlyIfAbsent)
                            e.val = value;
                        break;
                    }
                    Node<K,V> pred = e;
                    //Separate Chaining 방식 적용.
                    //추가 메모리를 사용해 동일한 버킷에 값이 있으면 링크드 리스트로 해당 value를
                    //뒤에 저장하는 방식을 Sepraate Chaining 방식이라고 한다.
                    if ((e = e.next) == null) {
                        pred.next = new Node<K,V>(hash, key, value);
                        break;
                    }
                }
            }
            // TreeNode에 추가하는 방식
            // TREEIFY_THRESHOLD 값에 따라 TreeBin을 선택할지 Chaining 방식을 선택할지 정한다
            else if (f instanceof TreeBin) {
                Node<K,V> p;
                binCount = 2;
                if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                               value)) != null) {
                    oldVal = p.val;
                    if (!onlyIfAbsent)
                        p.val = value;
                }
            }
            else if (f instanceof ReservationNode)
                throw new IllegalStateException("Recursive update");
        }
    }
    // binCount 가 TREEIFY_THRESHOLD 보다 크면 treeifyBin 메서드가 실행된다.
    // TREEIFY_THRESHOLD의 기본값은 8이다. 즉 버킷 개수가 8이상이 되면
    // chaining 방식에서 TreeBin 방식으로 자동으로 변환한다.
    if (binCount != 0) {
        if (binCount >= TREEIFY_THRESHOLD)
            treeifyBin(tab, i);
        if (oldVal != null)
            return oldVal;
        break;
    }
}
```

Separate Chaining과 TreeBin은 모두 해시충돌 시 실행되는 작업이다. 해시충돌이 일어났을 때,
Chaining 방식으로 단순히 리스트 뒤에 추가하는 방식의 시간 복잡도는 O(N)이지만, 이진 트리 방식은 최악의 상황에서도
O(logN) 이기 때문에 더 효율적이다.







