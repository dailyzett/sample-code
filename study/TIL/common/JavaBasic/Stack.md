# 목차

- [목차](#목차)
- [9.2 스택과 큐](#92-스택과-큐)
  - [9.2.1 스택 개요](#921-스택-개요)
    - [스택의 활용 예](#스택의-활용-예)
  - [9.2.2 큐 개요](#922-큐-개요)
    - [큐의 활용 예](#큐의-활용-예)

# 9.2 스택과 큐

## 9.2.1 스택 개요

- 스택은 후입선출(LIFO) 방식의 컬렉션이다.
- 후입선출 방식이므로 내부 구현 시 배열 방식이 적합하다.

|메서드|설명|
|:------|:------|
|boolean empty()|스택이 비어있는지 알려준다.|
|Object peek()|스택의 맨 위 객체 반환. pop() 과 달리 객체를 꺼내지는 않음|
|Object pop()|스택의 맨 위의 객체를 꺼낸다. 비어있으면 _EmptyStackException_ 발생|
|Object push(Object item)|스택에 객체를 넣는다.|
|int search(Object o)|스택에서 주어진 객체를 찾아서 그 위치를 반환. 못 찾으면 -1. 배열과 달리 시작 위치는 0이 아닌 1|

- 스택은 `Vector`를 확장한 클래스이기 때문에 아래와 같이 선언 가능하다.

```java
Stack<?> stack = new Stack();
```

### 스택의 활용 예

```text
수식계산, 수식괄호검사, 워드프로세서 undo/redo, 웹브라우저 뒤로/앞으로
```

## 9.2.2 큐 개요

- 큐는 선입선출(FIFO) 방식의 컬렉션이다.
- 선입선출 방식을 배열로 구현하면 제일 앞의 요소를 삭제 후 나머지 요소들의 인덱스를 앞으로 당겨야 한다.
- 이 때 배열의 복제가 일어나므로 효율적이지 못하다.
- 따라서 큐는 내부 구현 시 LinkedList 방식이 적절하다.

|메서드|설명|
|:------|:------|
|boolean add(Object o)|지정된 객체를 Queue 에 추가. 성공하면 true 반환. 저장 공간 부족 시 _IllegalStateException_ 발생|
|Object remove()|Queue에서 객체를 꺼내서 반환. 비어 있으면 _NoSuchElementException_ 발생|
|Object element()|삭제없이 요소를 가져온다. 비어 있으면 _NoSuchElementException_ 발생|
|boolean offer(Object o)| add와 같으나 예외를 던지지 않음. 실패하면 false 반환|
|Object poll()| Queue에서 객체를 꺼내서 반환. 비어있으면 null 반환|
|Object peek()| 삭제없이 요소를 읽어온다. 비어있으면 null 반환|

- _Queue_ 는 인터페이스이다. 따라서 생성하려면 _Queue_ 인터페이스를 구현한 클래스를 사용해야 한다.

```text
All Known Implementing Classes:
AbstractQueue, ArrayBlockingQueue, ArrayDeque, ConcurrentLinkedDeque, ConcurrentLinkedQueue, DelayQueue, LinkedBlockingDeque, LinkedBlockingQueue, LinkedList, LinkedTransferQueue, PriorityBlockingQueue, PriorityQueue, SynchronousQueue
```

```java
Queue<?> queue = new LinkedList<>();
```

### 큐의 활용 예

```text
최근 사용 문서, 인쇄 작업 대기 목록, 버퍼(buffer)
```
