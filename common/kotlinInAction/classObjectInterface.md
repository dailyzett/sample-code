# 목차

# 1. 클래스 계층 정의

## 1.1 가시성 변경자: 기본적으로 공개

다른 말로 접근 제한자. 영어 원문으로는 visibility modifier.

자바와 비슷하게 public, private, protected 가 있고 코틀린에서만 쓸 수 있는 internal 이 있다.

자바의 경우 기본 가시성은 패키지(package-private)이지만 코틀린에는 이러한 가시성이 없다. 대신 모듈 내부에서만 볼 수 있는 internal 이 있다. 또한 차이점이라고 하면, 코틀린의 기본 가시성은 public 이다.

```kotlin
internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey")
    protected fun whisper() = println("Let's talk!")
}

fun TalkativeButton.giveSpeech() {
    yell()
    whisper()
}
```

이 코드는 컴파일 오류가 발생한다.

- public 멤버가 internal 의 `TalkativeButton` 에 접근하려 했다.
- yell() 은 private 멤버다.
- whisper() 은 protected 멤버다.

자바는 같은 패키지라면 protected 에 접근이 가능하지만 코틀린에서는 불가능하다. 코틀린 protected 멤버는 오직 해당 클래스나 그 클래스를 확장한 클래스 안에서만 보인다.

## 1.2 내부 클래스와 중첩된 클래스: 기본적으로 중첩 클래스

자바에서 클래스 안에 정의한 클래스는 자동으로 내부 클래스가 된다. 자바 내부 클래스는 바깥쪽 클래스에 대한 참조를 묵시적으로 포함한다. 이로 인해 직렬화의 정확한 기준을 잡을 수 없다.

직렬화를 하기 위해선 내부 클래스에 static 키워드를 붙여 정적 내부 클래스로 만들어야 한다.

반면 코틀린은 아무것도 붙이지 않으면 내부 클래스의 디폴트 값은 자바 static 중첩 클래스와 같다. 자바의 내부 클래스와 동일하게 하려면 *inner* 키워드를 붙여야 한다.

```kotlin
class ButtonByKotlin: View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {
        super.restoreState(state)
    }
    class ButtonState : State {
        fun hello() {
            println("hi!")
        }
    }
}
```

코틀린의 inner class 상태에서 바깥쪽 클래스의 인스턴스를 참조하고 싶다면 *this@XXX* 를 쓴다.

```kotlin
class Outer {
    inner class Inner {
        fun example() : Outer = this@Outer
    }
}
```

## 1.3 봉인된 클래스: 클래스 계층 정의 시 확장 제한

*sealed class* 는 상위 클래스를 확장한 하위 클래스 정의를 제한할 수 있다. *sealed class* 의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩 시켜야 한다.

글만 보면 어디가 유용할까 싶지만, 코드 변경에 제약을 둠으로써 실수를 할 확률이 낮아진다.

```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int =
        when (e) {
            is Num -> e.value
            is Sum -> eval(e.right) + eval(e.left)
            else ->
                throw IllegalArgumentException("Unknown expression")
        }
```

위 예제는 Num, Sum 외에 반환할 만한 의미 있는 값이 없기 때문에 else 일 때 예외를 던진다. 하지만 이런 디폴트 분기는 항상 좋은 것만은 아니다.

Expr 을 구현하는 새로운 클래스가 생겼다고 해도 *when* 식에 그것을 처리하는 코드를 까먹으면 디폴트 분기로 넘어가게 되고 심각한 버그가 나타날 수 있다. 이 때 *sealed* 키워드를 둬서 제약을 둘 수 있다.

```kotlin
sealed class Expr {
    class Num(val value: Int) : Expr()
    class Sum(val left: Expr, val right: Expr) : Expr()
}

fun eval(e: Expr): Int =
        when (e) {
            is Expr.Num -> e.value
            is Expr.Sum -> eval(e.right) + eval(e.left)
        }
```

*when* 식에서 모든 하위 클래스를 처리한다면 디폴트 분기는 필요 없다. *sealed* 로 표시된 클래스는 자동으로 open 임을 기억하자. 따라서 별도로 open 키워드를 붙일 필요는 없다.

*sealed class* 는 클래스 외부에 자신이 확장한 클래스를 둘 수 없기 때문에 나중에 *sealed* 클래스의 하위 계층에 새로운 클래스를 추가해도 *when* 식이 컴파일되지 않는다. 따라서 *when* 식을 고쳐야한다는 점을 쉽게 알 수 있다.

참고로 *Expr* 생성자는 *private* 생성자를 가지고 그 생성자는 클래스 내부에서만 호출할 수 있다.
