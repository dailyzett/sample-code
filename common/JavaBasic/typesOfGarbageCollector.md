## Garbage Collector(GC) 의 종류

### 1. 개요

GC 의 종류는 4가지가 있다.

- Serial GC
- Parallel GC
- Concurrent Mark Sweep(CMS) GC
- Garbage First(G1) GC

각각의 GC는 장단점이 있다. 사용자는 JVM을 통해 어떤 GC를 사용할 것인지 설정할 수 있다.
이러한 설정을 하기 위해, JVM 전달인자(argument)가 필요하다.

GC 작동방식을 자세히 알기 위해 필수 용어를 아는 것이 중요하다. 필수 용어는 다음과 같다.
- Mark : 참조 가능한 객체에 마킹하는 작업이다.
- Sweep : Mark되지 않은 객체들을 제거하는 과정이다.
- Compact : Sweep 과정에 의해 삭제되면 메모리 단편화가 발생하는데, Compact 작업을 통해
빈 공간을 자동으로 채운다.
- Promotion : GC의 Surviovr 영역에서 계속해서 살아남은 객체들이 특정 age 값에 도달하면, Old Generation
에 도달하는데 이 과정을 promotion 이라고 한다.

### 2. Serial Garbage Collection

Serial GC는 **싱글 쓰레드 환경**에서 유용하다. Serial GC는 작업을 위해 단 하나의 유일한
쓰레드만 사용한다. Serial GC는 애플리케이션의 모든 쓰레드들을 홀딩한 채로 작업한다. 이 말의 의미는,
애플리케이션의 모든 쓰레드들을 GC 작업을 실행하는 동안 정지시킨다는 뜻과 같다. 그리고 이러한 작업을
**STW(Stop The World Event)**라고 부른다.

Serial GC를 서버 환경에서 사용하는 것은 지양해야한다. 싱글 쓰레드 환경에서 유용하기 때문에,
간단한 프로그램에 사용하는 것이 옳다. Serial GC를 사용하려면 JVM 전달인자로 **-XX:+UseSerialGC**
를 전달해주면 된다.

다음은 Serial GC가 작동하는 방식을 그림으로 표현한 것이다.

