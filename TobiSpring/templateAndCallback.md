# 1. 템플릿과 콜백

전략 패턴의 기본 구조에 익명 내부 클래스를 활용한 방식을 스프링에서는 **템플릿/콜백 패턴**이라고 부른다.

- 전략 패턴의 **컨텍스트** : 템플릿/콜백 패턴의 **템플릿**
- 익명 내부 클래스로 만들어지는 오브젝트 : **콜백**

```text
템플릿 : 템플릿은 어떤 목적을 위해 미리 만들어둔 모양이 있는 틀을 가리킨다.

콜백 : 실행되는 것을 목적으로 다른 오브젝트의 메서드에 전달되는 오브젝트를 말한다. 파라미터로 전달된다.
자바에서는 메서드 자체를 파라미터로 전달할 방법은 없기 때문에 메서드가 담긴 오브젝트를 리턴해야한다.
그래서 functional object 라고도 한다.
```

### 템플릿/콜백의 특징

여러 개의 메서드를 가진 일반적인 인터페이스를 사용할 수 있는 전략 패턴과 달리 템플릿/콜백 패턴은 보통 단일 메서드 인터페이스를 사용한다.
콜백은 일반적으로 하나의 메서드를 가진 인터페이스를 구현한 익명 내부 클래스로 만들어진다.

클라이언트가 템플릿 메서드를 호출하면서 콜백 오브젝트를 전달하는 것은 메서드 레벨에서 일어나는 DI 이다.
템플릿이 사용할 콜백 인터페이스를 구현한 오브젝트를 메서드를 통해 주입해주는 DI 작업이 클라이언트가 템플릿의 기능을 호출하는 것과 동시에 일어난다.
일반적인 DI라면 템플릿에 인스턴스 변수를 만들어두고 사용할 의존 오브젝트를 수정자 메서드로 받아서 사용할 것이다.

반면에 템플릿/콜백 방식에서는 매번 메서드 단위로 사용할 오브젝트를 새롭게 전달받는 것이 특징이다.
템플릿/콜백 방식은 전략 패턴과 DI의 장점을 익명 내부 클래스 사용 전략과 결합한 독특한 활용법이라고 이해할 수 있다.


### 템플릿/콜백 설계

#### 1. 예제

- 파일을 읽어 그 파일 안에 있는 숫자를 읽어오는 Calculator 클래스 생성
- 이 클래스를 테스트하는 CalcSumTest 클래스 생성

```java
public class Calculator {
    public Integer fileSum(String filepath) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
```

```java
public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        File file = new File("");
        int sum = calculator.fileSum("src/test/resources/numbers.txt");
        Assertions.assertEquals(sum, 10);
    }
}

```

#### 2. 중복 제거와 템플릿/콜백 설계

나중에 Calculator 메서드를 확장할 때 fileSum 메서드의 try/catch/finally 부분은 중복될 것이 뻔하다.
따라서 중복되는 부분을 템플릿으로 만든다.

현 코드에서 템플릿이 하는 역할

- 템플릿이 파일을 연다.
- 각 라인을 읽어올 수 있는 BufferedReader를 만들어서 콜백에게 전달한다.
- 콜백이 각 라인을 읽어서 결괏값만 템플릿에게 돌려준다.

```java
public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
```

```java
public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(filepath));
        int ret = callback.doSomethingWithReader(br);
        return ret;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        throw e;
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
```

이제 합계를 계산하는 메서드를 익명 메서드를 이용해 다음과 같이 작성한다.

```java
public Integer calcSum(String filepath) throws IOException {
    BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
        @Override
        public Integer doSomethingWithReader(BufferedReader br) throws IOException {
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            return sum;
        }
    };
    return fileReadTemplate(filepath, sumCallback);
}
```

BufferedRedaerCallback 인터페이스를 익명 메서드로 구현하고 sum 값을 리턴한다.
리턴한 값을 fileReadTemplate 에게 전달한다.

```java
public class CalcSumTest {
    Calculator calculator;
    String numFilepath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        numFilepath = "src/test/resources/numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        Assertions.assertEquals(calculator.calcSum(numFilepath), 10);
    }
}
```

테스트에 사용할 calculator 와 numFilepath 는 테스트 하기 전 똑같은 오브젝트로 설정돼야 하므로, @BeforeEach 를 이용한다.
그리고 sumOfNumbers() 테스트를 통해 내가 예상한 결괏값과 맞는지 체크한다.

