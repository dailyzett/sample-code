# 목차

- [목차](#목차)
- [1. 클래스 계층 정의](#1-클래스-계층-정의)
  - [1.1 가시성 변경자: 기본적으로 공개](#11-가시성-변경자-기본적으로-공개)
  - [1.2 내부 클래스와 중첩된 클래스: 기본적으로 중첩 클래스](#12-내부-클래스와-중첩된-클래스-기본적으로-중첩-클래스)
  - [1.3 봉인된 클래스: 클래스 계층 정의 시 확장 제한](#13-봉인된-클래스-클래스-계층-정의-시-확장-제한)
- [2. 생성자와 프로퍼티를 갖는 클래스 선언](#2-생성자와-프로퍼티를-갖는-클래스-선언)
  - [2.1 클래스 초기화: 주 생성자와 초기화 블록](#21-클래스-초기화-주-생성자와-초기화-블록)
  - [2.2 부 생성자: 상위 클래스를 다른 방식으로 초기화](#22-부-생성자-상위-클래스를-다른-방식으로-초기화)
  - [2.3 인터페이스에 선언된 프로퍼티 구현](#23-인터페이스에-선언된-프로퍼티-구현)
    - [2.3.1 게터와 세터에서 Backing Field에 접근](#231-게터와-세터에서-backing-field에-접근)
    - [2.3.2 접근자의 가시성 변경](#232-접근자의-가시성-변경)
  - [2.4 컴파일러가 생성한 메서드: 데이터 클래스와 클래스 위임](#24-컴파일러가-생성한-메서드-데이터-클래스와-클래스-위임)
    - [2.4.1 모든 클래스가 정의해야 하는 메서드](#241-모든-클래스가-정의해야-하는-메서드)
    - [2.4.2 데이터 클래스: 모든 클래스가 정의해야하는 메서드 자동 완성](#242-데이터-클래스-모든-클래스가-정의해야하는-메서드-자동-완성)
    - [2.4.3 클래스 위임: by 키워드](#243-클래스-위임-by-키워드)
- [3. object 키워드: 클래스 선언과 인스턴스 생성](#3-object-키워드-클래스-선언과-인스턴스-생성)
  - [3.1 객체 선언: 싱글톤](#31-객체-선언-싱글톤)
  - [3.2 동반 객체: 팩토리 메서드와 정적 멤버가 들어갈 장소](#32-동반-객체-팩토리-메서드와-정적-멤버가-들어갈-장소)
  - [3.3 동반 객체를 일반 객체처럼 사용](#33-동반-객체를-일반-객체처럼-사용)
  - [3.4 객체 식: 익명 내부 클래스를 다른 방식으로 작성](#34-객체-식-익명-내부-클래스를-다른-방식으로-작성)

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

### 2.3.2 접근자의 가시성 변경

```kotlin
fun main() {
   val lengthCounter = LengthCounter()
   lengthCounter.addWord("Hello")
   println(lengthCounter.counter)
}

class LengthCounter {
    var counter : Int = 0
  	  private set
    fun addWord(word: String) {
        counter += word.length
    }
}
```

전체 길이를 저장하는 프로퍼티는 *public* 으로 외부에 공개된다. 하지만 세터는 현재 *private*이므로 외부 코드에서는 단어 길이의 합을 마음대로 바꿀 수 없다.

## 2.4 컴파일러가 생성한 메서드: 데이터 클래스와 클래스 위임

자바 클래스는 클래스가 equals, hashCode, toString 등의 메서드를 구현해야 한다.(롬복을 쓰면 애노테이션 한 줄로 끝나긴 한다.) 하지만 코틀린은 이런 메서드들을 생성하는 작업을 보이지 않는 곳에서 해준다.

*name* 과 *postalCode* 를 프로퍼티로 가진 *Client* 클래스가 있다고 가정한다.

```kotlin
class Client(val name:String, val postalCode: Int)
```

### 2.4.1 모든 클래스가 정의해야 하는 메서드

> *toString()*, *equals()*, *hashCode*

```kotlin
class Client(val name: String, val postalCode: Int) {
    override fun toString(): String = "Client(name=$name, postalCode=$postalCode)"
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client) return false
        return name == other.name &&
                postalCode == other.postalCode
    }

    override fun hashCode(): Int =
            name.hashCode() * 31 + postalCode

}
```

Client 클래스 하나에도 오버라이드해야할 메서드가 너무 많다. 보통 자바에서는 롬복을 사용해서 이런 중복 코드들을 제거한다. 그리고 코틀린에서도 비슷한 방법을 제공한다.

### 2.4.2 데이터 클래스: 모든 클래스가 정의해야하는 메서드 자동 완성

위 코드는 단 한 줄로 리팩토링 가능하다.

```kotlin
data class Client(val name: String, val postalCode: Int)
```

- 인스턴스 간 비교를 위한 equals
- HashMap 과 같은 해시 기반 컨테이너에서 사용할 수 있는 hashCode
- toString

롬복과 똑같이 클래스의 모든 필드들을 고려해서 만들어준다.

세 가지 외에도 여러가지 메서드를 지원해준다 (이후 7장 TIL 에서 정리)

> *copy()*

오브젝트의 불변성은 되도록 지키는 것이 좋다. 멀티 쓰레드 환경에서 변수가 불변성을 지키지 못하면 어떤 예상치 못한 상황이 발생할 지 모르기 때문이다.

하지만 객체를 복사하면서 일부 프로퍼티를 바꾸는 작업이 종종 있는데 이럴 때 유용한게 *copy* 메서드이다. 원본 객체 대신 복사본 객체의 프로퍼티를 변경한다.

```kotlin
@Test
fun copyTest() {
    val lee = Client("Lee", 322)
    println(lee.copy(postalCode = 4000))
}
```

*data class* 는 자동으로 copy 도 만들어주기 때문에 바로 사용할 수 있다.

### 2.4.3 클래스 위임: by 키워드

> **by 키워드를 사용하는 이유**

객체 지향 시스템이 복잡해질수록 구현 상속(implementation inheritance)이 발생한다.

서브 클래스가 슈퍼 클래스의 메서드 중 일부를 오버라이드하면 서브 클래스는 슈퍼 클래스의 세부 구현 사항에 의존하게 된다. 슈퍼 클래스가 시간이 지남에 따라 변경되면서 서브 클래스가 기존에 가지고 있던 슈퍼 클래스의 기존의 가정이 깨질 수 있다. 코틀린은 이를 해결하기 위해 *open* 키워드가 아니면 전부 *final*로 선언해 이런 문제를 예방한다.

하지만 때때로 상속을 허용하지 않는 클래스에 기능을 추가해야될 때가 있다. 이럴 때 **데코레이터 패턴**을 사용한다.

- 기존 클래스 대신 사용할 수 있는 새로운 클래스를 만들되 기존 클래스와 같은 인터페이스를 데코레이터가 제공하게 만들고, 기존 클래스를 데코레이터 내부에 필드로 유지한다.
- 새로 정의해야하는 기능은 데코레이터 메서드에 새로 정의한다.
- 기존 기능이 그대로 필요한 부분은 데코레이터의 메서드가 기존 클래스의 메서드에게 요청을 전달한다.

**단점: 준비 코드가 상당히 많이 필요하다.**

이 단점을 해결하는 코틀린 방식의 위임을 이용해 코드를 작성해보자. 요구사항은 다음과 같다.

- 원소를 추가할 때 시도한 횟수를 기록하는 컬렉션을 만든다.

```kotlin
class CountingSet<T>(
        private val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet { // MutableCollection 구현을 innerSet 에게 위임한다.
    var objectsAdded = 0
    override fun add(element: T): Boolean { // add, addALL 메서드는 위임하지 않고 새로운 구현을 제공한다.
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}
```

```kotlin
internal class CountingSetTest {
    @Test
    fun test() {
        val cSet = CountingSet<Int>()
        cSet.addAll(listOf(1, 2, 3, 4))
        println("${cSet.objectsAdded} objects were added, ${cSet.size} remain")
    }
}
```

**결과:**

```text
4 objects were added, 4 remain
```

코드를 보면 *CountingSet* 에 *MutableCollection* 의 구현 방식에 대한 의존관계가 생기지 않는다. *MutableCollection* 에 문서화된 API를 그대로 이용하는 것이기 때문에 API 스펙 자체가 변경되지 않는 이상 코드가 계속 잘 작동할 것이라고 확신할 수 있다.

# 3. object 키워드: 클래스 선언과 인스턴스 생성

코틀린에서 *object* 키워드를 사용하는 여러 상황이 있지만 모든 경우 클래스를 정의하면서 동시에 인스턴스를 생성한다는 공통점이 있다.

- 객체 선언 : 싱글톤을 선언하는 방법
- 동반 객체 : 어떤 클래스와 관련 있는 메서드와 팩토리 메서드를 담을 때 사용
- 객체 식은 자바의 익명 내부 클래스 대신 쓰인다.

## 3.1 객체 선언: 싱글톤

코틀린은 싱글톤 객체를 언어 자체에서 지원한다. 객체 선언은 클래스 선언과 그 클래스에 속한 단일 인스턴스의 선언을 합친 선언이다.

```kotlin
object Payroll {
    val allEmployees = arrayListOf<Person>()

    fun calculateSalary() {
        for (person in allEmployees) {
            //TODO
        }
    }
}
```

객체 선언은 *object* 키워드로 시작한다. 클래스와 동일하게 객체 선언 안에도 프로퍼티, 메서드, 초기화 블록 같은 것들이 들어갈 수 있다.

객체 선언도 클래스와 인터페이스를 상속할 수 있다. 특정 인터페이스를 구현해야하지만 내부에 상태(state)가 필요없을 때 유용하다.

예를 들어 *Comparable* 인터페이스를 구현할 때, 비교만 하면 끝이지 내부에 값을 저장할 필요는 전혀 없다.

```kotlin
object CaseInsensitiveFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        return o1.path.compareTo(o2.path, ignoreCase = true)
    }
}
```

```kotlin
@Test
fun test2() {
    val result = CaseInsensitiveFileComparator.compare(
            File("/User"), File("/user")
    )
    Assertions.assertThat(result).isEqualTo(0)
}
```

테스트는 통과한다. 클래스 안에도 객체 선언을 사용할 수 있다.

```kotlin
data class Person(val name: String) {
    object nameComparator : Comparator<Person> {
        override fun compare(o1: Person?, o2: Person?): Int =
                o1?.name?.compareTo(o2!!.name)!!
    }
}
```

```kotlin
@Test
fun test4() {
    val persons = listOf(Person("Bob"), Person("Hello"), Person("Alice"))
    println(persons.sortedWith(Person.nameComparator))
}
```

데이터 클래스 안에 객체 선언을 이용해 *comparable* 오버라이드 했다. 테스트 코드는 *comparable* 결과를 받아 소팅하는 메서드를 사용했고 결괏값은 알파벳 순으로 출력된다.

## 3.2 동반 객체: 팩토리 메서드와 정적 멤버가 들어갈 장소

코틀린 언어는 static 키워드가 없다.

대신 객체 선언과 최상위 함수로 전부 대체할 수 있다.

- 객체 선언: 자바 static 필드를 대체할 수 있음.
- 최상위 패키지 함수: 자바의 정적 메서드를 대체할 수 있음.

코틀린 최상위 함수는 *private* 으로 표시된 클래스 비공개 멤버에 접근할 수 없다.

그래서 클래스 인스턴스와 관계없이 호출해야 하지만, 클래스 내부 정보에 접근해야 하는 함수가 필요할 때는 **클래스에 중첩된 객체 선언의 멤버 함수로 정의해야 한다.**

클래스 안에 정의된 객체 중 하나에 *companion*이라는 표시를 붙이면 그 클래스의 동반 객체로 만들 수 있다.

```kotlin

class A {
    companion object {
        fun bar() {
            println("Hello Bar")
        }
    }
}
```

호출할 때는 자바의 정적 메서드를 호출하는 것처럼 `A.bar()` 를 타이핑하면 된다.

> *동반 객체*는 private 생성자를 호출하기 좋은 위치다.

companion object는 자신을 둘러싼 클래스의 모든 private 멤버에 접근할 수 있다. 따라서 동반 객체는 바깥쪽 클래스의 private 생성자도 호출할 수 있다.(팩토리 메서드를 구현하기 가장 좋다.)

```kotlin
class User private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) = User(email.substringBefore('@'))
        fun newFacebookUser(accountId: Int) = User("$accountId")
    }
}

fun main() {
    val subsUser = User.newSubscribingUser("bob@gmail.com")
    println(subsUser.nickname)
    val facebookUser = User.newFacebookUser(331)
    println(facebookUser.nickname)
}
```

*User* 클래스의 생성자는 *private*이다. 그리고 내부에 companion object 로 동반 객체를 선언했다.

클래스 이름을 사용해 그 클래스에 속한 동반 객체의 메서드를 호출할 수 있다.

## 3.3 동반 객체를 일반 객체처럼 사용

동반 객체는 클래스 안에 정의된 일반 객체이다. 따라서 다음 행동이 가능하다.

- 동반 객체에 이름을 붙힌다.
- 동반 객체가 인터페이스를 상속한다.
- 동반 객체 안에 확장 함수와 프로퍼티를 정의할 수 있다.

회사의 급여 명부를 제공하는 웹 서비스를 만든다고 가정하자. 서비스에서 사용하기 위해 객체를 JSON으로 직렬화하거나 역직렬화해야 한다. 이 직렬화 로직을 동반 객체 안으로 넣을 수 있다.

```kotlin
fun main() {
   person = Person.Loader.fromJson("{name: 'Dmitry'}")
   person.name
  
   person2 = Person.fromJson("{name: 'Brent'}")
   person2.name
}

class Person(val name: String) {
    companion object Loader{
        fun fromJson(jsonText: String) : Person = ...//JSON 파싱 코드
    }
}
```

클래스 이름을 통해 동반 객체에 속한 멤버 참조가 가능하기 때문에 이름을 짓느라 고생할 필요가 없다. 여기서는 *Loader* 라고 동반 객체 이름을 지정했는데 지정하지 않으면 디폴트 값으로 *Companion* 이 된다.

> **동반 객체에서 인터페이스 구현**

시스템에 *Person* 을 포함한 여러가지 타입의 객체가 있다고 가정하자. 여러가지 타입에 대해 동일한 JSON 파싱을 위해 인터페이스를 만들 수 있다.

```kotlin
interface JSONFactory<T> {
    fun fromJSON(jsonText: String): T
}

class Person(val name: String) {
    companion object : JSONFactory<Person> {
        override fun fromJSON(jsonText: String): Person = ...
    }
}
```

추상화를 위해 JSONFactory 인터페이스를 동반 객체에서 제네릭으로 구현했다.

> **동반 객체 확장**

1. *C* 라는 클래스 안에 동반 객체가 있다.
2. 그 동반 객체 안에 func 를 정의하면 외부에서는 func()를 *C.func()* 로 호출할 수 있다.

객체의 관심사를 조금 더 분리하고 싶을 때, 핵심 비즈니스 로직 모듈이 특정 데이터 타입에 의존하기를 원치 않을 때, 확장 함수를 사용해 조금 더 추상화 된 구조를 잡을 수 있다.

```kotlin
class Person2(val firstName: String, val lastName: String) {
    companion object {}
}

fun Person2.Companion.fromJson(json: String): Person2 {
    //TODO
}

val result = Person2.fromJson("{Example : 23}")
```

*Person2* 클래스 내부에 동반 객체를 선언하고 *fromJson* 을 외부에서 정의했다. 하지만 코드를 보면 동반 객체 안에서 fromJson 함수를 정의한 것처럼 호출했다.`val result = Person2.fromJson()`

## 3.4 객체 식: 익명 내부 클래스를 다른 방식으로 작성

자바의 익명 객체를 정의할 때도 *object* 키워드를 쓴다. 이벤트 리스너를 코틀린에서 구현해보자.

```kotlin
window.addMouseListener(
    object : MouseAdpater() {
	override fun mouseClicked(e: MouseEvent) {...}
        override fun mouseEntered(e: MouseEvent) {...}
    }
)
```

*MouseAdapter* 를 확장하는 익명 객체를 선언한다.

객체 선언과 비슷하지만 한가지 차이점은 객체 이름이 없다는 점이다.

객체 식은 클래스를 정의하고 그 클래스에 속한 인스턴스를 생성하지만, 이름을 붙이진 않는다. 보통 함수를 호출하면서 인자로 익명 객체를 넘기기 때문에 클래스와 인스턴스 모두 이름이 필요 없다.

만약 객체에 이름을 붙여야한다면 변수에 익명 객체를 대입하자.

```kotlin
fun main() {
    val listener = object : MouseAdpater() {
	override fun mouseClicked(e: MouseEvent) {...}
        override fun mouseEntered(e: MouseEvent) {...}
    }
}
```

객체 식은 *final*이 아닌 변수도 객체 식 안에서 사용할 수 있다. 예를 들어 윈도우가 호출된 횟수를 리스너에서 누적하게 만들 수 있다.

```kotlin
fun countClick(window: Window) {
    var clickCount = 0

    window.addMouseListener(object : MouseAdapter() {
        override fun mouseClicked(e: MouseEvent) {
            clickCount++
        }
    })
}
```

객체 식은 익명 객체 안에서 여러 메서드를 오버라이드 해야하는 경우에 훨씬 유용하다. 메서드가 하나 뿐인 인터페이스를 구현해야 한다면
코틀린의 SAM 변환 지원을 이용하는 것이 더 낫다. SAM 변환을 사용하려면 익명 객체 대신 람다를 사용해야 한다.