![Serial GC](https://static.javatpoint.com/core/images/types-of-garbage-collector-in-java2.png)

### 3. Parallel Garbage Collection

Parallel GC는 자바 8버전 이하이면서 JVM을 사용할 때 기본적으로 설정되는 GC이다. Parallel GC도 기본적인 동작 방식은
Serial GC와 같다. 한가지 차이점은 Parallel GC는 GC 작업을 수행할 때 멀티 쓰레드 방식을 선택한다는 점이다.
Parallel GC는 여러개의 CPU를 활용해서 처리량을 높일 수 있다. 그래서 Parallel GC는 **throughput collector**
라고 알려져 있다.

Parallel GC는 장시간 작업이 필요한 프로세스(예를 들면, 배치 프로세스)나 장시간의 일시정지를 허용하는
환경에서 사용된다. Parallel GC를 사용하기 위해 전달해야하는 인수는 **-XX:+UseParallelGC**이다.

다음은 Parallel GC가 작동하는 방식을 그림으로 표현한 것이다. GC 처리 과정에서 멀티 쓰레드를 이용한다는 것을
제외하고 Serial GC와 작동방식은 차이가 없다.

![Parallel GC](https://static.javatpoint.com/core/images/types-of-garbage-collector-in-java3.png)

Parallel GC는 다음과 같은 인자를 설정할 수 있다.

| JVM Argument              | Description                        |
|:--------------------------|:-----------------------------------|
| -XX:ParallelGCThreads=<n> | GC 쓰레드의 개수를 (n)개로 설정한다.            |
| -XX:MaxGCPauseMillis=<t>  | 최대 일시정지 시간(Pause time)을 설정한다       |
| -XX:GCTimeRatio=<n>       | 최대 처리량 목표(throughput target)를 설정한다 |

- 일시정지 시간 : GC 두 개 사이의 간격을 뜻한다.
- 처리량 목표 : GC 내부에서 보낸 시간과 GC 외부에서 보낸 시간과의 차이를 뜻한다.

### 4. Concurrent Mark and Sweep(CMS) Garbage Collector

CMS GC는 GC 작업을 수행할 때 여러개의 GC 쓰레드를 이용한다. 이렇게 설계한 이유는
STW를 최소화하고, 애플리케이션이 실행되는 동안
GC 작업과 함께 프로세스 자원들을 공유하기 위해서이다. 따라서 CMS GC를 사용하는 애플리케이션은
평균 응답 시간이 다른 GC에 비해 느리지만 GC를 수행할 때 응답을 보내는 것을 멈추진 않는다.

작동방식을 그림으로 표현하면 다음과 같다.

![CMS GC](https://static.javatpoint.com/core/images/types-of-garbage-collector-in-java4.png)

파란색 화살표가 GC 작업을 의미하는데 애플리케이션의 쓰레드가 실행 중인 상태여도 GC 작업은 멈추지 않는다.
중요한 것은 Java 9에서 CMS GC는 **deprecated**가 되었고, Java 14부터는 해당 GC의 지원을
완전히 없앴다. 따라서 해당 GC는 사용하는 것을 지양해야한다.

> **Note.** <br>
> GC 작업을 수행할 때 동시다발적으로 진행되기 때문에 CMS GC가 좋아보이지만, 지원 중단의 이유는
> 설계의 복잡성에 비해 큰 성능의 이점이 없다는 점 때문이다. JVM 전달인자로 Parallel GC는 6개가 필요하고
> CMS GC는 무려 72개가 필요하다. 이러한 특성은 GC 개발팀이 성능 개선을 위해 코드를 수정하려고 할 때,
> 많은 어려움이 따른다는 것을 의미한다. 왜 deprecated 됐는지 자세한 설명은 아래의 사이트에서 참조 가능하다.<br>
> https://blog.gceasy.io/2019/02/18/cms-deprecated-next-steps/

### 5. G1 Garbage Collector

G1 GC는 큰 메모리(힙 영역이 4GB 이상)를 할당할 수 있을 때 사용하는 GC이다.
G1 GC는 힙을 일정 사이즈(보통 1MB에서 32MB 사이)로 분할한다. 그리고 나눈 힙 영역들에게
우선 순위를 부여해서 우선 순위에 따라 Parallel GC 작업을 수행한다.

GC는 Eden, surviors, old area 영역으로 나눌 수 있는데 이 세가지 영역들을 모두
동일한 사이즈로 분할한다. 세가지 영역 외에도 G1 GC는 두 가지 타입의 영역이 더 존재한다.

- Humongous : 메모리가 큰 객체를 할당할 때 사용하는 영역이다.
- Available : 비어있는 영역을 표시한다.

아래 그림을 보면 어떻게 힙 영역을 분할하는지 쉽게 알 수 있다.

![G1GC](https://static.javatpoint.com/core/images/types-of-garbage-collector-in-java5.png)

G1 GC는 CMS GC 보다 JVM 전달인자는 적으면서 더 효율적인 성능을 보여준다.(CMS GC가 사라진 두 번째 이유이기도 하다)
G1 GC는 첫 번째로 mark 작업을 하며, 힙 전체에서 객체 활성 상태를 결정한다. mark 작업이 끝나면 G1 GC는
비어 있는 영역을 파악할 수 있다. G1 GC는 상당한 양의 여유 공간을 제공할 수 있는 이러한 영역들을 첫번째(first)로 수집한다.
이러한 동작 방식은 해당 GC가 Garbage-First라고 불리는 이유이다. G1 GC는 Java 9 부터 
기본 GC 방식으로 채택되었다.

### 6. Java 8 에서 GC 변경점

동일한 String 인스턴스가 생성될 때 불필요한 메모리가 할당되는 것을 방지하기 위해 JVM 전달인자로
하나가 더 추가되었다. **-XX:+UseStringDeduplication** 명령어로 설정할 수 있고, 중복되는 String 값들을 전체적으로
사용할 수 있는 단일 char[] 배열로 대체하여 힙 메모리를 최적화시킨다.


### 7. 출처

> https://www.javatpoint.com/types-of-garbage-collector-in-java

> https://www.baeldung.com/jvm-garbage-collectors