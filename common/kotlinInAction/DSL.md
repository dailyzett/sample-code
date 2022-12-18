## 1.1 API --> DSL

개발자는 API를 유지보수하기 쉽도록 코드를 작성해야하는 의무가 있다.

단순히 라이브러리 개발자들만 이러한 책임을 지는 것이 아니다.

API가 깔끔하다는 것에는 두 가지 의미가 있다.

1. 코드를 읽는 독자들이 어떤 일이 벌어질지 명확하게 이해할 수 있어야한다.
우리가 코드를 개발할 때 변수 네이밍에 신경써야하는 이유이다.

2. 코드가 간결해야 한다. 불필요한 구문이나 번잡한 준비 코드가 가능한 한 적어야한다

## 1.2 수신 객체 지정 람다와 확장 함수 타입

- 수신 객체 지정 람다: lambda with a receiver

```kotlin
fun buildString (builderAction: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}
```

이런 코드가 있을 때 실사용을 하려면,

```kotlin
val s = buildString {
    it.append("Hello, ")
    it.append("World!")
}
```

이렇게 문자열을 append 할 때마다 `it`을 추가해줘야 한다. 이렇게 하지 않으려면 람다를 lambda with a receiver 로 바꿔야 한다.

아래와 같이 코드를 리팩토링 한다.

```kotlin
fun buildString (builderAction: StringBuilder.() -> Unit) : String {
    val sb = StringBuilder()
    sb.builderAction()
    return sb.toString()
}
```

이렇게 하면 `it`을 빼버릴 수 있다. lambda with a receiver 를 인자로 넘기기 때문에 람다 안에서 `it`을 사용하지 않아도 된다.

물론 완전한 문장은 `this`가 되겠지만 모호한 상황이 아니라면 굳이 붙일 필요는 없다.

`buildString` 의 함수를 보면 인자 타입으로 확장 함수를 받는다. 여기서 `.` 앞에 오는 타입(StringBuilder)를 *receiving object type* 이라고 부른다.
그리고 람다에 전달되는 그런 타입의 객체를 *receiving object*라고 부른다.

```kotlin
String.(Int, Int) -> Unit
//receiving object type.parameter type -> return type
```

확장 함수 본문에서는 확장 대상 클래스에 정의된 메서드를 그 클래스 내부에서 호출하듯이 사용할 수 있다.

따라서 람다 본문 안에서 특별한 수식자 없이 사용할 수 있게 되는 것이다.

표준 라이브러리의 buildString 구현은 더 짧다.

```kotlin
@kotlin.internal.InlineOnly
public inline fun buildString(builderAction: StringBuilder.() -> Unit): String {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return StringBuilder().apply(builderAction).toString()
}
```

`builderAction`을 명시적으로 호출하는게 아니라 `apply`로 묵시적으로 호출한다.

기본적으로 `apply`, `with`는 자신이 제공받은 *receiving object*로 확장 함수 타입의 람다를 호출한다.

`apply`는 receiving object 를 다시 반환하지만, `with`는 람다를 호출해 얻은 결과를 반환한다는 점에서 다르다.

