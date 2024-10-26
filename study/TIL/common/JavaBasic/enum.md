## Enum

### 1. int 상수 대신 enum 을 사용해야하는 이유?

enum 타입은 변수가 미리 정의된 상수 집합이 될 수 있도록 하는 데이터 타입이다. enum 타입은 미리 정의된
값 중 하나와 같아야 한다. 예를 들어 동서남북을 enum 타입으로 정의할 때 값은 NORTH, SOUTH, EAST, WEST 중 하나이다.

### 1.1 int 상수를 사용할 때의 문제점


보통 int 상수를 이용할 때 다음과 같이 코드를 작성한다.
```java
public static final int APPLE_FUJI          = 0;
public static final int APPLE_PIPPIN        = 1;
public static final int APPLE_GRANNY_SMITH  = 2;
public static final int ORANGE_NAVEL        = 0;
public static final int ORANGE_TEMPLE       = 1;
public static final int ORANGE_BLOOD        = 2;
```
int 상수를 이렇게 선언했을 경우 많은 단점이 있다.

- 타입 안전성과 편의성이 없다.
- **apple** 을 원하는 메서드에 **orange** 를 전달해도 컴파일 에러가 나오지 않는다.
- 열거형 상수와 연결된 int 가 변경되면 애플리케이션을 다시 컴파일해야한다.
- int 상수는 출력 가능한 문자열로 변환하기가 쉽지 않다.(값이 숫자로만 되있기 때문에)

### 1.2 해결 방법

Java 는 이런 단점들을 극복하기 위해 enum 타입을 제공한다. int 상수 나열은 enum 타입으로 변경하면 다음과
같이 변경할 수 있다.

```java
public enum Apple {
    FUJI, PIPPIN, GRANNY_SMITH
}

public enum Orange {
    NAVEL, TEMPLE, BLOOD
}
```

enum 타입을 사용함으로써 얻을 수 있는 장점은 다음과 같다.

- 각각의 값들은 int형 상수의 public static final 키워드를 이용해서 선언한 것과 같기 때문에 코드가 간결해진다.
- enum 인스턴스 자체가 싱글톤이다.
- enum 타입은 컴파일 시간 때 타입 안전성을 체크해준다.
- toString() 메서드를 호출해서 enum 타입을 출력 가능한 문자열로 변환 가능하다.
- Object 메소드 구현이 제공된다.
- 비교 가능하고 직렬화가 가능하다.

### 1.2.1 enum 사용 예제

각 행성들을 enum 키워드를 이용해 초기화 한 예제이다. 두 개의 매개변수를 입력받는 생성자를 이용했다.
enum 타입의 모든 필드는 final 키워드가 필수이기 때문에 계산하기 위해 사용되는 코드들은 모두 final로 선언했다.
필드의 접근 제한자는 public도 가능하지만 그것보다는 private으로 설정하고 getter 메서드를 따로 만드는 것이
더 좋은 방법이다.

```java
public class Practice{
    public enum Planet{
        MERCURY(3.302e+23, 2.439e6),
        VENUS(4.869e+24, 6.052e6),
        EARTH(5.975e+24, 6.378e6),
        MARS(6.419e+23, 3.393e6),
        JUPITER(1.899e+27, 7.149e7),
        SATURN(5.685e+26, 6.027e7),
        URANUS(8.683e+25, 2.556e7),
        NEPTUNE(1.024e+26, 2.477e7);

        private final double mass;
        private final double radius;
        private final double surfaceGravity;

        private static final double G = 6.67300E-11;

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
            surfaceGravity = mass / (radius * radius);
        }

        public double getMass() {
            return mass;
        }

        public double getRadius() {
            return radius;
        }

        public double getSurfaceGravity() {
            return surfaceGravity;
        }

        public double surfaceWeight(double mass){
            return mass * surfaceGravity;
        }

        private static void testWeight(){
            double earthWeight = 175.0;
            double mass = earthWeight / Planet.EARTH.getSurfaceGravity();

            System.out.println("지구 무게 = " + earthWeight);
            for (Planet p : Planet.values()) {
                System.out.println(p.toString() + "의 무게는 " + + p.surfaceWeight(mass) + "이다." );
            }
        }

        public static void main(String[] args) {
            testWeight();
        }
    }
}
```

