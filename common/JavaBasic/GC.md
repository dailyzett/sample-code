## Garbage Collection

---

> 자동 메모리 관리를 수행하는 프로세스

![Garbage Collection](https://1.bp.blogspot.com/-ZiJiWwHc54A/VvPOdZJoV7I/AAAAAAAAFQA/GvxtW0r2SuQgvCQAKLYj7X_8f2HpdwAtg/w610-h330/Garbage%2BCollection%2Band%2BJVM%2Bparameters.jpg)

- Yong Generation

> 젊은 객체들이 존재하는 영역.

Young Generation은 eden과 두 개의 survivor 영역으로 나뉘는데 객체를 생성하자마자 저장되는 장소는 eden이다.
Perm 영역에는 클래스나 메소드에 대한 정보가 쌓인다.

eden에서 객체가 저장되면 다음 과정을 밟는다.

- eden 영역에서 객체가 생성된다.
- eden 영역이 가득차면 살아있는 객체만 survivor 영역으로 복사되고, 다시 eden 영역을 채운다.
- survivor 영역이 꽉 차면 다른 survivor 영역으로 객체가 복사된다. 이 때 eden 영역에 있는 객체들도 다른 survivor 영역으로 간다.
- 즉 survivor 영역의 둘 중 하나는 무조건 비어 있어야 한다.

오래 살아있는 객체들은 Old Generation으로 이동하게 되고 여기도 꽉 차게 되면 GC가 발생한다.<br>
이것을 major GC, Full GC 라고 부른다.

속도 측면에서 Young Generation 이 Old Generation 보다 당연히 더 빠르다. <br>
일반적으로 더 작은 공간이 할당되고, 객체들을 처리하는 방식도 다르기 때문.

하지만 모든 힙 영역을 Young Generation 으로 만들면 장애가 발생할 확률이 매우 높아진다.
