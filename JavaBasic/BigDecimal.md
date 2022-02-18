## BigDecimal

---

### Structure

java.lang.Object<br>
&nbsp;&nbsp;java.lang.Number<br>
&nbsp;&nbsp;&nbsp;&nbsp;java.math.BigDecimal

### All implemented interfaces

Serializable, Comparable<BigDecimal>

----

### 설명

>임의의 정밀도를 가진 부호 있는(signed) 십진수이고 불변 객체이다. <br>

BigDecimal 은 unscaled value 와 32비트의 integer scale 로 구성.
- unscaled value는 소수점을 빼고 단순 숫자만 추출(ex. 값이 3.14일 때 unscaled value는 314)
- scaled value는 소숫점 오른쪽의 자릿수

```java
public static void main(String[] args) {
    BigDecimal positive = new BigDecimal("314.31");
    System.out.println(positive.unscaledValue());
    System.out.println(positive.scale());

    System.out.println();

    BigDecimal negative = new BigDecimal("-314.31");
    System.out.println(negative.unscaledValue());
    System.out.println(negative.scale());
}
```

출력값 :
```java
31431 // unscaled value
2 // scale

-31431 // unscaled value
2 // scale
```

BigDecimal 은 반올림을 완벽하게 제어한다. 반올림 모드가 지정되지 않고 정확한 결과를 나타낼 수 없을 때
예외가 발생한다.

Java는 반올림을 제어하는 방법으로 총 8가지를 제공하는데, 이 클래스의 integer field들을 사용하여 반올림 모드를
나타내는 것은 쓸모없다고 하고 있다.(ex) ROUND_HALF_UP

실제로 사용해보면 deprecated 가 나오므로 RoundingMode enum 값들 중 하나를 쓰는 것이 좋다. (ex) RoundingMode.HALF_UP

![img](https://media.vlpt.us/images/dailyzett/post/b59bd525-e056-4cf1-917c-68c9bc1c422a/image.png)



```java
BigDecimal positive = new BigDecimal("314.36");

//setScale(int newScale, RoundingMode)
positive = positive.setScale(1, RoundingMode.HALF_UP);
System.out.println(positive);
```

출력값 :
```java
314.4 // newScale 값이 기존 scale 값보다 높을 경우, 그만큼 소숫점 오른쪽에 0 추가
```


Java 공식 문서에서는 간결성을 위해서 BigDecimal 메소드에 대한 설명 전체에 [슈도 코드](https://ko.wikipedia.org/wiki/%EC%9D%98%EC%82%AC%EC%BD%94%EB%93%9C)
를 표기한다.

- 슈도코드 (i+j) 는 BigDecimal의 i + BigDecimal의 j
- (i==j) BigDecimal i == BigDecimal j
- 대괄호는 특정 BigInteger 및 scale을 나타냄
    - [19, 2]는 0.19와 동일한 BigDecimal 이며 scale 은 2이다.


이 클래스의 모든 메소드는 null 객체를 전달하면 NullPointerException을 throw 한다.

---

### 주의사항

아무리 BigDecimal 이라도 값을 정확하게 계산할 수 없으면 exception 을 던진다
따라서 값이 나누어떨어지지 않는 경우 계산결과를 적당한 scale로 조정해야한다.

```java
BigDecimal a = new BigDecimal("1");
BigDecimal b = new BigDecimal("3");

//devide(BigDecimal divisor, int scale, RoundingMode roundingMode)
BigDecimal c = a.divide(b, 100, RoundingMode.CEILING);

System.out.println(c);

// 출력값 : 0.3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333334
```


---


## BigInteger

---

### Structure

java.lang.Object<br>
&nbsp;&nbsp;java.lang.Number<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java.math.BigInteger<br>

---

BigDecimal 과 마찬가지로 불변 객체이다.

int나 long 처럼 비트연산자를 제공하지만 연산자 대신 메소드를 사용해야한다.
<br> ex) and, or, not, xor, andNot, shiftLeft, shiftRight

참고로
&lt;&lt;&lt; 연산자는 BigDecimal 가 제공하는 [infinite word size abstraction](https://stackoverflow.com/questions/57195887/infinite-word-size-abstraction) 때문에 거의 의미가 없기 때문에 생략된다.



---
지원범위 :
- -2<sup>Integer.MAX_VALUE</sup> < x < 2<sup>Integer.MAX.VALUE</sup>


null 값을 허용하지 않는다.


각 메소드와 생성자는 공식 문서 참조
- [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html)
- [BigInteger](https://docs.oracle.com/javase/8/docs/api/java/math/BigInteger.html)
- [참고할만한 사이트 1](https://www.baeldung.com/java-bigdecimal-biginteger)
- [참고할만한 사이트 2](https://www.geeksforgeeks.org/bigdecimal-class-java/)