### 2. ordinal() 보다 인스턴스 필드를 사용

ordinal() 메서드는 enum 타입에서 선언된 상수들의 순서를 리턴한다. 하지만 사용은 권장되지 않는데,
그 이유는 다음과 같다.

### 2.1 ordinal() 을 사용해서 발생되는 문제

다음은 ordinal 을 사용해서 순서를 출력하는 코드이다.
```java
public class Practice{
    public  enum  Ensemble1 {
        SOLO , DUET , TRIO , QUARTET , QUINTET ,
        SEXTET , SEPTET , OCTET , NONET , DECTET ;
        public int numMusicians () {
            return ordinal() + 1 ;
        }
    }

    public static void main(String[] args) {
        System.out.println(Ensemble1.NONET.numMusicians());
    }
}
```

이 코드는 유지 보수를 매우 어렵게 만든다. 만약 개발자가 상수 중간에 값을 추가한다면 기존에 예상했던 enum의 순서가
하나씩 밀리기 때문에 코드 결과값을 예상할 수 없게 만들고 이를 고치기 위해 개발자는
일일히 상수 순서를 맞추기 위해 코드를 변경해줘야한다. 유지 보수를 쉽게 하기 위해 위 코드를 다음 코드로 변경할 수 있다.

```java
public enum Ensemble {
    SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
    SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
    NONET(9), DECTET(10), TRIPLE_QUARTET(12);

    private final int numMusicians;
    
    Ensemble(int n) {
        this.numMusicians = n;
    }

    public int numMusicians() {
        return numMusicians;
    }
}
```
enum 상수에 해당되는 값을 ordinal() 메서드를 사용하지 말고, 객체 필드에 따로 저장해야한다. 위 코드를 보면,
Ensemble 생성자를 통해 각 상수마다 값을 할당했고 그 값들은 final 필드인 numMusicians에 할당되어 있다. 상수 사이에
새로운 상수를 집어넣을 때도 코드를 변경할 필요가 없기 때문에 이전 코드보다 유지 보수성이 상당히 좋아진다.


### 3. 서수 인덱싱(ordinal indexing) 대신 EnumMap 을 사용

EnumMap 은 enum 타입 키와 함께 사용하기 위한 특수한 Map 구현체이다. enum 맵의 모든 키는
맵이 생성될 때, 명시적으로 또는 암시적으로 지정된 단일 enum 타입에서 가져와야한다. enum 맵들은
내부적으로 배열로 표현된다.

일반적으로 배열을 인덱싱하기 위해 서수(ordinal)을 사용하는 것은 권장되지 않는다. 그 대신 EnumMap 을 이용하면
된다. 만약 2차원 이상의 배열인 경우 EnumMap<..., EnumMap<...>> 를 사용하면 된다.

아래 코드는 EnumMap을 사용한 예제 코드이다.
```java
import java.util.EnumMap;
import java.util.Map;

public class Practice{
    enum Days{
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    }

    public static void main(String[] args) {
        final EnumMap<Days, String> enumMap = new EnumMap(Days.class);
        enumMap.put(Days.SUNDAY, "It's Sunday!!");
        enumMap.put(Days.FRIDAY, "It's FRIDAY!!");
        enumMap.put(Days.SATURDAY, "It's SATURDAY!!");

        for (final Map.Entry<Days, String> entry : enumMap.entrySet()) {
            System.out.println("key: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }
    }
}
```


### 4. 참고 출처

[Java enum best practices](https://www.javaguides.net/2018/09/java-enums-and-annotations-best.html)


