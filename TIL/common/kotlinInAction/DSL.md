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

## 1.3 invoke 관례를 사용한 더 유연한 중첩 블록

관례는 특별한 이름이 붙은 함수를 간단한 다른 구문으로 호출할 수 있게 지원해주는 기능이다.

```kotlin
class Greeter(private val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name!")
    }
}
```

이렇게 `invoke()` 함수를 지정해두면 사용자는

```
val bavarianGreeter = Greeter("Servus")
bavarianGreeter("Dmitry")
```

이렇게 해당 함수를 호출할 수 있다. 다만 너무 남용하면 다른 사람이 보기에 가독성이 떨어지는 코드가 나올 수 있으므로 주의해서 사용해야 한다.

인라인 람다를 제외한 모든 람다는 `Functional Interface`를 구현하는 클래스로 컴파일된다.

각 함수형 인터페이스 안에는 그 인터페이스 이름이 가리키는 개수만큼 파라미터를 받는 invoke 메서드가 들어 있다.

```kotlin
interface Function2<in P1, in P2, out R> {
    operator fun invoke(p1: P1, p2: P2): R
}
```

이런 사실을 굳이 알아야 될까? 싶지만 이러한 사실을 앎으로써 얻을 수 있는 이점이 있다.

> 복잡한 람다를 여러 메서드로 분리하면서도 여전히 분리 전의 람다처럼 외부에서 호출할 수 있는 객체를 만들 수 있다.

중요 이슈를 걸러내야 하는 기능을 구현한다고 가정해보자.

- project 와 인자가 동일해야 한다.
- 이슈 타입은 버그여야 한다.
- 중요도가 Major 혹은 Critical 인 것만 걸러낸다.

이렇게 복잡한 *predicate* 일 때, 람다 하나에 모두 우겨넣으면 복잡해지고 라인 수가 길어져 가독성을 해친다.

```kotlin
class ComplexityLambda {
    @Test
    fun test() {
        val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
        val i2 = Issue(
            "KT-12183",
            "Kotlin",
            "Bug",
            "Major",
            "Intention: convert serveral calls on the same receiver to with/apply"

        )
        val i3 = Issue("IDEA-12333", "IDEA", "Bug", "Critical", "Hello world!")

        val predicate = ImportantIssuesPredicate("IDEA")
        for (issue in listOf(i1, i2, i3).filter(predicate)) {
            println(issue.id)
        }
    }
}
//IDEA-154446 , IDEA-12333
```

이렇게 메인 코드가 있을 때, 가장 좋은 구현 방법은 *predicate* 를 요구 기능대로 쪼개어 이름을 붙이는 것이다.

```kotlin
class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {
    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }
    //TODO(isImportant())
}
```

첫 번째 요구사항을 만족시키기 위해 `project`를 인자로 받고 해당 프로젝트가 인자와 동일하거나,
중요도가 높을 때만 True를 리턴하도록 한다.

이제 isImportant()를 확장 함수로 구현할 차례다. (Issue 는 단순 데이터 클래스이므로 따로 작성항지 않았다.)

```kotlin
private fun Issue.isImportant(): Boolean {
        return type == "Bug" && (priority == "Major" || priority == "Critical")
    }
```

타입이 버그이고 중요도가 Major or Critical 인 것만 거르는 함수를 이렇게 분리함으로써 가독성을 크게 향상시킬 수 있다.

이렇게 람다가 invoke() 메서드가 들어 있다는 사실을 알면,
람다를 여러 메서드로 나누고 각 메서드에 뜻을 명확히 알 수 있는 이름을 붙일 수 있다.

> 람다를 함수 타입 인터페이스를 구현하는 클래스로 변환하고 그 클래스의 invoke 메서드를 오버라이드하면 이런 리팩토링이 가능하다.

predicate 클래스 내부와 predicate 가 쓰이는 주변에 복잡한 로직이 있는 경우 이런 식으로 관심사를 분리할 수 있다는 것은 큰 장점이다.
