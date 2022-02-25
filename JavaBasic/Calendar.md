## Java 8 Calendar

#### Java 8 에서 신규 Calendar가 생기게 된 배경
- 기존 날짜 클래스로 Date 혹은 SimpleDateFormatter라는 클래스를 사용
- 하지만 Thread-safe 하지 않고, immutable 객체도 아니기 였기 때문에 지속적으로 값 변경이 가능했음.
- 그리고 API 구성도 복잡해 년은 1900년으로 시작하도록 되어있고 달은 1부터, 일은 0부터 시작.



LocalDate 객체를 추가하고, Java 8 에서
추가된 Period 객체의 between method를 이용해 쉽게 날짜 간의 차이를 계산할 수 있다.

아래 코드에서 Period 객체를 그냥 출력하면 P1Y11M29D 가 나온다.<br>
앞의 P는 이것이 Period 객체라는 것을 알려주는 구분자이고 1Y 11M 29D 가 연산에서 나온 날짜를 뜻한다.

Period 는 CharSequence 를 상속받는 구현 클래스(String, StringBuilder 등등..) 를 매개변수로 받아
Period 객체로 변환해주는 parse 메소드도 지원한다.
```java
import java.time.LocalDate;
import java.time.Period;

public class Demo{
    public static void main(String[] args) {
        LocalDate a = LocalDate.of(2021, 02, 03);
        LocalDate b = LocalDate.of(2022, 02, 01);

        Period temp = Period.between(a, b);
        
        System.out.println(temp);
        String date = String.format("%d Years, %d Months, %d Days",
                temp.getYears(), temp.getMonths(), temp.getDays());
        System.out.println(date);
    }
}

//출력값:
//P1Y11M29D
//1 Years, 11 Months, 29 Days
```


국가의 위치마다 시간은 모두 다르다. Java 8 은 국가마다 다른 시간을 구별하기 위해 다음과 같은 패키지를 지원한다.

- java.time.ZoneId
- java.time.ZoneOffset

만약 사용자가 한국인 경우, 순서대로 "Asia/Seoul, +09:00" 같은 정보를 가진다.<br>
참고로, Offset 은 그리니치 시간대(UTC) 와의 차이를 가지는 시간을 의미.


Java 8 은 요일을 출력할 때 DayOfWeek 클래스를 사용한다.

getDisplayName 메소드를 사용하며 매개변수로 TextStyle 과 Locale(지역) 변수를 받는다.
참고로 한국은 TextStyle.NARROW 와 TextStyle.SHORT 모드의 차이가 없다.

영어일 때는 NARROW 는 월요일일 때 Mon, SHORT 는 M 으로 출력된다.

```java
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

public class Demo{
    public static void main(String[] args) {
        DayOfWeek[] values = DayOfWeek.values();
        Locale aDefault = Locale.getDefault();

        for (DayOfWeek day : values) {
            System.out.println(day.getDisplayName(TextStyle.SHORT, aDefault));
        }
    }
}

//출력값
//월화수목금토일
```
