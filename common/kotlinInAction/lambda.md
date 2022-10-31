# 목차

- [목차](#목차)
- [1. 람다 식과 멤버 참조](#1-람다-식과-멤버-참조)
  - [1.1 현재 영역에 있는 변수에 접근](#11-현재-영역에-있는-변수에-접근)
  - [1.2 멤버 참조](#12-멤버-참조)
- [2. 컬렉션 함수형 API](#2-컬렉션-함수형-api)
  - [2.1 filter 와 map](#21-filter-와-map)
  - [2.2 all, any, count, find: 컬렉션에 술어 적용](#22-all-any-count-find-컬렉션에-술어-적용)
  - [2.3 groupBy: 리스트를 여러 그룹으로 이뤄진 맵으로 변경](#23-groupby-리스트를-여러-그룹으로-이뤄진-맵으로-변경)
  - [2.4 flatMap 과 flatten: 중첩된 컬렉션 안의 원소 처리](#24-flatmap-과-flatten-중첩된-컬렉션-안의-원소-처리)
- [3. 지연 계산 컬렉션 연산](#3-지연-계산-컬렉션-연산)
  - [3.1 시퀀스 연산 실행: 중간 연산과 최종 연산](#31-시퀀스-연산-실행-중간-연산과-최종-연산)
  - [3.2 시퀀스 만들기](#32-시퀀스-만들기)
- [4. 수신 객체 지정 람다: with 와 apply](#4-수신-객체-지정-람다-with-와-apply)
  - [4.1 with 함수](#41-with-함수)
  - [4.2 apply 함수](#42-apply-함수)

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

## 1.2 멤버 참조

:: 을 사용하는 식을 멤버 참조라고 부른다. 멤버 참조는 프로퍼티나 메서드를 단 하나만 호출하는 함수 값을 만들어준다.

```kotlin
private fun salute() = println("Salute!")

@Test
fun test8() {
    run(::salute)
}
```

참고로 *run*은 인자로 받은 람다를 호출한다.

람다가 인자가 여럿인 다른 함수한테 작업을 위임하는 경우 직접 위임 함수에 대한 참조를 제공하면 편리하다.

```kotlin
val action = { person: Person, message: String -> 
    sendMail(person, message)}
  
val nextAction = ::sendMail
```

**생성자 참조**를 사용하면 클래스 생성 작업을 연기하거나 저장해둘 수 있다.

```kotlin
data class Person(val name: String, val age: Int)

fun main() {
    val createPerson = ::Person // Person 의 인스턴스를 만드는 동작을 값으로 저장
    val p = createPerson("Bob", 39)
    println(p)
}

//결괏값
//Person(name=Bob, age=29)
```

# 2. 컬렉션 함수형 API

## 2.1 filter 와 map

*filter* 함수는 컬렉션을 순회하면서 주어진 람다에 각 원소를 넘겨서 람다가 true 를 반환하는 원소만 모은다.

```kotlin
@Test
fun test9() {
    val list = listOf(1, 2, 3, 4)
    println(list.filter { it % 2 == 0 })
}
```

하지만 *filter*는 원소를 변환할 수는 없다. 변환하려면 map 을 써야한다.

다음과 같이 숫자로 이뤄진 리스트를 각 숫자의 제곱이 모인 리스트로 바꿀 수 있다.

```kotlin
@Test
fun test10() {
    val list = listOf(1, 2, 3, 4)
    println(list.map { it * it })
}
```

## 2.2 all, any, count, find: 컬렉션에 술어 적용

컬렉션의 모든 원소가 어떤 조건을 만족하는지 판단하는 연산이 있다. 코틀린에서는 all 과 any 가 여기에 포함된다.

count 함수는 조건을 만족하는 원소의 개수를 반환.

find 함수는 조건을 만족하는 첫 번째 원소를 반환한다.

```kotlin
data class Person(val name: String, val age: Int)

fun main() {
    val canBeInClub27 = {p: Person -> p.age <= 27} // 27살이 넘는지 확인하는 람다 함수를 변수에 담는다
    val people = listOf(Person("Alice", 27), Person("Bob", 31)) 
    println(people.all(canBeInClub27)) // all 을 이용해 해당 조건을 만족하는지 조사. 결과는 당연히 false
}
```

만약 술어를 만족하는 원소가 하나라도 있는지 궁금하면 *any* 를 사용한다.

어떤 조건에 대해 *!all*과 그 조건의 부정에 대해 *any*를 수행한 결과는 같다. 또 어떤 조건에 대해 *!any*를 수행한 결과와 그 조건의 부정에 대해 *all*을 수행한 결과도 같다. 하지만 가독성이 떨어지므로 *all*과 *any* 에는 ! 를 붙이지 않는게 좋다.

```kotlin
println(people.find(canBeInClub27)) // 술어를 만족하는 원소를 하나 찾는다
```

## 2.3 groupBy: 리스트를 여러 그룹으로 이뤄진 맵으로 변경

컬렉션의 모든 원소를 어떤 특성에 따라 여러 그룹으로 나누고 싶다고 하자.

```kotlin
data class Person(val name: String, val age: Int)

fun main() {
    val people = listOf(Person("Alice", 31), Person("Bob", 29), Person("Carol", 31))
    println(people.groupBy {it.age})
}

// {31=[Person(name=Alice, age=31), Person(name=Carol, age=31)], 29=[Person(name=Bob, age=29)]}
```

각 그룹은 리스트다. 따라서 groupBy 결과 타입은 Map<Int, List&lt;Person&gt;> 가 된다. 필요하면 이 맵을 mapKeys 나 mapValues 등을 사용해 변경할 수 있다.

## 2.4 flatMap 과 flatten: 중첩된 컬렉션 안의 원소 처리

flatMap 함수는 먼저 인자로 주어진 람다를 컬렉션의 모든 객체에 적용하고(또는 매핑하고) 람다를 적용한 결과 얻어지는 여러 리스트를 한 리스트로 한 데 모은다(또는 펼친다)

매핑하고 펼친다를 합치면 map-flatten 이 되고 이것이 flatMap 이다.

```kotlin
class Book(val title: String, val authors: List<String>)

fun main() {
	val books = listOf(Book("Thursday Next", listOf("Jasper Fforde")), 
                      Book("Mort", listOf("Terry Pratchett")),
                      Book("Good Omens", listOf("Terry Prachett", "Neil Gaiman")))
  
    println(books.flatMap {it.authors}.toSet())
}

//[Jasper Fforde, Terry Prachett, Neil Gaiman]
```

책 저자는 여러명일 수 있다. book.authors 프로퍼티는 작가를 모아둔 컬렉션이다.

flatMap 함수는 모든 책의 작가를 리스트 하나로 모은다. toSet은 flatMap의 결과 리스트에서 중복을 없애고 집합으로 만든다.

리스트의 리스트가 있는데 모든 중첩된 리스트의 원소를 한 리스트로 모아야 한다면?

flatMap을 떠올릴 수 있을 것이다. 특별히 반환해야할 내용이 없다면 리스트를 펼치기만 하면 된다. 그럴 때는 flatten 을 사용하자.

# 3. 지연 계산 컬렉션 연산

map이나 filter는 결과 컬렉션을 **즉시** 생성한다. 이는 컬렉션 함수를 연쇄하면 매 단계마다 계산 중간 결과를 새로운 컬렉션에 임시로 담는다는 말이다.

**시퀀스**를 사용하면 중간 임시 컬렉션을 사용하지 않아도 된다.

```kotlin
people.map(Person::name).filter { it.startsWith("A") }
```

filter 와 map은 리스트를 반환한다. 이는 이 연쇄 호출이 리스트를 두 개 만든다는 뜻이다. 원본 리스트가 많아지면 많아질수록 효율이 급격하게 나빠진다.

```kotlin
data class Person(val name: String)

fun main() {
  
    val people = listOf(Person("Alice"), 
                        Person("Bob"), 
                        Person("Hello"), 
                        Person("Apple"))
  
	val list = people.asSequence()
        .map(Person::name)
    	.filter{it.startsWith("A")}
    	.toList()
  
    println(list)
}

// [Alice, Apple]
```

asSequence 를 사용하면 중간 결과를 사용하는 컬렉션이 생기지 않기 때문에 원소가 많은 경우 눈에 띄게 성능이 좋아진다.

코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다. 이 인터페이스의 강점은 인터페이스 위에 구현된 연산이 계산을 수행하는 방법 때문에 생긴다.

시퀀스의 원소는 **필요할 때 비로소 계산된다.** 따라서 중간 처리 결과를 저장하지 않고도 연산을 연쇄적으로 적용해서 효율적으로 계산을 수행할 수 있다.

asSequence 확장 함수를 호출하면 어떤 컬렉션이든 시퀀스로 바꿀 수 있다. 시퀀스를 다시 리스트로 바꿀 때 toList 를 사용한다.

## 3.1 시퀀스 연산 실행: 중간 연산과 최종 연산

시퀀스에 대한 연산은 **중간 연산**과 **최종 연산**으로 나뉜다.

중간 연산은 다른 시퀀스를 반환하고 최종 연산은 결과를 반환한다.

중간 연산은 항상 지연 계산된다.

```kotlin
val list = people.asSequence()
    .map(Person::name)
    .filter{it.startsWith("A")}
```

이 코드를 실행하면 아무것도 출력되지 않는다. 이는 map 과 filter 변환이 늦춰져서 결과를 얻을 필요가 있을 때 적용된다는 뜻이다. 즉, toList()가 오기전까지 아무것도 하지 않는다.

최종 연산을 호출애햐만 연기됐던 모든 연산이 수행된다.

```kotlin
fun main() {
	listOf(1, 2, 3, 4).asSequence()
    	.map { print("map($it) "); it * it}
        .filter { print("filter($it) "); it % 2 == 0}
        .toList()
}

//map(1) filter(1) map(2) filter(4) map(3) filter(9) map(4) filter(16)
```

함수 호출 순서를 보자. 시퀀스의 모든 연산은 각 원소에 대해 순차적으로 적용된다.

반면 시퀀스를 사용하지 않으면 map 함수를 각 원소에 대해 먼저 수행하고 이후에 filter 를 수행한다.

```kotlin
fun main() {
	listOf(1, 2, 3, 4)
    	.map { print("map($it) "); it * it}
        .filter { print("filter($it) "); it % 2 == 0}
        .toList()
}

//map(1) map(2) map(3) map(4) filter(1) filter(4) filter(9) filter(16) 
```

차이를 보면 알듯이, 시퀀스를 사용하면 결과가 얻어지면 그 이후의 원소에 대해서는 변환이 이뤄지지 않을 수도 있다.

map과 find를 이용해 리스트의 각 숫자를 제곱하고 그 중에서 find로 3보다 큰 첫 번째 원소를 찾아보자.

```kotlin
fun main() {
	var result = listOf(1, 2, 3, 4).asSequence()
    	.map { print("map($it) "); it * it}
        .find{ it > 3 }
  
  
    println(result)
}

//map(1) map(2) 4
```

map(1), map(2) 딱 필요한 만큼만 실행된 것을 볼 수 있다.

> **참고**
>
> 자바 8의 스트림과 코틀린의 시퀀스는 개념이 같다. 코틀린이 같은 개념을 따로 구현해 제공하는 이유는 예전 버전 자바를 사용하는 경우 자바 8에 있는 스트림이 없기 때문이다.

## 3.2 시퀀스 만들기

시퀀스를 만드는 다른 방법으로 generateSequence 함수를 사용할 수 있다.

```kotlin
fun main() {
	val naturalNumbers = generateSequence(0) {it + 1}
    val numberTo100 = naturalNumbers.takeWhile {it <= 100}
    println(numberTo100.sum())
}

//5050
```

여기서는 sum이 최종연산이기 때문에 sum이 호출되기 전까지 아무것도 하지 않는다.

# 4. 수신 객체 지정 람다: with 와 apply

자바의 람다에는 없는 코틀린 람다의 독특한 기능이 있다. 바로 수신 객체를 명시하지 않고 람다의 본문 안에서 다른 객체의 메서드를 호출할 수 있게 하는 것이다.

그런 람다를 **수신 객체 지정 람다**(lambda with receiver)라고 부른다.

## 4.1 with 함수

일단 다음 코드를 보자.

```kotlin
private fun alphabet(): String {
    val result = StringBuilder()
    for (letter in 'A'..'Z') {
        result.append(letter)
    }
    result.append("\nNow I know the alphabet!")
    return result.toString()
}

@Test
fun alphaTest() {
    println(alphabet())
}

// ABCDEFGHIJKLMNOPQRSTUVWXYZ
// Now I know the alphabet!

```

이제 이 코드를 with 를 이용해 리팩토링한다.

```kotlin
private fun alphabet(): String {
    val stringBuilder = StringBuilder()
    return with(stringBuilder) { // 메서드를 호출하려는 수신 객체를 지정
        for (letter in 'A'..'Z') {
            this.append(letter) // this를 명시해서 앞에서 지정한 수신 객체의 메서드를 호출
        }
        append("\nNow I know the alphabet") // this를 생략하고 메서드를 호출한다.
        this.toString() // 람다에서 값을 반환한다.
    }
}
```

with문은 언어가 제공하는 특별한 구문처럼 보이지만, 실제로는 파라미터가 두 개 있는 함수이다. 여기서 첫 번째 파라미터는 stringBuilder 이고 두 번째 파라미터는 람다다.

수신 객체 this의 멤버를 호출할 때는 생략할 수 있다. 따라서 위 코드의 this도 전부 생략가능하다.

그리고 불필요한 StringBuilder() 인스턴스도 삭제해보자.

```kotlin
private fun alphabet(): String {
    return with(StringBuilder()) { // 메서드를 호출하려는 수신 객체를 지정
        for (letter in 'A'..'Z') {
            append(letter) // this를 명시해서 앞에서 지정한 수신 객체의 메서드를 호출
        }
        append("\nNow I know the alphabet") // this를 생략하고 메서드를 호출한다.
        toString() // 람다에서 값을 반환한다.
    }
}
```

코드가 훨씬 간단해졌다.

> **참고**
>
> 만약 with 를 사용하는 코드가 들어있는 클래스와 with에게 인자로 넘긴 객체의 클래스에 같은 메서드가 있으면 this 참조 앞에 레이블을 붙여 충돌을 피할 수 있다.
>
> this@OuterClass.toString() 처럼 말이다.

with가 반환하는 값은 람다 코드를 실행한 결과며, 그 결과는 람다 식의 본문에 있는 마지막 식의 값이다.

하지만 람다의 결과 대신 수신 객체가 필요한 경우도 있다. 그럴 때는 apply 라이브러리 함수를 사용한다.

## 4.2 apply 함수

with와 거의 동일하지만 apply는 항상 자신에게 전달된 객체를 반환한다는 점이 다르다.

```kotlin
private fun alphabet() = StringBuilder().apply {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I Know the alphabet!")
}.toString()
```

이런 apply 함수는 객체의 인스턴스를 만들면서 즉시 프로퍼티 일부를 초기화해야 하는 경우 유용하다. 자바에서는 보통
Builder 객체가 이런 역할을 담당한다.

apply와 with는 수신 객체 지정 람다를 사용하는 일반적인 예제 중 하나고 더 구체적인 함수를 비슷한 패턴으로 사용할 수 있다.
예를 들어 buildString 라이브러리 함수는 StringBuilder 객체를 만드는 일과 toString을 호출해주는 일을 알아서 해준다.

```kotlin
private fun alphabet() = buildString {
    for (letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow I know the alphabet!")
}
```

buildString 함수는 StringBuilder 를 이용해서 String 객체를 만들 때 사용할 수 있는 좋은 방법이다.

