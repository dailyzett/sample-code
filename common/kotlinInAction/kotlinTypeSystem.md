# 목차

# 1. 널 가능성

> **as?**

어떤 값을 지정한 타입으로 변경한다.

값을 대상 타입으로 변경할 수 없으면 널을 반환한다.

# 1.1 let 함수

널이 될 수 있는 값을 널이 아닌 값만 인자로 받는 함수에 넘기려면 *let* 함수를 쓴다.

```kotlin
@Test
fun test2() {
    fun sendMailTo(email: String) {
        println("Sending email to $email")
    }
    var email: String? = "yole@example.com"
    email?.let { sendMailTo(it) }
    email = null
    email?.let { sendMailTo(it) }
}

// Sending email to yole@example.com
//
// Process finished with exit code 0
```

아주 긴 식이 있고, 그 값이 null이 아닐 때, 그리고 수행해야 하는 로직이 있을 때 *let*을 쓰면 훨씬 편하다.

```kotlin
//1.
val person: Person? = getTheBestPersonInTheWorld()
if (person != null) sendMailTo(person.email) // ---> (X)

//2.
getTheBestPersonInTheWorld()?.let { sendEmailTo(it.email) } ---> (O)
```

X 표시처럼 굳이 person 변수를 추가해줄 필요가 없다.

여러 값이 널인지 검사해야 한다면 *let*을 중첩해서 쓸 수 있지만 코드가 복잡해지므로 이럴 땐 일반적인 if 를 사용하자.

let 함수는 널이 될 수 있는 타입의 값에 대해 호출할 수 있지만 let은 this가 널인지 검사하지 않는다. 널이 될 수 있는 타입의 값에 대해 안전한 호출을 사용하지 않고 let을 호출하면 람다의 인자는 널이 될 수 있는 타입으로 추론된다.

```kotlin
val person: Person? = ...
person.let { sendEmailTo(it) } // 안전한 호출을 하지 않으므로 "it"은 널이 될 수 있는 타입으로 취급됨
```

따라서 let을 사용할 때 반드시 안전한 호출 연산인 `?.`을 사용하자.

## 1.2 타입 파라미터의 널 가능성

```kotlin
@Test
fun test5() {
    fun <T> printHashCode(t: T) {
        println(t?.hashCode())
    }
    printHashCode(null)
}
```

모든 타입 파라미터는 기본적으로 널이 될 수 있다. 타입 파라미터가 널이 될 수 없음을 확실히 하려면 *타입 상한*을 지정해야 한다.

```kotlin
@Test
fun test5() {
    fun <T: Any> printHashCode(t: T) { // 이제 T는 널이 될 수 없다.
        println(t?.hashCode())
    }
    printHashCode(42)
}
```

타입 파라미터는 널이 될 수 있는 타입을 표시하려면 반드시 물음표를 타입 이름 뒤에 붙여야 한다는 규칙의 유일한 예외다.

# 2. 코틀린읜 원시 타입

코틀린은 원시 타입과 래퍼 타입을 구분하지 않는다.

## 2.1 널이 될 수 있는 원시 타입: Int?, Boolean? 

어떤 클래스의 타입 인자로 원시 타입을 넘기면 코틀린은 그 타입에 대한 박스 타입을 사용한다.

이렇게 되는 이유는 JVM에서 제네릭을 구현하는 방법 때문이다. JVM은 타입 인자로 원시 타입을 허용하지 않는다.

만약 원시 타입으로 이뤄진 대규모 컬렉션을 효율적으로 저장해야 한다면?

- 서드파티 라이브러리(트로브 등..)를 사용한다.
- 배열을 사용한다.

## 2.2 Any, Any?: 최상위 타입

코틀린에서 Any 타입은 모든 널이 될 수 없는 타입의 조상 타입이다. (널이 될 수 있는 조상 타입은 Any?)

자바의 Object와 비슷하지만 차이점이 있다.

- 자바 원시 타입은 Object에 속하지 않는다. 즉 원시 타입은 매번 래퍼 객체로 감싸야 Object 타입과 매칭시킬 수 있다.
- 하지만 코틀린은 Any가 Int를 비롯한 모든 원시 타입의 조상 타입이다.

## 2.3 Unit 타입: 코틀린의 void

자바의 void와 비슷하지만, 코틀린의 Unit 타입은 모든 기능을 갖는 일반적인 타입이며 void와 달리 Unit은 타입 인자로 사용 가능하다.

제네릭 파라미터를 반환하는 함수를 오버라이드하면서 반환 타입으로 Unit으로 쓰면 유용하다.

```kotlin
interface Processor<T> {
    fun process(): T
}

class NoResultProcessor : Processor<Unit> {
    override fun process() {
        //반환 값이 필요 없다.
    }
}
```

컴파일러가 묵시적으로 return Unit을 넣어주기 때문에 명시적으로 Unit을 반환할 필요가 없다.

`java.lang.Void`를 써도 되지만, Void 타입에 유일하게 대응할 수 있는 값인 null을 반환하기 위해
`return null`을 명시해줘야 한다.

## 2.4 Nothing 타입: 정상적으로 끝나지 않는다

코틀린에는 결코 성공적으로 값을 돌려주는 일이 없으므로 "반환 값"이라는 개념 자체가 의미 없는 함수가 존재한다.

예를 들어,

- 테스트를 일부러 실패시키기 위해 fail이란 특별한 메시지가 들어있는 테스트 함수
- 무한 루프를 도는 함수

이런 함수들을 분석할 때 이 함수가 정상적으로 끝나지 않는다는 사실을 미리 알면 유용하다. 이 떄 Nothing이 쓰인다.

```kotlin
@Test
fun test3() {
    fun fail(message: String): Nothing {
        throw IllegalStateException(message)
    }

    fail("Error occurred")
}
```

Nothing 타입은 아무 것도 반환하지 않는다. 따라서 Nothing은 함수의 반환 타입이나 반환 타입으로 쓰일 타입 파라미터로만 쓸 수 있다.

Nothing을 반환하는 함수를 엘비스 연산자의 우항에 사용해서 전제 조건을 검사할 수 있다.

```kotlin
val company = company.address ?: fail("No Address")
println(address.city)
```

예제에서 컴파일러는 company.address가 널인 경우 엘비스 연산자의 우항에서 예외가 발생한다는 사실을 파악하고 address의 값이 널이 아님을 추론할 수 있다.