## Parallel array sorting


다음과 같이 기존 java.util.Arrays 패키지를 사용하면 배열 정렬을 손쉽게 할 수 있었다.
아래 코드는 Arrays 의 static 메소드를 이용해 정렬 후 출력한 결과이다.
```java
public class Demo{
    public static void main(String[] args) {
        int[] arr = {3, 5, 1, 4, 9};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
```
Java 8 에서는 parallelSort() 정렬 메소드가 제공되고, Java 7 에서 등장한 [Fork-Join](https://github.com/dailyzett/TIL/blob/main/common/JavaBasic/Java7Add.md) 프레임워크가 사용된다.

기존 sort 의 경우 단일 쓰레드로 수행되지만 parallelSort() 는 필요에 따라 여러 개의 쓰레드로 나뉘어 작업이
수행 된다. 따라서 속도는 parallelSort()가 더 빠르다.

parallelSort() 가 더 빨라지는 임계점은 다음과 같다.

![img](https://sanaulla.files.wordpress.com/2013/04/table_parallelsort2.jpg)
![img2](https://sanaulla.files.wordpress.com/2013/04/graph_parallelsort2.jpg)

표를 보면 element가 4984개부터 유의미한 속도 차이가 벌어진다.
element 개수가 4984 개 이하라면 기존의 Arrays.Sort 를 사용해도 상관없음.