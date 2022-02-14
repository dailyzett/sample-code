## Set

> 순서에 상관 없이, 데이터가 존재하는지를 확인하기 위한 용도로 사용, 중복을 허가하지 않는다.

Set Interface를 구현한 주요 클래스는 HashSet, TreeSet, LinkedHashSet이 있다.

---

### HashSet
> 순서가 전혀 필요 없는 테이블을 해시 테이블에 저장

### TreeSet
> 저장된 데이터의 값에 따라 정렬되는 Set. red-black이라는 트리 타입으로 값이 저장된다.
> [Red Black 트리란?](https://github.com/dailyzett/TIL/blob/main/JavaBasic/ReadBlackTree.md)

### LinkedSet
> 연결된 목록 타입으로 구현된 해시 테이블에 데이터를 저장. 저장된 순서에 따라 값이 정렬

성능 비교 : HashSet > TreeSet > LinkedSet


---

### ArrayList vs HashSet

> ArrayList 는 순서가 있지만 HashSet 은 없다. 따라서 데이터의 위치와 관련된 메소드는 존재하지 않는다.


#### 생성자 종류

```java
HashSet()
HashSet(Collection<? extends E> c)
HashSet(int initialCapacity)
HashSet(int initialCapacity, float loadFactor)
```

#### LoadFactor란?

HashMap의 초기 용량은 해시 테이블의 버킷 수이고 2<sup>4</sup> = 16 이다.
용량은 임계값에 도달할 때마다 두 배가 된다.

Load Factor은 O(1) 의 시간 복잡도를 유지하기 위해 HashMap 의 용량을 언제 늘릴지 결정하는 척도이다.
HashMap의 기본 Load Factor 계수는 0.75 이다.

#### Load Factor 크기 계산 방법

> 해시맵의 초기 용량 * 해시맵의 로드 팩터

초기 용량인 16 과 로드 팩터 기본 계수의 0.75를 곱하면 12가 된다.<br>
이것은 HashMap 의 12번째 key-value 쌍의 총 크기가 16이라는 뜻이고, 13번째 요소가 해시맵에 들어오면 크기가 16 버킷에서 2배 증가해 32 버킷으로 자동 증가하게된다.

---







