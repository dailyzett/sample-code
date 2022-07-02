## Serializable

---

> 사용자가 만든 클래스를 파일에 읽고 쓰거나, 다른 서버로 보내거나 받을 수 있도록 하려면 이 인터페이스를 반드시 구현해야한다.

Serializable 인터페이스를 구현한 후에는 serialVersionUID 라는 값을 지정해주는 것을 권장한다.

```java
static final long serialVersionUID = 1L;
```

각 서버에서, serialVersionUID의 값이 다르다면 다른 클래스로 인식한다.
같은 UID 라고 해도 변수의 개수나 타입 등이 다르면 이 경우도 다른 클래스로 인식한다.


---

### transient

> 객체를 저장하거나 다른 JVM 으로 보낼 때 transient 예약어를 사용하여 선언한 변수는 Serializable
> 의 대상에서 제외된다.

보안상 중요한 변수나 꼭 저장해야 할 필요가 없는 변수 인 경우 해당 예약어를 사용


