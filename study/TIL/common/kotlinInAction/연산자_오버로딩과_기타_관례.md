# 목차

- [목차](#목차)
- [1. 산술 연산자 오버로딩](#1-산술-연산자-오버로딩)
  - [1.1 이항 산술 연산 오버로딩](#11-이항-산술-연산-오버로딩)
  - [1.2 복합 대입 연산자 오버로딩](#12-복합-대입-연산자-오버로딩)
  - [1.3 단항 연산자 오버로딩](#13-단항-연산자-오버로딩)
- [2. 비교 연산자 오버로딩](#2-비교-연산자-오버로딩)
  - [2.1 동등성 연산자: equals](#21-동등성-연산자-equals)
  - [2.2 순서 연산자: compareTo](#22-순서-연산자-compareto)
- [3. 컬렉션과 범위에 대해 쓸 수 있는 관례](#3-컬렉션과-범위에-대해-쓸-수-있는-관례)
  - [3.1 구조 분해 선언과 component 함수](#31-구조-분해-선언과-component-함수)
  - [3.2 구조 분해 선언과 루프](#32-구조-분해-선언과-루프)
- [4. 프로퍼티 접근자 로직 재활용: 위임 프로퍼티](#4-프로퍼티-접근자-로직-재활용-위임-프로퍼티)
  - [4.1 위임 프로퍼티 소개](#41-위임-프로퍼티-소개)
  - [4.2 프로퍼티 값을 맵에 저장](#42-프로퍼티-값을-맵에-저장)

# 1. 산술 연산자 오버로딩

## 1.1 이항 산술 연산 오버로딩

```kotlin
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point{
        return Point(x + other.x, y + other.y)
    }
}
```

```kotlin
class MyTest2 {
    @Test
    fun test() {
        val p1 = Point(10, 20)
        val p2 = Point(30, 40)
        println(p1 + p2)
    }
}
```

plus 함수에 operator 키워드를 붙여야 한다. 연산자를 오버로딩하는 함수 앞에는 반드시 operator가 있어야 한다.

코틀린이 자동으로 연산자 교환 법칙을 지원하지는 않음에 유의하라. (a op b == b op a)

> **참고.**  
> 코틀린은 표준 숫자 타입에 대해 비트 연산자를 지원하지 않는다. 따라서 커스텀 타입에서 비트 연산자를 정의할 수 없다.
> 대신에 중위 연산자 표기법을 지원하는 일반 함수를 사용해 비트 연산을 수행한다.

## 1.2 복합 대입 연산자 오버로딩

`+=`, `-=` 같은 연산자를 복합 대입 연산자라고 부른다.

코틀린 표준 라이브러리는 컬렉션에 대해 두 가지 접근 방법을 제공한다.

- `+` 와 `-` 는 항상 새로운 컬렉션을 반환하며, `+=`, `-=` 연산자는 항상 변경 가능한 컬렉션에 작용해 메모리에 있는 객체 상태를 변화시킨다.
- 읽기 전용 클래스에서 `+=`와 `-=`는 변경을 적용한 복사본을 반환한다.

## 1.3 단항 연산자 오버로딩

```kotlin
operator fun Point.unaryMinus(): Point {
    return Point(-x, -y)
}

operator fun BigDecimal.inc() = this + BigDecimal.ONE
```

# 2. 비교 연산자 오버로딩

코틀린에서는 모든 객체에 대해 `==`로 비교 연산을 수행할 수 있다. 그래서 자바에 비해 코드가 간략해진다.

## 2.1 동등성 연산자: equals

`==`와 `!=`는 내부에서 인자가 널인지 검사하므로 널이 될 수 있는 값에도 적용할 수 있다.
널이 아닌 경우에만 a.equals(b)를 호출하는 형식이다.

## 2.2 순서 연산자: compareTo

자바는 compareTo를 객체 크기를 비교할 때 줄일 수 있는 방법이 없다.

코틀린은 똑같은 Comparable 인터페이스를 제공한다. 게다가 코틀린은 compareTo 메서드를 호출하는 관례를 제공한다.

따라서 객체 간의 비교도 (<, >, <=, >=)로 컴파일된다.

```kotlin
class Person2(val firstName: String, val lastName: String) : Comparable<Person2> {
    override fun compareTo(other: Person2): Int {
        return compareValuesBy(this, other, Person2::lastName, Person2::firstName)
    }
}
```

Comparable의 compareTo에도 operator 연산자가 붙어있으므로 하위 클래스의 오버라이딩 함수에 operator를 붙일 필요 없다.

코틀린의 표준 라이브러리인 compareValuesBy는 첫 번째 비교 함수에 두 객체를 넘겨서 두 객체가 같지 않다는 값이 나오면
두 번째 비교 함수를 통해 두 객체를 비교한다.

필드를 직접 비교하면 코드는 조금 더 복잡해지지만 비교 속도는 훨씬 더 빨라짐을 기억하자.

# 3. 컬렉션과 범위에 대해 쓸 수 있는 관례

## 3.1 구조 분해 선언과 component 함수

구조 분해 선언은 여러 값을 반환할 때 유용하다.

```kotlin
data class NameComponents(val name: String, val extension: String)

fun splitFileName(fullName: String): NameComponents {
    val result = fullName.split('.', limit = 2)
    return NameComponents(result[0], result[1]) // 파일 이름, 확장자명 반환
}
```

```kotlin
@Test
fun test() {
    val (name, extension) = splitFileName("example.kt") // 한꺼번에 반환받을 수 있다.
    println(name)
    println(extension)
}
```

배열이나 컬렉션에도 구조 분해 선언이 있기 때문에 이 코드를 더 최적화할 수 있다.

특히 크기가 정해진 컬렉션을 다루는 경우 더 유용하다. 예를 들어 split은 두 개의 원소로 이뤄진 리스트를 반환한다.

```kotlin
fun splitFileName(fullName: String): NameComponents {
    //변수 선언부터 구조 분해 선언으로 값을 할당한다.
    val (name, extension) = fullName.split('.', limit = 2)
    return NameComponents(name, extension)
}
```

## 3.2 구조 분해 선언과 루프

변수가 들어갈 수 있는 곳 어디든 구조 분해 선언을 사용할 수 있다.

Map의 원소에 대해 이터레이션할 때 구조 분해 선언이 유용하다.

```kotlin
fun printEntries(map: Map<String, String>) {
    for ((key, value) in map) {
        println("$key -> $value")
    }
}
```

이 함수에 사용된 관례:

- 객체를 이터레이션하는 관례, Map 원소에 대한 이터레이터를 반환
- 구조 분해 선언

# 4. 프로퍼티 접근자 로직 재활용: 위임 프로퍼티

delegated properties(위임 프로퍼티)를 이용하면 값을 뒷받침하는 필드에 단순히 저장하는 것보다 더 복잡한 방식으로 작동하는
프로퍼티를 쉽게 구현할 수 있다.

**위임**은 객체가 직접 작업을 수행하지 않고 다른 도우미 객체가 그 작업을 처리하게 맡기는 디자인 패턴을 말한다.

## 4.1 위임 프로퍼티 소개

```kotlin
class Foo {
    var p: Type by Delegate() //by 키워드는 프로퍼티와 위임 객체를 연결한다.
}
```

- p 프로퍼티는 접근자 로직을 다른 객체에게 위임한다. 
- 여기서는 Delegate 클래스의 인스턴스를 위임 객체로 사용한다.
- *by* 뒤에 있는 식을 계산해서 위임에 쓰일 객체를 얻는다.

```kotlin
class Foo {
    private val delegate = Delegate() //컴파일러가 생성한 도우미 프로퍼티
    var p: Type //p 프로퍼티를 위해 컴파일러가 생성한 접근자는 getValue와 setValue 메서드를 호출
    set(value: Type) = delegate.setValue(TODO(), value)
    get() = delegate.getValue(TODO())
}
```

```kotlin
@Test
fun test() {
    val foo = Foo()
    val oldValue = foo.p
    foo.p = newValue
}
```

p의 게터나 세터는 Delegate 타입의 위임 프로퍼티 객체에 있는 메서드를 호출한다.

코틀린 라이브러리는 프로퍼티 위임을 사용해 프로퍼티 초기화를 지연시켜줄 수 있다.

## 4.2 프로퍼티 값을 맵에 저장

자신의 프로퍼티를 동적으로 정의할 수 있는 객체를 만들 때 위임 프로퍼티를 활용하는 경우가 자주 있다.
이런 객체를 *expando object*라고 부르기도 한다.


> Ex) 연락처 관리 시스템에서 연락처별로 임의의 정보를 저장할 수 있게 허용하는 경우를 살펴본다.

- 시스템에 저장된 연락처에는 일부 필수 정보가 있다.
- 여기에는 사람마다 달라질 수 있는 추가 정보도 있다.

이를 구현하기 위해서 정보를 모두 맵에 저장하되, 맵을 통해 처리하는 프로퍼티를 이용해 필수 정보를 제공하는 방법이 있다.

- 추가 데이터를 저장하기 위해 일반적인 API를 사용한다.
- 특정 프로퍼티(name)을 처리하기 위해 구체적인 개별 API를 사용한다.

```kotlin
class Person {
    //추가 정보
    private val _attributes = hashMapOf<String, String>()

    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }

    //필수 정보
    val name: String
    get() = _attributes["name"]!!
}
```

```kotlin
class DelegatedPropertyTest {
    
    @Test
    fun test() {
        val p = Person()
        val data = mapOf("name" to "Dmitry", "company" to "JetBrains")
        for((attrName, value) in data)
            p.setAttribute(attrName, value)
        println(p.name)
    }
}
```

이 코드를 위임 프로퍼티의 *by* 키워드로 바꾸면 아래와 같다.

```kotlin
//필수 정보
val name: String by _attributes
```

이 코드가 작동하는 이유는 표준 라이브러리가 *Map*과 *MutableMap* 인터페이스에 대해 *getValue*와 *setValue* 확장
함수를 제공하기 때문이다. *getValue*에서 맵에 프로퍼티 값을 저장할 때는 자동으로 프로퍼티 이름을 키로 활용한다.

`p.name`은 `_attributes.getValue(p, prop)` 라는 호출을 대신하고,
`_attributes.getValue(p, prop)`는 다시 `_attributes[prop.name]`을 통해 구현된다.