## Static Nested Class vs Non-Static Nested Class 의 차이점

### 1. 개요
클래스는 Java 에서 static 이거나 아닐 수 있다. 따라서 둘 간에는 많은 차이가 있다.
Java 에는 최상위 클래스와 중첩 클래스(nested class) 라고 불리는 두 종류의 클래스가 존재한다.
최상위 클래스는 '.java' 파일에 선언된 클래스이다.

반면에 nested class 는 다른 클래스의 내부에 선언한다.
static nested class 인 경우 다음과 같이 선언, 사용할 수 있다.

```java
class ClassA {
    static int x = 10;
 
    int y = 20;
 
    private static int z = 30;
 
    static class ClassB {
        void get()
        {
            System.out.println("x: " + x);
            System.out.println("z: " + z);
        }
    }
}
```

```java
class GFG {
    public static void main(String[] args)
    {
        ClassA.ClassB ob1 = new ClassA.ClassB();
        ob1.get();
    }
}
```

만약 ClassB가 non-static nested class 라면 다음과 같이 사용하면 된다

```java
ClassA ob1 = new ClassA();
ClassA.ClassB ob2 = ob1.new ClassB();
ob2.get();
```

#### 1.1 Nested Classes 를 사용하는 이유

사용 이유는 다음과 같다.

- 한 곳에서 사용되는 클래스를 논리적으로 그룹화 할 수 있는 방법
  - 만약 클래스가 오직 한 개의 다른 클래스에만 유용할 때, 해당 클래스에 포함시키고 두 개의 클래스를 함께 포함시키는 것이 좋다.
- 캡슐화 증가
  - 두 개의 최상위 클래스 A, B가 있다고 고려해보자. B는 A의 private 접근 제한자로 선언된 멤버에 접근이 필요한 상태이다.
  - A 클래스 내부에 B를 포함시킴으로써, A의 멤버는 private으로 선언될 수 있고 B는 그것에 접근할 수 있다.
- 유지 보수가 쉽고 가독성이 좋은 코드를 만들 수 있다.
  - 최상위 클래스 내부의 소규모의 중첩 클래스를 사용하면 그것이 사용되는 위치에 더 가깝게 배치되기 때문이다.
  

#### 1.2 IDE가 static nested class 를 권장하는 이유

외부 참조가 유지된다는 것은 메모리에 대한 참조가 유지되고 있다는 말과 동일하다. 즉, GC(Garbage Collection)이 메모리를
회수할 수가 없는데, 이것은 메모리 누수로 이어질 수 있다. 그리고 항상 외부 인스턴스의 참조를 통해야 하므로 성능 상 비효율적이기 때문에
외부 인스턴스에 대한 참조가 필요하지 않다면 static nested class로 만드는 것이 낫다.

### 2. 차이점

- static nested class 는 외부 클래스의 static 멤버에 직접 액세스가 가능하다. 하지만 외부 클래스의 인스턴스 멤버에 액세스하려면 외부 클래스를 인스턴스화 해야함.
- static nested class 는 외부 클래스의 참조가 필요하지 않지만 일반 중첩 클래스는 내부 또는 외부 클래스의 참조가 필요
- nested class 는 클래스 멤버들에 대해 모든 액세스 권한을 가지지만, static nested class 는 중첩된 인스턴스에 대한 참조가 없으므로 static nested class 는 non-static method 를 호출하거나 non-static field 에 액세스 할 수 없다.


이런 차이점이 발생하는 이유는 static area 는 프로그램의 시작부터 종료가 될 때까지 메모리에 남아 있는다.
반면 non-static 의 경우 힙 메모리 영역에서 있다가 사용이 불필요하다면 JVM 에 의해 알아서 해제가 된다.

>즉, 메모리 관점에서 보면 static nested 클래스는 프로그램 실행 시부터 존재하지만
non-static field 는 프로그램 실행 후 새로운 객체를 "생성" 해야 값으로써 존재할 수 있기 때문에
static nested 클래스는 non-static method field나 method에 접근 제한이 생기는 것이다.