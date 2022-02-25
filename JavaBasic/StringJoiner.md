## StringJoiner

> java.util 에 포함되어 있으며, 순차적으로 나열되는 문자열을 처리하는데 사용


기존의 String 객체를 이어서 만들려면 다음과 같이 코드를 작성했다.
```java
public class Demo{
    public static void main(String[] args) {
        String[] old = new String[]{"apple", "banana", "melon"};

        StringBuilder sb = new StringBuilder();
        for (String x : old) {
            sb.append(x + ",");
        }
        System.out.println(sb.toString());
    }
}
```

마지막에 ,를 빼줘야하는 코드도 따로 작성해야하기 때문에 여러모로 귀찮다.

이런 단점을 보완하기 위해 Java 8에서 StringJoiner가 만들어졌다.
배열의 구성요소에 "," 만 넣고자 한다면 다음과 같이 StringJoiner 생성자에 구분자를 넣어주고 add 메서드를 사용하면 된다.

```java
public class Demo{
    public static void main(String[] args) {
        String[] old = new String[]{"apple", "banana", "melon"};

        StringJoiner join = new StringJoiner(",");
        for (String x : old) {
            join.add(x);
        }
        System.out.println(join);
    }
}

//출력값
//apple,banana,melon
```

StringJoiner 는 구분자 뿐만아니라 prefix와 suffix도 변경 가능하다.
사용법은 아래와 같다.

![img](https://media.vlpt.us/images/dailyzett/post/e1aa4cfb-950e-4f62-bbf2-3be395a022ae/image.png)


```java
public class Demo{
    public static void main(String[] args) {
        String[] old = new String[]{"apple", "banana", "melon"};

        StringJoiner join = new StringJoiner(",", "(", ")");
        for (String x : old) {
            join.add(x);
        }
        System.out.println(join);
    }
}

//출력값:
//(apple,banana,melon)
```


추가로 Java 8의 스트림과 람다 표현식을 이용해서 다음과 같이 코드를 작성할 수도 있다.

```java
public class Demo{
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        String commaSeparatedNumbers = numbers.stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(", "));

        System.out.println(commaSeparatedNumbers);
    }
}

//출력값:
//1, 2, 3, 4
```