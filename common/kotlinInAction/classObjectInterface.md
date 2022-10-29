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

# 2. 생성자와 프로퍼티를 갖는 클래스 선언

## 2.1 클래스 초기화: 주 생성자와 초기화 블록

코틀린에는 주 생성자와 부 생성자가 있다.

주 생성자를 생성하는 방법은 간단하다

```kotlin
class User(val nickname: String)
```

이렇게 클래스 이름 뒤에 괄호로 둘러싸인 코드를 **주 생성자**라고 한다.

만약 어떤 클래스가 슈퍼 클래스를 구현한 클래스라면 어떻게 해야 될까? 주 생성자에서 슈퍼 클래스의 생성자를 호출해야할 필요가 있다.

```kotlin
open class User(val nickname: String)

class TwitterUser(nickname: String) : User(nickname) {}
```

서브 클래스 *TwitterUser* 를 보면 슈퍼 클래스인 *User* 뒤에 생성자 인자인 *nickname* 을 넘기고 있음을 볼 수 있다.

그런데 *User* 클래스의 생성자가 하나도 없는 상태라면?

```kotlin
open class User()
class TwitterUser : User()
```

이 규칙으로 인해 슈퍼 클래스 이름 뒤에는 꼭 빈 괄호가 들어간다. 인터페이스는 생성자가 없기 때문에 아무 괄호가 없다. 즉 괄호의 유무로 이것이 확장한 것인지 구현한 것인지 쉽게 파악할 수 있다.

어떤 클래스를 외부에서 인스턴스화하지 못하게 막으려면 주 생성자에 private 키워드를 붙이면 된다.

```kotlin
class PrivateClass private constructor() {}
```

주 생성자는 생성하기도 쉽고 알아보기도 간편해서 이것만 사용하면 될 것 같지만 실제로는 어려움이 따르기 마련이다. 그래서 코틀린은 필요에 따라 생성자를 정의하는 여러 방법을 제공한다.

## 2.2 부 생성자: 상위 클래스를 다른 방식으로 초기화

일반적으로 코틀린에서 생성자가 여럿 있는 경우는 자바보다 훨씬 적다.

> **Tip.**
>
> 인자(Argument)에 대한 디폴트 값을 제공하기 위해 부 생성자를 여럿 만들지 말고, 파라미터의 디폴트 값을 생성자 시그니처에 직접 명시하자.

```kotlin
open class View { 
    constructor(viewName: String) {
        //TODO
    }
  
    constructor(viewName: String, isSubs: Boolean) {
        //TODO
    }
}
```

부 생성자는 *constuctor* 키워드로 여러개 생성할 수 있다. 이 클래스를 확장하면서 똑같은 부 생성자를 만들 수 있다.

```kotlin
class SubView : View {
    constructor(viewName: String) : super(viewName) {
        //TODO
    }

    constructor(viewName: String, isSubs: Boolean) : super(viewName, isSubs) {
        //TODO
    }
}
```

보다시피 슈퍼 클래스와 서브 클래스 간의 생성자들이 각각 대응된다. super 키워드를 통해 상위 클래스 생성자를 호출하는 것이다. 자바와 마찬가지로 *this* 로 자기 자신의 다른 생성자를 호출할 수도 있다.

```kotlin
class SubView : View {
    constructor(viewName: String) : this(viewName, isSubs = false) {
        //TODO
    }
```

*SubView* 클래스의 생성자 중 하나가 파라미터의 디폴트 값을 넘겨서 같은 클래스의 다른 생성자에게 생성을 위임한다.

부 생성자가 필요한 이유:

- 자바 상호 운용성
- 클래스 인스턴스를 생성할 때 파라미터 목록이 다른 생성 방법이 여럿 존재하면 부 생성자를 여러개 둘 수 밖에 없기 때문에

## 2.3 인터페이스에 선언된 프로퍼티 구현

```kotlin
interface User {
    val nickname: String
}
```

코틀린에서는 인터페이스에 추상 프로퍼티 선언을 넣을 수 있다.

```kotlin
class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
     get() = email.substringBefore('@')
}
```

- *PrivateUser* : 주 생성자에 있는 프로퍼티
- *SubscribingUser* : 커스텀 게터 구현

인터페이스에는 추상 프로퍼티뿐만 아니라 게터와 세터가 있는 프로퍼티를 선언할 수도 있다.

```kotlin
fun main() {
 	println(PrivateUser("test@kotlin.org").email)
    println(PrivateUser("test@kotlin.org").nickname)
}

interface User {
    val email: String
    val nickname: String
    	get() = email.substringBefore('@')
}

class PrivateUser(val example: String) : User {
    override val email : String
    	get() = example
}
```

하위 클래스는 추상 프로퍼티인 *email* 을 반드시 오버라이드해야 한다. 반면 *nickname* 은 오버라이드하지 않고 상속할 수 있다. 인터페이스에 선언된 프로퍼티와 달리 클래스에 구현된 프로퍼티는 뒷받침하는 필드를 원하는 대로 사용할 수 있다.

### 2.3.1 게터와 세터에서 Backing Field에 접근

코틀린의 *Backing Field*는 게터 세터에 기본적으로 생성되는 프로퍼티이다. 클래스 내의 프로퍼티에 값을 할당할 때 내부적으로 세터가 불러올 때는 게터가 호출된다.

예제 코드로 프로퍼티에 저장된 값의 변경 이력을 로그에 남기기 위한 코드를 작성한다.

- 변경 가능한 프로퍼티를 제공해야 한다.
- 세터에서 프로퍼티 값을 바꿀 때마다 약간의 코드를 추가로 실행해야 한다.

```kotlin
fun main() {
   val user = User("Alice")
   user.address = "Elesenhi 47, 60684 Muenchen"
}

class User(val name: String) {
    var address: String = "unspecified"
    	set(value: String) {
           	println("$field -> $value")
            field = value
        }
}
```

*field* 키워드는 프로퍼티의 실제 값을 저장하는 변수를 지칭한다. 이 프로퍼티의 값을 저장하는 *field*를 *backing field*라고 한다.
