# 목차

- [목차](#목차)
    - [2.3.3 when과 함께 임의의 객체를 사용](#233-when과-함께-임의의-객체를-사용)
    - [2.3.4 스마트 캐스트: 타입 검사와 타입 캐스트를 조합](#234-스마트-캐스트-타입-검사와-타입-캐스트를-조합)
    - [2.3.5 리팩토링: if를 when으로 변경](#235-리팩토링-if를-when으로-변경)
    - [2.3.7 if와 when의 분기에서 블록 사용](#237-if와-when의-분기에서-블록-사용)
  - [2.4 대상을 이터레이션: while과 for 루프](#24-대상을-이터레이션-while과-for-루프)
    - [2.4.1 while 루프](#241-while-루프)
    - [2.4.2 수에 대한 이터레이션: 범위와 수열](#242-수에-대한-이터레이션-범위와-수열)
    - [2.4.3 맵에 대한 이터레이션](#243-맵에-대한-이터레이션)
    - [2.4.4 in으로 컬렉션이나 범위의 원소 검사](#244-in으로-컬렉션이나-범위의-원소-검사)
  - [2.5 코틀린의 예외 처리](#25-코틀린의-예외-처리)
    - [2.5.1 try, catch, finally](#251-try-catch-finally)
    - [2.5.2 try를 식으로 이용](#252-try를-식으로-이용)

### 2.3.3 when과 함께 임의의 객체를 사용

```kotlin
import com.begin.colors.Color
import com.begin.colors.Color.*


class Test {
    @Test
    fun testA() {
        println(mix(BLUE, YELLOW))
    }
}

fun mix(c1: Color, c2: Color) =
    when(setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("dirty color")
    }
```

코틀린에서 when은 기능은 자바의 switch와 같지만 기능이 훨씬 강력하다. 상수뿐만 아니라 임의의 객체까지도 넣을 수 있기 때문이다.
코드를 보면 _setOf_ 라는 메서드가 _switch_ 분기 조건으로써 쓰이고 있는 것을 볼 수 있다.

### 2.3.4 스마트 캐스트: 타입 검사와 타입 캐스트를 조합

```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```

```kotlin
class Test2 {

    @Test
    fun test0 (){
        println(eval(Sum(Sum(Num(1), Num(2)), Num (4))))
    }

    fun eval(e: Expr) : Int {
        if(e is Num) {
            val n = e as Num // Num 타입으로 변환하는데 이는 불필요한 중복이다.
            return n.value
        }
        if(e is Sum) {
            return eval(e.right) + eval(e.left) // 변수 e에 대해서 스마트 캐스트를 사용한다.
        }
        throw IllegalArgumentException("Unknown Expression")
    }
}
```

- is는 자바의 instance of 와 비슷하다. 하지만 자바에서는 타입 검사 후 명시적으로 변수 타입을 캐스팅해야 한다.
- 코틀린에서는 프로그래머 대신 컴파일러가 캐스팅을 해준다. 어떤 변수가 원하는 타입인지 *is* 로 검사하고 나면 굳이 변수를 원하는 타입으로 캐스팅하지 않아도 된다.
- 원하는 타입으로 캐스팅하지 않아도 처음부터 그 변수가 원하는 타입으로 선언된 것처럼 사용할 수 있는데, 이것을 **스마트 캐스트** 라고 한다.

주의할 점은 val이 아니거나 val이지만 커스텀 접근자를 사용하는 경우 스마트 캐스트를 사용할 수 없다. 프로퍼티에 대한 접근이 항상 같다고 보장할 수 없기 때문이다.

### 2.3.5 리팩토링: if를 when으로 변경

코틀린의 if(a>b) a else b 는 자바의 a > b ? a : b 처럼 동작한다. 코틀린은 if가 식을 만들어내므로 삼항 연산자가 달리 필요없다.

이런 특성을 이용해서 2.3.4 의 eval 함수를 더욱 간단하게 리팩토링할 수 있다.

```kotlin
    fun eval(e: Expr): Int =
            if (e is Num) {
                e.value
            } else if (e is Sum) {
                eval(e.right) + eval(e.left)
            } else {
                throw IllegalArgumentException("Unknown expression")
            }
```

if 분기에 식이 하나 밖에 없다면 중괄호를 생략해도 된다. 그리고 위의 if 문은 코틀린의 when 으로 전환 가능하다.

```kotlin
    fun eval(e: Expr): Int =
            when (e) {
                is Num ->
                    e.value
                is Sum ->
                    eval(e.right) + eval(e.left)
                else ->
                    throw IllegalArgumentException("Unknown expression")
            }
```

when 식을 동등성 검사가 아닌 다른 기능에도 쓸 수 있다.

### 2.3.7 if와 when의 분기에서 블록 사용

```kotlin
    fun evalWithLogging(e: Expr): Int =
            when (e) {
                is Num -> {
                    println("num: ${e.value}")
                    e.value
                }
                is Sum -> {
                    val left = evalWithLogging(e.left)
                    val right = evalWithLogging(e.right)
                    println("sum: $left + $right")
                    left + right
                }
                else -> throw IllegalArgumentException("Unknown expression")
            }
```

*블록의 마지막 식이 블록의 결과* 라는 규칙은 블록이 값을 만들어내야하는 경우 항상 성립한다.
하지만 이 규칙은 함수에 대해서는 성립하지 않는다. 식이 본문인 함수는 블록을 가질 수 없고 블록이 본문인 함수는 내부에 return문이 반드시 있어야한다.

## 2.4 대상을 이터레이션: while과 for 루프

### 2.4.1 while 루프

코틀린에는 while 과 do-while 이 있는데 문법은 자바와 똑같다. 이 두 루프는 코틀린에서 새로 추가한 기능도 없다.

### 2.4.2 수에 대한 이터레이션: 범위와 수열

```kotlin
    fun fizzBuzz(i: Int) = when {
        i % 15 == 0 -> "FizzBuzz "
        i % 3 == 0 -> "Fizz "
        i % 5 == 0 -> "Buzz "
        else -> "$i "
    }
```

```kotlin
    @Test
    fun test0() {
        for(i in 1..100) {
            println(fizzBuzz(i))
        }
    }
```

이 코드는 1-100 범위의 정수에 대해 이터레이션한다.

```kotlin
    @Test
    fun test0() {
        for(i in 100 downTo 1 step 2) {
            println(fizzBuzz(i))
        }
    }
```

downTo 키워드를 사용해 100부터 1까지, step 2 씩 전진해 짝수만 이터레이션할 수 있다.

### 2.4.3 맵에 대한 이터레이션

```kotlin
    val binaryReps = TreeMap<Char, String>()

    @Test
    fun test0() {
        for(c in 'A'..'F'){
            val binary = Integer.toBinaryString(c.code)
            binaryReps[c] = binary
        }

        for((letter, binary) in binaryReps) {
            println("$letter=$binary")
        }
    }
```

- 원소를 풀어서 letter, binary 라는 두 변수에 저장한다.
- letter 에는 키가 들어가고 binary 에는 값이 들어간다.

binaryReps[c] = binary 라는 코드는 binaryReps.put(c, binary) 와 똑같다. 맵에 사용했던 구조 분해 구문을 맵이 아닌 컬렉션에도 활용할 수 있다.
그런 분해 구조 구문을 사용하면 원소의 현재 인덱스를 유지하면서 컬렉션을 이터레이션할 수 있다.

### 2.4.4 in으로 컬렉션이나 범위의 원소 검사

```kotlin
    fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
    fun isNotDigit(c: Char) = c !in '0'..'9'

    @Test
    fun letterDigitTest() {
        println(isLetter('q'))
        println(isNotDigit('x'))
    }
```

어떤 문자가 글자인지를 검사하는 방법은 간단하다. 내부적으로 교묘한 부분도 전혀 없다. in과 !in을 when 식에서 사용해도 된다.

```kotlin
    fun recognize(c: Char) = when (c) {
        in '0'..'9' -> "It's a digit"
        in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
        else -> "I don't know..."
    }
```

범위는 문자에만 국한되지 않는다. 비교가 가능한 클래스라면(java.lang.Comparable 인터페이스를 구현한 클래스라면) 그 클래스의 인스턴스 객체를 사용해 범위를 만들 수 있다.

```kotlin
    @Test
    fun letterDigitTest() {
        println("Kotlin" in "Java".."Scala") // true
        println("Kotlin" in setOf("Java", "Scala")) // false
    }
```

in 연산을 사용해서 문자열이 범위 안에 들어가는지 체크 할 수 있다. *setOf* 는 집합을 구하는 함수이므로 당연히 false 결과값을 도출한다.

## 2.5 코틀린의 예외 처리

```kotlin
val percent = if (number in 1..100)
    number
else
    throw IllegalArgumentException(
            "A percentage value must be between 0 and 100: $number"
    )
```

코드를 보면 알겠지만 자바와 다르게 예외를 던질 때 *new* 연산자가 필요없다.

### 2.5.1 try, catch, finally

```kotlin
fun readNumber(reader: BufferedReader): Int? {
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: java.lang.NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

@Test
fun tryCatchTest() {
    val reader = BufferedReader(StringReader("239"))
    println(readNumber(reader))
}
```

자바 코드와 가장 큰 차이점은 *throws* 절이 코드에 없다는 점이다. 자바에서는 함수를 작성할 때 IOException을 던져야한다. 이유는 IOException이 checked exception이기 때문이다.

다른 최신 JVM 언어와 마찬가지로 코틀린도 checked exception 과 unchecked exception을 구별하지 않는다. 코틀린에서는 함수가 던지는 예외를 지정하지 않고 발생한 예외를 잡아내도 되고
잡아내지 않아도 된다.

실제로 *BufferedReader.close* 는 *IOException* 을 발생시키는데 실제 스트림을 닫다가 실패하는 경우 클라이언트 프로그램이 취할 수 있는 의미 있는 동작은 없다.

### 2.5.2 try를 식으로 이용

```kotlin
fun readNumber(reader: BufferedReader) {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: java.lang.NumberFormatException) {
        return
    }
    println(number)
}

@Test
fun readNumberTest() {
    val reader = BufferedReader(StringReader("not a number"))
    readNumber(reader)
}
```

코틀린의 try 키워드는 if와 when 과 마찬가지로 식이다. 따라서 try 값을 변수에 대입할 수 있다.
if와 달리 try의 본문을 반ㄷ싀 중괄호로 감싸야 한다.

- *try* 코드 블록의 실행이 정상적으로 끝나면 그 블록의 마지막 식의 값이 결과다.
