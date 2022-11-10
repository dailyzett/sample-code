# 목차

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