```java
public Integer calcMultiply(String filepath) throws IOException {
    BufferedReaderCallback multiplyCallback = new BufferedReaderCallback() {
        @Override
        public Integer doSomethingWithReader(BufferedReader br) throws IOException {
            Integer multiply = 1;
            String line = null;
            while ((line = br.readLine()) != null) {
                multiply *= Integer.valueOf(line);
            }
            return multiply;
        }
    };
    return fileReadTemplate(filepath, multiplyCallback);
}
```

try/catch/finally 중복되는 부분을 템플릿으로 제외시켰기 때문에, Calculator 클래스에서 곱셈 기능을 추가한다고 해도
덧셈을 구현했을 때처럼 구현할 수 있다. 하지만 이 calcSum() 과 calcMultiply() 메서드도 자세히 보면 중복되는 부분이 있다.

```text
calcXXX()
Integer XXX = 0 or 1;
String line = null;
while((line = br.readLine()) != null){
    XXX += or *= Integer.valueof(line)
}
return XXX;
```

line 과 값을 리턴하는 value 의 차이만 있으므로 다시 이부분을 인터페이스로 선언한다.

```java
public interface LineCallback {
    Integer doSomethingWithLine(String Line, Integer Value);
}
```

```java
public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(filepath));
        Integer res = initVal;
        String line = null;
        while ((line = br.readLine()) != null) {
            res = callback.doSomethingWithLine(line, res);
        }
        return res;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        throw e;
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
```

그리고 템플릿에 해당하는 코드를 변경한다.
기존에 try/catch/finally 만 템플릿으로 작성했다면, 위 코드는 초기셋팅값, 라인 불러오기를 담당하고 있다.
**계산을 담당하는 부분**만 콜백 메서드를 호출한다.

```java
public Integer calcSum(String filepath) throws IOException {
    LineCallback sumCallback = new LineCallback() {
        @Override
        public Integer doSomethingWithLine(String line, Integer value) {
            return value + Integer.valueOf(line);
        }
    };
    return lineReadTemplate(filepath, sumCallback, 0);
}

public Integer calcMultiply(String filepath) throws IOException {
    LineCallback multiCallback = new LineCallback() {
        @Override
        public Integer doSomethingWithLine(String line, Integer value) {
            return value * Integer.valueOf(line);
        }
    };
    return lineReadTemplate(filepath, multiCallback, 1);
}
```

코드의 중복되는 부분이 눈에 띄게 줄었다. Java 8의 람다를 적용하면 더욱 간단해진다.

```java
public Integer calcSum(String filepath) throws IOException {
    LineCallback sumCallback = (line, value) -> value + Integer.valueOf(line);
    return lineReadTemplate(filepath, sumCallback, 0);
}

public Integer calcMultiply(String filepath) throws IOException {
    LineCallback multiCallback = (line, value) -> value * Integer.valueOf(line);
    return lineReadTemplate(filepath, multiCallback, 1);
}
```

#### 3. 제네릭을 이용한 콜백 인터페이스

만약에 파일에 존재하는 문자를 더해주고 싶을 때 Integer 타입만으로는 한계가 있다.
아래 코드는 좀 더 범용성을 지니게 하기 위해 Java 5 에서 소개된 제네릭을 사용한 템플릿/콜백 패턴이다.

```java
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
```

```java
public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
    BufferedReader br = null;
    try {
        br = new BufferedReader(new FileReader(filepath));
        T res = initVal;
        String line = null;
        while ((line = br.readLine()) != null) {
            res = callback.doSomethingWithLine(line, res);
        }
        return res;
    } catch (Exception e) {
        System.out.println(e.getMessage());
        throw e;
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
```

이제 문자열 연결 기능을 제공하는 메서드와 테스트 코드를 작성한다.

```java
public String concatenate(String filepath) throws IOException {
    LineCallback<String> concatenateCallback = ((line, value) -> value + line);
    return lineReadTemplate(filepath, concatenateCallback, "");
}
```

```java
@Test
public void 문자열연결() throws IOException {
    assertEquals(calculator.concatenate(numFilepath), "1234");
}
```

![img_3.png](img_3.png)

최종적으로, 템플릿/콜백과 제네릭을 이용해 범용성과 재사용성, 가독성을 모두 잡았다.