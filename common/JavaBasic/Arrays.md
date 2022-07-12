# 목차

# 9.2 Arrays

- 배열을 다루기 편하도록 스태틱 메서드를 제공해준다.

## 9.2.1 배열의 출력

```java
int [] testArray = IntStream.range(0, 5).toArray();

Arrays.toString(testArray);
//arrays = [0, 1, 2, 3, 4]
```

## 9.2.1 배열의 복사

- copyOf()
- copyOfRange()

```java
int[] arrays = IntStream.range(0, 5).toArray();
int[] copyArray = Arrays.copyOf(arrays, 5);
```

## 9.2.3 배열 채우기

- fill()
- setAll()

```java
int[] arr = new int[5];
Arrays.setAll(arr, i -> i);
// arr 배열 전부를 인덱스 번호와 일치한 값으로 설정한다.
```

```java
Arrays.fill(arr, 9); // arr = [9,9,9,9,9]
```

## 9.2.4 배열의 정렬과 검색

- sort()
- binarySearch()

이진 탐색은 정렬되어 있을 때만 정상적으로 작동한다.

```java
int [] arr = {0, 3, 2, 1, 4}

Arrays.sort(arr);
int index = Arrays.binarySearch(arr, 2); // index = 2
```

## 9.2.5 배열을 List로 변환

- asList(Object.. a)

```java
List list = Arrays.asList(new Integer[]{1, 2, 3, 4});
list.add(6);
```