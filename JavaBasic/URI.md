## URI

URI 는 Uniform Resource Identifier 의 약자이다.

- Uniform : 리소스 식별하는 통일된 방식
- Resource : 자원, URI 로 식별할 수 있는 모든 것
- Identifier : 다른 항목과 구분하는데 필요한 정보



URI 공식 문서에 따르면 URI 는 locator, name 또는 둘 다 추가로 분류될 수 있다라고 나온다.

- URL(locator) 는 리소스가 있는 위치를 특정한다.
- URN(name) 은 리소스에 이름을 부여한다

하지만 URN 만으로 실제 리소스를 찾을 수 있는 방법은 보편화 되지 않았으므로
URI와 URL만 자세하게 알고 있으면 된다.


### URL

다음은 구글 검색 시 나오는 URL 이다.

https://www.google.co.kr/search?q=%EA%B2%80%EC%83%89

차례대로 구조를 나누면 다음과 같다.

- https (프로토콜)
- www.google.co.kr(호스트명)
- /search(패스)
- q=%EA%B2%80%EC%83%89(쿼리 파라미터)


원래 포트 번호가 있어야 하지만 https 의 경우 기본적으로 443 이기 때문에 생략이 가능하다. <br>
http의 경우는 80이다.

http, https 같은 프로토콜은 어떤 방식으로 자원에 접근 할 것인가 하는 규칙이다.



