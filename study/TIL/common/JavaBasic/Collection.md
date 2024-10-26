## Collection

### 자바 Collection 종류

> Collection 인터페이스는 java.util 패키지에 선언 <br>
> Map 은 Collection 과 관련 없는 별도의 인터페이스에 선언

![img.png](https://imbf.github.io/assets/interview/naver-practical-interview-preparation5-3.jpg)

---
### Collection 선언

```java
public interface Collection<E> extends Iterable<E>
```

Collection 인터페이스는 Iterable<E> 인터페이스를 확장시킨 것이다.
Iterable 인터페이스에 선언되어 있는 메소드는 다음과 같다.

| 리턴 타입       | 메소드이름      |
|:------------|:-----------|
| Iterator<T> | Iterator() |

Iterator()에는 3개의 메소드가 있다.
- hasNext()
- next()
- remove()

Collection 인터페이스가 Iterable 인터페이스를 확장했다는 것은 값을 가져올 때 
Iterator 인터페이스를 사용하여 순차적으로 가져올 수 있다는 의미

[Collection 메소드 종류](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html)




