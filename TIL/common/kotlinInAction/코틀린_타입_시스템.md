# 목차

- [목차](#목차)
- [1. 널 가능성](#1-널-가능성)
- [1.1 let 함수](#11-let-함수)
  - [1.2 타입 파라미터의 널 가능성](#12-타입-파라미터의-널-가능성)
- [2. 코틀린읜 원시 타입](#2-코틀린읜-원시-타입)
  - [2.1 널이 될 수 있는 원시 타입: Int?, Boolean?](#21-널이-될-수-있는-원시-타입-int-boolean)
  - [2.2 Any, Any?: 최상위 타입](#22-any-any-최상위-타입)
  - [2.3 Unit 타입: 코틀린의 void](#23-unit-타입-코틀린의-void)
  - [2.4 Nothing 타입: 정상적으로 끝나지 않는다](#24-nothing-타입-정상적으로-끝나지-않는다)
- [3. 컬렉션과 배열](#3-컬렉션과-배열)
  - [3.1 널 가능성과 컬렉션](#31-널-가능성과-컬렉션)
  - [3.2 읽기 전용과 변경 가능한 컬렉션](#32-읽기-전용과-변경-가능한-컬렉션)
  - [3.3 코틀린 컬렉션과 자바](#33-코틀린-컬렉션과-자바)
  - [3.4 자바 컬렉션을 코틀린 컬렉션으로](#34-자바-컬렉션을-코틀린-컬렉션으로)
  - [3.5 객체의 배열과 원시 타입의 배열](#35-객체의-배열과-원시-타입의-배열)

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

# 3. 컬렉션과 배열

## 3.1 널 가능성과 컬렉션

```kotlin
List<Int?>

List<Int>?
```

위 두 개는 서로 구분해서 써야한다.

첫번째는 컬렉션 내부의 원소 하나가 널이 될 수 있느냐고
두번째는 컬렉션 전체가 널이 될 수 있느냐를 뜻한다.

만약 어떤 컬렉션을 처리할 때 원소에 대한 널 검사를 수행하고
그 컬렉션에 속한 모든 원소에 대해 다시 널 검사를 수행해야 된다면
`List<Int?>?` 처럼 선언해야 한다.

```kotlin
fun addValidNumbers(numbers: List<Int?>) {
    var sumOfValidNumbers = 0
    var invalidNumbers = 0
    for (number in numbers) {
        if (number != null) {
            sumOfValidNumbers += number
        } else {
            invalidNumbers++
        }
    }
    println("Sum of valid numbers: $sumOfValidNumbers")
    println("Invalid numbers: $invalidNumbers")
}
```

컬렉션에서 널인 값을 거를려면 순회를 하면서 if - else문을 사용해야하지만 생각보다 이런 작업이 많기 때문에
코틀린은 `filterNotNull` 이라는 함수를 제공한다. 이 함수를 이용해 리팩토링을 할 수 있다.

```kotlin
fun addValidNumbers(numbers: List<Int?>) {
    val validNumbers = numbers.filterNotNull()
    println("Sum of valid numbers = ${validNumbers.sum()}")
    println("Invalid numbers = ${numbers.size - validNumbers.size}")
}
```

변수 validNumbers 는 이미 걸러낸 컬렉션이므로 타입은 List<Int> 가 된다.

## 3.2 읽기 전용과 변경 가능한 컬렉션

코틀린의 컬렉션은 읽기와 쓰기 가능한 컬렉션의 인터페이스를 서로 분리했다.

컬렉션의 데이터를 수정하려면 MutableCollection을, 그렇지 않다면 그냥 Collection을 사용한다.

**주의해야할 것은 읽기 전용 컬렉션 = 불변 컬렉션이라는 점은 아니라는 점이다.**

읽기 전용 인터페이스 타입인 변수를 사용할 때, 그 인터페이스는 실제로 어떤 컬렉션 인스턴스를 가리키는 수많은 참조 중 하나일 수 있다.

![](image/TIL-Image(1).png)

이런 상황에서 이 컬렉션을 동시 호출한다거나 병렬 실행한다면 컬렉션을 사용하는 도중에 다른 컬렉션이 그 컬렉션의 내용을 변경하는 상황이 생길 수 있다.

따라서 이런 읽기 전용 컬렉션은 **쓰레드에 안전하지 않다.**

멀티 쓰레드 환경에서 데이터를 다루는 경우 그 데이터를 적절히 동기화하거나 동시 접근을 허용하는 데이터 구조를 활용해야 한다.

## 3.3 코틀린 컬렉션과 자바

코틀린의 읽기 전용과 변경 가능 인터페이스의 기본 구조는 java.util 패키지의 구조를 그대로 옮겨 놓았다.

추가로 변경 가능한 각 인터페이스는 자신과 대응하는 읽기 전용 인터페이스를 확장한다.
읽기 전용 인터페이스에는 컬렉션을 변경할 수 있는 모든 요소가 빠져 있다.

자바에는 ArrayList 와 HashSet 컬렉션이 있다.
코틀린은 이들이 마치 MutableList 와 MutableSet 인터페이스를 상속한 것처럼 취급한다.

이런 방식을 통해 코틀린은 자바 호환성을 제공하는 한편 읽기 전용 인터페이스와 변경 가능 인터페이스를 분리한다.

> **주의점**  
> 자바는 읽기 전용과 변경 가능 컬렉션을 구분하지 않는다.
> 따라서 코틀린에서 읽기 전용으로 선언된 컬렉션이라도 자바 코드에서는 그 컬렉션 객체의 내용을 변경할 수 있다.

## 3.4 자바 컬렉션을 코틀린 컬렉션으로

자바 인터페이스를 코틀린으로 구현하려면 아래 부분들을 생각해봐야 한다.

- 컬렉션이 널이 될 수 있는가?
- 컬렉션의 원소가 널이 될 수 있는가?
- 오버라이드하는 메서드가 컬렉션을 변경할 수 있는가?

```java
interface DataParser<T> {
    void parseData(
        String input,
        List<T> output,
        List<String> errors
    ) {
        //TODO(
        // 인터페이스를 구현한 클래스가
        // 텍스트 폼에서 읽은 데이터를 파싱하고,
        // 리스트의 객체들을 출력 리스트 뒤에 추가하고,
        // 데이터를 파싱하는 과정에서 발생한 오류 메시지를 리스트에 별도로 보관한다.)
    }
}
```

- 컬렉션이 널이 될 수 있는가?
  - 호출하는 쪽에서 항상 오류 메시지를 받아야 하므로 List<String> 은 널이 되면 안 된다.
- 컬렉션의 원소가 널이 될 수 있는가?
  - erorrs의 원소는 널이 될 수 있다. 오류가 없으면 그게 널이다.
- 오버라이드하는 메서드가 컬렉션을 변경할 수 있는가?
  - 구현 코드에서 원소를 추가해야하므로 List<String>은 변경가능해야 한다.

```kotlin
class KotlinDataParser : DataParser<Person> {
    override fun parseData(
        input: String,
        output: MutableList<Person>,
        errors: MutableList<Person?>
    ) {
        // TODO()
    }
}
```

## 3.5 객체의 배열과 원시 타입의 배열

코틀린에서 배열을 만드는 방법은 다양하다.

- arrayOf 함수에 원소를 넘긴다.
- arrayOfNulls 함수에 정수 값을 인자로 넘긴다. 그러면 모든 원소가 널인 사용자가 정의한 크기의 배열이 생성된다.
- Array 생성자는 배열 크기와 람다를 인자로 받아서 각 배열의 원소를 초기화한다.

대부분 코틀린에서는 배열을 인자로 받는 함수를 호출하거나 vararg 파라미터를 받는 코틀린 함수를 호출하기 위해 배열을 만든다.

만약 데이터가 이미 컬렉션에 있어도 `.toTypedArray()` 같은 편리한 메서드를 코틀린은 제공해준다.

```kotlin
fun main(args: Array<String>) {
    runApplication<MykotlinApplication>(*args)
}
```

코틀린으로 스프링 프레임워크를 시작하면 볼 수 있는 코드인데,
`*args` 앞에 있는 `*`는 스프레드 연산자로 가변 배열을 넘겨줄 때 필수인 연산자다.

배열 타입의 타입 인자도 항상 객체 타입이 된다. 만약 원시 타입의 배열이 필요하다면 특정 배열 클래스를 사용해야 한다.

IntArray, ByteArray, CharArray, BooleanArray 등이 그 예이다.

이 모든 타입은 자바의 int[], byte[], char[] 과 같이 컴파일 된다.

일반 배열을 만들 때와 다른 점은 원시 타입은 초기화될 때 널이 아닌 0이 디폴트 값으로 설정된다는 점이다.