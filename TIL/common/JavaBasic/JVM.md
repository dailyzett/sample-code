## JVM

---

### JVM의 작동 방식

>JVM은 응용 프로그램을 실행하는 엔진 역할을 하고 JVM이 실행될 때 내부의 Main Method를 실행한다.

자바 애플리케이션을 Write Once Run Anywhere 라고 하는데, 개발자가 자바 코드를 한 번 개발하면
별 다른 작업 없이도 다른 환경에 적용할 수 있다는 것을 의미한다.

이것이 가능한 이유는 Java 언어에는 JVM이 있기 때문이다.

.java 파일을 컴파일하면 동일한 클래스 이름을 가진 .class 파일이 생성된다.
그리고 이 .class 파일은 JVM에 의해 다양한 단계를 거치게 된다.

---

![JVM order](https://media.geeksforgeeks.org/wp-content/uploads/jvm-3.jpg)

Class Loader 하위 시스템은 다음을 담당한다.

- Loading
- Linking
- Initialization

#### Loading

> Class Loader는 .class 파일에서 이진 파일을 생성 후 메소드 영역(Method Area)에 저장한다.<br>
메소드 영역에 저장되는 정보는 아래와 같다.

- 로드된 클래스와 해당 부모 클래스의 FQCN **(클래스가 속한 패키지명을 모두 포함한 이름을 뜻함)**
- Class, Interface, Enum과 관련있는 지 여부
- Java 접근 제어자, 변수 및 메소드 정보

.class 파일을 로딩 후 JVM은 Heap 메모리에 Class 유형의 객체를 생성한다. 이 객체는 java.lang 패키지에
정의 되어 있는 클래스고 해당 객체의 참조를 얻으려면 **Object** 클래스의 getClass() 메소드를 사용하면 된다.


#### Linking

> Verification, Preparation, Resolution 세 가지 상태를 준비한다.

- Verification : 이 파일이 올바른 형식으로 지정되고 유효한 컴파일러에 의해 생성되었는지 여부를 확인
- Preparation : JVM이 클래스 변수에 대한 메모리를 할당하고 메모리를 기본값으로 초기화
- Resolution : Symbolic reference 를 실제 물리주소를 참조할 수 있도록 Direct reference 로 대체한다, 메소드 영역을 검색하여 수행

#### Initialization

> 이 단계에서 모든 static 변수 및 static block 에 값이 할당된다.

---

#### Class Loader

일반적으로 세 가지 클래스 로더가 있다.
- BootStrap ClassLoader
  - Extension ClassLoader
    - Application ClassLoader

모든 JVM은 BootStrap ClassLoader가 구현되어 있어야한다. C, C++ 와 같은 네이티브 언어로 구성<br>
Extendsion ClassLoader 은 BootStrap ClassLoader의 자식이고
Application ClassLoader 은 Extension ClassLoader의 자식이다.

JVM 은 Delegation-Hierachy(위임 계층) 원칙에 따라 클래스를 로드한다. 만약 세가지 ClassLoader 에서 클래스를 찾지못한다면
java.lang.ClassNotFoundException을 발생시킨다.

아래는 세가지 ClassLoader의 작동 방식에 대한 그림이다.
![ClassLoader](https://media.geeksforgeeks.org/wp-content/uploads/jvmclassloader.jpg)


---

### JVM 메모리
- **메소드 영역** 
  - static 변수를 포함하여 클래스 이름, 부모 클래스 이름, 메서드 및 변수 정보 등과 같은 모든 클래스 수준 정보가 저장. JVM 당 하나의 메소드 영역만 있으며 공유 자원이다.
- **힙 영역** 
  - 모든 객체의 정보가 힙 영역에 저장된다. JVM 당 하나의 힙 영역이고 공유 자원
- **스택 영역** 
  - 모든 스레드에 대해 하나의 런타임 스택을 생성. 이 스택의 모든 블록은 메소드 호출을 저장하는 스택 프레임이라고 한다. 해당 메소드의 지역 변수는 해당 프레임에 저장되고, 스레드가 종료된 후 런타임 스택은 JVM에 의해 파괴, 공유 자원이 아니다.
- **PC 레지스터** 
  - 스레드의 현재 실행 명령의 주소를 저장. 각 스레드에는 별도의 PC 레지스터가 존재
- **네이티브 메소드 스택** 
  - 모든 스레드에 대해 별도의 네이티브 스택이 생성. 기본 메소드 정보를 저장

![JVM Memory](https://media.geeksforgeeks.org/wp-content/uploads/jvm-memory-2.jpg)

---

### 실행 엔진

실행 엔진은 .class 를 실행한다. 세 가지 부분으로 분류할 수 있다.

- Interpreter
  - 바이트코드를 한 줄씩 실행. 하나의 메소드를 여러 번 호출할 때마다 매번 해석이 필요
- JIT
  - 인터프리터의 효율을 높이고자 사용. 전체 바이트코드를 컴파일 후 네이티브 코드로 변경해 인터프리터가 반복되는 메소드 호출을 볼 때마다 재해석을 하지 않아도 되도록 만듦
- Garbage Collector
  - 참조되지 않는 객체를 파괴











