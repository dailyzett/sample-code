# 목차

- [목차](#목차)
- [1. 확장 메서드와 확장 프로퍼티](#1-확장-메서드와-확장-프로퍼티)
  - [1.1 임포트와 확장 메서드](#11-임포트와-확장-메서드)
  - [1.2 자바에서 확장 메서드 호출](#12-자바에서-확장-메서드-호출)
  - [1.3 확장 메서드는 오버라이드 할 수 없다.](#13-확장-메서드는-오버라이드-할-수-없다)
  - [1.4 확장 프로퍼티](#14-확장-프로퍼티)
- [2. 컬렉션 처리](#2-컬렉션-처리)
  - [2.1 가변 인자 함수](#21-가변-인자-함수)
  - [2.2 값의 쌍 다루기: 중위 호출과 구조 분해 선언](#22-값의-쌍-다루기-중위-호출과-구조-분해-선언)
- [3. 문자열과 정규식 다루기](#3-문자열과-정규식-다루기)
  - [3.1 정규식을 사용하지 않고 문자열 다루기](#31-정규식을-사용하지-않고-문자열-다루기)
- [4. 코드 다듬기: 로컬 함수와 확장](#4-코드-다듬기-로컬-함수와-확장)

# 1. 확장 메서드와 확장 프로퍼티

코틀린과 자바를 함께 쓰려고할 때 자바 언어에서도 코틀린이 제공하는 여러가지 편의 기능을 그대로 사용할 수 있다면 매우 편할 것이다.


```kotlin
fun String.lastChar(): Char = this[this.length - 1]
```

다음과 같은 메서드를 만들어두면 이 함수를 호출하는 구문은 다른 일반 클래스 멤버를 호출하는 것과 같다.

```kotlin
@Test
fun lastIndexTest() {
    //결괏값: n
    println("Kotlin".lastChar())
}
```

확장 메서드를 만들려면 추가하려는 메서드 이름 앞에 그 메서드가 확장할 클래스의 이름을 덧붙이기만 하면 된다.

- 이 때 클래스 이름을 **Receiver Type** 이라고 한다.
- 피호출 오브젝트는 **Receiver Object** 라고 부른다.

여기서는 _String_ 이 리시버 타입이고 _kotlin_ 이 리시버 오브젝트이다.


## 1.1 임포트와 확장 메서드

- 확장 메서드를 정의해도 *import* 문은 필수이다.

```kotlin
import strings.lastChar
//import strings.* 도 가능
```

```kotlin
import strings.lastChar as last
```

*as* 키워드를 이용하면 임포트한 클래스나 메서드를 다른 이름으로 호출할 수 있다. 한 파일 안에서 다른 여러 패키지에 속해있는 이름이 같은 메서드를 가져와야할 때 이름을 바꿔서 임포트하면 이름 충돌을 막을 수 있다.

## 1.2 자바에서 확장 메서드 호출

```java
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import strings.JoinKtKt;

@Slf4j
public class JavaTest {

    @Test
    void test() {
        log.info("text={}", JoinKtKt.lastChar("Java"));
    }
}
```

## 1.3 확장 메서드는 오버라이드 할 수 없다.

확장 메서드는 클래스 밖에 선언된다. 이름과 파라미터가 같아도 실제로 확장 메서드를 호출할 때 리시버 오브젝트로 지정한 변수의 정적 타입에 의해 어떤 확장 메서드가 호출될지 결정된다. 즉, 그 변수에 저장된 객체의 동적인 타입에 의해 확장 메서드가 결정되지 않는다.

```kotlin
fun View.showOff() = println("I'm a View!")
fun Button.showOff() = println("I'm a button!")

val view: View = Button()
view.showOff()
//결과: I'm a view!
```

*view* 가 가리키는 객체의 실제 타입이 *Button* 이지만, 이 경우 *view* 타입이 *View* 이므로 무조건 *View* 의 확장 메서드가 호출된다.

## 1.4 확장 프로퍼티

> 확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의
> 구문으로 사용할 수 있는 API를 추가할 수 있다.

```kotlin
val String.lastChar: Char
    get() = get(length - 1)

@Test
fun extensionPropertyTest() {
    println("Kotlin".lastChar)
}
```

# 2. 컬렉션 처리

- *vararg* 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 메서드를 정의할 수 있다.
- 중위 메서드 호출 구문을 사용하면 인자가 하나뿐인 메서드를 간편하게 호출할 수 있다.

## 2.1 가변 인자 함수

코틀린의 가변 길이 인자는 자바와 비슷하다. 하지만 문법은 조금 다르다. 타입 뒤에 "..."을 붙이는 대신 파라미터 앞에 *vararg* 변경자를 붙인다.

이미 배열에 들어있는 원소를 가변 인자로 넘길 때도 자바는 배열 그 자체를 넘기면 되지만 코틀린은 배열을 명시적으로 풀어서 배열의 각 원소가 인자로 전달되게 해야한다.

```kotlin
private fun hello(args: Array<String>) {
    val list = listOf("args: ", *args)
    println(list)
}
```

보다시피 `*` 라는 스프레드 연산자가 이런 작업을 도와준다. 위 예제는 배열에 들어있는 값과 다른 여러 값들을 함께 써서 함수를 호출할 수 있음을 보여준다. 자바에서는 이러한 것이 불가능하다.

## 2.2 값의 쌍 다루기: 중위 호출과 구조 분해 선언

```kotlin
val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
```

여기서 *to* 는 코틀린 키워드가 아니다. 중위 호출(infix call)이라는 특별한 방식으로 *to* 라는 일반 메서드를 호출한 것이다.

- 1.to("one") : 일반적인 방식으로 호출
- 1 to "one" : 중위 호출 방식으로 호출

to 함수는 Pair 인스턴스를 반환한다. *Pair*은 코틀린 표준 라이브러리 클래스로 두 원소로 이루어진 순서쌍을 반환한다. *Pair* 로 두 변수를 즉시 초기화 가능하다.

```kotlin
val (number, name) = 1 to "one"
```

이런 기능을 **구조 분해 선언**이라고 한다.

다음은 *mapOf* 함수의 선언 부분이다.

```kotlin
fun<K, V> mapOf(vararg values: Pair<K, V>): Map<K, V>
```

*listOf* 와 마찬가지로 원하는 개수 만큼 인자를 전달할 수 있다.

# 3. 문자열과 정규식 다루기

자바 split 메서드로는 "." 을 사용해 문자열을 분리할 수 없다. 정규식을 이용하기 때문에 "." 은 모든 문자열을 나타내는 정규식으로 해석되기 때문이다.

코틀린은 자바의 *split* 대신 여러 가지 다른 조합의 파라미터를 받는 *split* 확장 함수를 제공해 혼동을 최소한으로 줄인다.

```kotlin
@Test
fun stringTest() {
    println("12.345-6.A".split("\\.".toRegex()))
    println("12.345-6.A".split(".", "-"))
}

//결과: [12, 345-6, A]
//결과: [12, 345, 6, A]
```

- 코틀린에서는 *toRegex* 확장 함수를 사용해 문자열을 정규식으로 변환할 수 있다.

## 3.1 정규식을 사용하지 않고 문자열 다루기

파일의 전체 경로명을 디렉터리, 파일 이름, 확장자로 구분지어 보자.

```kotlin
private fun parsePath(path: String) {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")

    println("Dir: $directory, fullName: $fullName, name: $fileName, ext: $extension")
}

@Test
fun parsePathTest() {
    val path = "/Users/yole/kotlin-book/chapter.adoc"
    parsePath(path)
}
```
결과:
```text
Dir: /Users/yole/kotlin-book
fullName: chapter.adoc
name: chapter
ext: adoc
```

보다시피 정규식을 사용하지 않고 확장 함수만으로 쉽게 문자열을 다룰 수 있다.

# 4. 코드 다듬기: 로컬 함수와 확장

자바 언어로 개발하다보면 반복하지 않기 위해 메서드 추출 리팩토링을 적용해서 긴 메서드를 부분 부분 나눠 각 부분을 재활용하는 방식을 사용한다.

다만 기능별로 너무 작은 단위로 쪼개다보면 오히려 전체 코드 흐름을 파악하기 어렵다는 단점이 생긴다. 별도의 이너 클래스를 생성하는 방법도 있지만, 코드를 조작하기 위해 불필요한 코드가 생긴다.

코틀린에는 이것보다 좋은 해법이 있다. 코틀린에서는 함수에서 추출한 함수를 원 함수 내부에 중첩시킬 수 있다.

예를 들어 사용자를 데이터베이스에 저장하는 함수가 있을 때, 데이터베이스에 사용자 객체를 저장하기 전에 각 필드를 검증해야한다.

```kotlin
class User(val id: Int, val name: String, val address: String)

fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalArgumentException(
                "Can't save user ${user.id}: empty Name"
        )
    }

    if (user.address.isEmpty()) {
        throw IllegalArgumentException(
                "Can't save user ${user.id}: empty Address"
        )
    }
}
```

User 필드 수가 많아질수록 if 문도 많아질 것이고 같은 구조가 계속 반복될 것이다. 이것을 코틀린의 함수 내부 중첩을 사용하면 아래와 같이 리팩토링할 수 있다.

```kotlin
fun saveUser(user: User) {

    fun validate(user: User, value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user ${user.id}: empty $fieldName"
            )
        }
    }

    validate(user, user.name, "Name")
    validate(user, user.address, "Address")
}
```

중복이 훨씬 줄었다. 하지만 User 객체를 로컬 함수에 일일이 전달해줘야 한다는 사실이 조금 아쉽다. 다행히도, 이 부분은 쉽게 고칠 수 있다.

로컬 함수는 자신이 속한 바깥 함수의 모든 파라미터와 변수를 사용할 수 있기 때문에 그냥 User 파라미터를 빼주기만 하면 된다!

```kotlin
fun saveUser(user: User) {

    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user ${user.id}: empty $fieldName"
            )
        }
    }

    validate(user.name, "Name")
    validate(user.address, "Address")
}
```

여기서 더 나아간다면 검증 로직을 User 클래스를 확장한 함수로 만들 수도 있다.

```kotlin
class User(val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user ${id}: empty $fieldName"
            )
        }
    }

    validate(name, "Name")
    validate(address, "Address")
}

fun saveUser(user: User) {
    user.validateBeforeSave()

    //DB에 저장하는 로직
}
```

- User.validateBeforeSave 를 saveUser 내부에 로컬 함수로 넣을 수도 있지만 중첩 함수의 깊이가 깊어지면 해석하기가 상당히 어려워진다.
- 따라서 한 단계의 함수만 중첩시키는 방향으로 생각하자.

