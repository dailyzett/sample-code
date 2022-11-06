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


## 1.3