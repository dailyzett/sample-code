## Static Nested Class vs Non-Static Nested Class 의 차이점

클래스는 Java 에서 static 이거나 아닐 수 있다. 따라서 둘 간에는 많은 차이가 있다.

Java 에는 최상위 클래스와 중첩 클래스(nested class) 라고 불리는 두 종류의 클래스가 존재한다.
<br> 최상위 클래스는 '.java' 파일에 선언된 클래스이다.

반면에 nested class 는 다른 클래스의 내부에 선언

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


### 차이점

- static nested class 는 외부 클래스의 static 멤버에 직접 액세스가 가능하다. 하지만 외부 클래스의 인스턴스 멤버에 액세스하려면 외부 클래스를 인스턴스화 해야함.
- static nested class 는 외부 클래스의 참조가 필요하지 않지만 일반 중첩 클래스는 내부 또는 외부 클래스의 참조가 필요
- nested class 는 클래스 멤버들에 대해 모든 액세스 권한을 가지지만, static nested class 는 중첩된 인스턴스에 대한 참조가 없으므로 static nested class 는 non-static method 를 호출하거나 non-static field 에 액세스 할 수 없다.


이런 차이점이 발생하는 이유는 static area 는 프로그램의 시작부터 종료가 될 때까지 메모리에 남아 있는다.
반면 non-static 의 경우 힙 메모리 영역에서 있다가 사용이 불필요하다면 JVM 에 의해 알아서 해제가 된다.

>즉, 메모리 관점에서 보면 static nested 클래스는 프로그램 실행 시부터 존재하지만
non-static field 는 프로그램 실행 후 새로운 객체를 "생성" 해야 값으로써 존재할 수 있기 때문에
static nested 클래스는 non-static method field나 method에 접근 제한이 생기는 것이다.