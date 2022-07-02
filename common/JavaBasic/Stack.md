## Class Stack &lt;E&gt;

### LIFO (Last In First Out) 을 구현하는 클래스

### 상속 관계

java.lang.Object<br>
&nbsp;&nbsp; java.util.AbstractCollection<E><br>
&nbsp;&nbsp;&nbsp;&nbsp; java.util.AbstractList<E><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; java.util.Vector<E><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; java.util.Stack<E><br>

> Stack 클래스는 Java에서 상속을 잘못 받은 케이스이다.
원래 취지인 LIFO 를 생각한다면 Vector에 속해있으면 안되지만, 자바의 하위 호환성을 위해 지금까지
이 상속 관계를 유지하고 있다.

---

[Stack Method 자료](https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html)
