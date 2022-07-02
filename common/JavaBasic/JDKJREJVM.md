## JDK, JRE 및 JVM의 차이점

---

### JDK
>JDK는 Java 애플리케이션을 개발하는 데 사용되는 소프트웨어 개발 환경이다.<br>
>여기에는 JRE, 인터프리터/로더, 컴파일러(javac), 아카이버(jar), 문서 생성기(Javadoc) 및 기타 Java 개발에 필요한 도구가 포함된다.

### JRE
>JRE(Java Runtime Environment)는 Java 애플리케이션을 실행하기 위한 최소 환경을 제공한다.
><br>JVM, 핵심 클래스 및 지원 파일로 구성된다.

### JVM
> 자바 바이트코드를 실행하는 실행기
> <br> Java 프로그램을 한 줄씩 실행하는 역할을 하므로 인터프리터라고도 한다.
---

### JDK, JRE, JVM 관계 그림

![relation](https://media.geeksforgeeks.org/wp-content/uploads/20210218150010/JDK.png)

---

### Java 코드 실행 순서

![Java code order](https://media.geeksforgeeks.org/wp-content/uploads/JRE_JDK_JVM.jpg)

Example.java 가 컴파일을 거치면 바이트 코드인 Example.class가 생성된다.