# 

# 목차

# 1. 람다 식과 멤버 참조

코틀린 표준 라이브러리에는 *joinToString*라는 함수가 있다. 마지막 인자로 함수를 더 받는데 리스트의 원소를 toString이 아닌 다른 방식을 통해 문자열로 변환하고 싶은 경우 이 인자를 활용한다.

```kotlin
fun main() {
    val people = listOf(Person("이몽룡", 29), Person("성춘향", 31))
    val names = people.joinToString(separator = " ", transform = {p: Person -> p.name})
    println(names)  
}

data class Person(val name: String, val age: Int)
```

위 람다식에서 함수를 괄호 밖으로 뺀 모습은 아래와 같다.

```kotlin
val names = people.joinToString(separator = " ") {p: Person -> p.name}
```

더 구문을 간단하게 다듬고 싶으면 파라미터를 생략하면 된다. 예를 들어,

```kotlin
people.maxBy { p: Person -> p.age }
people.maxBy { p -> p.age }
```

위와 아래는 모두 같은 결과가 나온다. *maxBy* 함수는 파라미터 타입은 항상 컬렉션 원소 타입과 같다. 즉 컴파일러가 이미 *Person* 객체가 넘어온다는 사실을 알고 있다는 뜻이다.

마지막으로 람다의 파라미터 이름을 디폴트 이름인 *it*으로 바꾸면 람다 식을 더 간단하게 바꿀 수 있다.

**람다의 파라미터가 하나 뿐이고 그 타입을 컴파일러가 추론할 수 있으면 it을 쓸 수 있다.**

```kotlin
people.maxBy { it.age }
```

> **주의사항**
>
> *it*은 코드를 간단하게 만들어주지만 너무 남용해서 쓰면 코드를 추론하기 힘들어진다. 특히 람다 안에 람다가 중첩되는 경우 *it*이 가리키는 파라미터가 어떤 람다에 속해있는지 구별하기 힘들어진다.

여러 줄로 이뤄진 경우 본문의 맨 마지막에 있는 식이 람다의 결괏값이 된다.

```kotlin
fun main() {
	val sum = {x: Int, y: Int -> println("Computing the sum of $x and $y...") 
        x + y}
    println(sum(2,3))
}
```

## 1.1 현재 영역에 있는 변수에 접근

람다를 함수 안에서 정의하면, 함수의 파라미터뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 사용할 수 있다.

```kotlin
private fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach { println("$prefix $it") } // 람다 안에서 함수의 "prefix" 파라미터를 사용한다.
}

@Test
fun test6() {
    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessagesWithPrefix(errors, "Error: ")
}

// Error:  403 Forbidden
// Error:  404 Not Found
```

자바와 다른 점 중 중요한 것은 코틀린 람다 안에서는 final 변수가 아닌 변수에 접근할 수 있다는 점이다. 또한 람다 안에서 바깥의 변수를 변경해도 된다.

```kotlin
private fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors++ // 람다 안에서 람다 밖의 변수를 변경한다
        } else if (it.startsWith("5")) {
            serverErrors++
        }
    }
    println("$clientErrors client errors, $serverErrors server errors")
}

@Test
fun test7() {
    val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
    printProblemCounts(responses)
}
```

prefix, clientErrors, serverErrors 와 같이 람다 안에서 사용하는 외부 변수를 람다가 포획(capture)한 변수라고 부른다.

기본적으로 함수 안에 정의된 로컬 변수의 생명 주기는 함수가 반환되면 끝난다. 하지만 어떤 함수가 자신의 로컬 변수를 포획한 람다를 반환하거나 다른 변수에 저장한다면 로컬 변수의 생명 주기와 함수의 생명주기는 달라질 수 있다.

포획한 변수가 있는 람다를 저장해서 함수가 끝난 뒤에 실행해도 람다의 본문 코드는 여전히 포획한 변수를 읽고 쓸 수 있다. 이것이 가능한 이유는,

- 파이널 변수(val)를 포획한 경우, 람다 코드를 변수 값과 함께 저장한다.
- 일반 변수(var)인 경우, 변수를 특별한 래퍼로 감싸서 나중에 변경하거나 읽을 수 있게 한 다음, 래퍼에 대한 참조를 람다 코드와 함께 저장한다.

변수 캡처에 한 가지 주의해야 할 점이 있다.

람다를 이벤트 핸들러나 다른 비동기적으로 실행되는 코드로 활용할 경우 함수 호출이 끝난 다음에 로컬 변수가 변경될 수도 있다.

```kotlin
private fun tryToCountButtonClicks(button: Button): Int {
    var clickCount = 0
    button.onClick { clickCount++ }
    return clickCount
}
```

이 함수는 항상 "0"을 반환한다.

*onClick* 핸들러는 호출될 때마다 *clickCount* 값을 증가시키지만 그 값의 변경을 관찰할 수는 없다. *tryToCountButtonClicks* 가 *clickCount*를 반환한 다음에 호출되기 때문이다.

이 함수를 제대로 구현하려면 *clickCount*를 함수의 내부가 아니라 클래스의 프로퍼티나 전역 프로퍼티 등의 위치로 빼내서 나중에 변수 변화를 살펴볼 수 있게 해야 한다.
