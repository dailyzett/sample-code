## 시간 복잡도와 공간 복잡도


#### 시간 복잡도
> 알고리즘의 함수가 실행되는 데 걸리는 시간을 측정한다.
> <br> 시간 복잡도는 Big-O 표기법으로 표시된다.

다음은 Big O 연산에 따른 그래프를 나타낸다.

![img1](https://media.vlpt.us/images/dailyzett/post/33ec555a-35c7-4d2e-8020-896561490a20/image.png)


Input Length 에 따른 최악의 시간 복잡도는 다음과 같다.


![img2](https://media.vlpt.us/images/dailyzett/post/01ca7283-fa0f-475a-b84e-bd9972d2aebb/image.png)


----


#### 공간 복잡도

> 알고리즘이 차지하는 공간의 양을 수량화한 것, 똑같이 빅오 표기법으로 표기가능하다.

일반적으로 알고리즘의 경우 공간 복잡도와 시간 복잡도는 서로 반대된다.
<br>따라서 시간 효율성이 높을수록 공간 효율성은 낮아지고, 그 반대도 마찬가지다.

일례로 Bubble Sort 는 많은 사람들이 알듯이 시간복잡도는 최악이나 공간 효율성은 최상이다.<br>
Merge Sort 의 경우 정렬 속도는 매우 빠르나 공간 효율성은 최악이다.