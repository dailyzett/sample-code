# HTTP 쿠키

> 쿠키는 서버가 사용자의 웹 브라우저에 전송하는 작은 데이터 조각이다. 브라우저는 그 데이터 조각들을
> 저장해 놓았다가, 동일한 서버에 재요청 시 저장된 데이터를 함께 전송한다.

쿠키는 두 개의 요청이 동일한 브라우저에서 들어왔는지 아닌지를 판단할 때 주로 사용한다.
상태가 없는 (stateless) HTTP 에 상태 정보를 기억시켜주기 때문에, 쿠키를 이용해서 사용자의 로그인
상태를 기억할 수 있다.

쿠키는 주로 세 가지 목적을 위해 사용된다.

- 세션 관리 : 서버에 저장해야할 로그인, 장바구니 등의 정보 관리
- 개인화 : 개인 사용자의 세팅
- 트래킹 : 사용자 행동을 분석하고 기록하는 용도

과거엔 클라이언트 측에 정보를 저장할 때 쿠키를 주로 이용했다. 저장 방법이 쿠키가 유일한 방법이였을 때는
어쩔 수 없었지만 지금은 modern storage APIs 를 이용해 정보를 저장하는 것을 권장한다. 쿠키를 사용하면
모든 요청마다 쿠키와 함께 서버에 전송되기 때문에 성능이 떨어지는 원인이 될 수 있기 때문이다.


## 1. 쿠키 생성

HTTP 요청을 수신할 때, 서버는 응답과 함께 Set-Cookie 헤더를 전송할 수 있다. 쿠키는 브라우저에 의해
저장되며, 그 후 쿠키는 같은 서버들에 의해 만들어진 요청들의 **Cookie HTTP 헤더**안에 포함되어 전송된다.

### 1.1 Set-Cookie

HTTP 응답 헤더는 서버로부터 클라이언트로 전송된다. 간단한 쿠키는 다음과 같이 설정될 수 있다.

```http request
Set-Cookie:<cookie-name>=<cookie-value>
```

서버의 헤더는 클라이언트에게 쿠키를 전달하라고 지시한다.

```http request
HTTP/1.0 200 OK
Content-type: text/html
Set-Cookie: yummy_cookie=choco
Set-Cookie: tasty_cookie=strawberry

[page content]
```

### 1.2 쿠키의 라이프타임

쿠키의 라이프타임은 두 가지로 나뉜다.

- **세션 쿠키(session cookie)** : 현재 세션이 끝날 때 삭제된다. 브라우저는 "현재 세션"이 끝나는 시점을
정의하며, 어떤 브라우저는 재시작할 때 세션을 복원해 세션 쿠키가 무기한 존재할 수 있도록 할 수 있다.
- **영속적 쿠키(permanent cookie)** : Expires 속성에 명시된 날짜가 지나서 삭제되거나, Max-Age
속성에 명시된 기간 이후에 삭제된다.

다음은 예시이다.
```http request
Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT;
```

※ 주의해야할 점은 만료날짜가 설정되면 설정된 시간과 날짜는 서버가 아니라 쿠키가 설정되는
클라이언트를 기준으로 한다.


### 1.3 쿠키 저장 위치

웹 브라우저의 종류에 따라 쿠키가 저장되는 위치가 달라진다.

- Google Chrome : In the browser you can have a look at them by typing chrome://settings/cookies in the url box. The physical location of the saved cookies is C:\Users\<yourusername>\AppData\Local\Google\Chrome\User Data\Default\Local Storage but apart from the Internet Cache, others are encoded into SQLite database files.

- Internet Explorer : Cookies are present at C:\Users\<yourusername>\AppData\Roaming\Microsoft\Windows\Cookies

- Firefox :  check this article http://stackoverflow.com/questions/302294/cookies-in-firefox
The file might be encoded into a SQLLite database depending upon the FF version

### 1.4 Session ID

웹 서버는 클라이언트에게 response 헤더 필드의 set-cookie 값으로 클라이언트 식별자인 session id를 발행한다.
서버로부터 발행된 session id는 해당 서버와 클라이언트 메모리에 저장된다.
이 때 클라이언트 메모리에 사용되는 쿠키 타입은 세션 종료 시 같이 소멸되는 **Memory cookie**가 사용된다.




## 2. 쿠키 스코프

Domain 과 Path 디렉티브는 쿠키의 스코프를 정의한다.

- Domain : 쿠키가 전송할 호스트를 명시한다. 만약 명시되지 않으면, 현재 문서 위치의 일부를 기본값으로 한다.
- Path : Cookie 헤더를 전송하기 위하여 반드시 존재해야 하는 URL 경로이다.
만약 **path=/docs** 가 설정되면, 다음의 경로가 모두 매치된다.
  - /docs
  - /docs/web
  - /docs/web/HTTP

다음은 예시이다.

```http request
Set-Cookie: name=Greg; domain=nczonline.net; path=/blog
```

## 3. 쿠키의 종류

- 퍼스트 파티 쿠키

사용자가 방문한 웹사이트에서 직접 발행하는 쿠키 파일을 말한다. 사용자가 google.com에 접속했을 때
해당 사이트에서 로그인 기록에 관한 쿠키를 발행하는 건 퍼스트 파티 쿠키에 해당한다. 서브 도메인이 발행한
쿠키도 퍼스트 쿠키로 간주한다.

- 서드 파티 쿠키

사용자가 방문한 웹 사이트가 아닌, 다른 웹사이트에서 발행한 쿠키 파일을 말한다. 보통 광고 서버에서 발행하는
쿠키가 여기에 해당된다.

## 4. 보안

> 중요한 정보는 HTTP 쿠키 내에 저장되거나 전송되어서는 안 된다.

### 세션 하이재킹과 XSS

쿠키는 보통 웹 애플리케이션에서 사용자와 그들의 인증된 세션을 식별하기 위해 사용된다.
그래서 쿠키를 가로채는 것은 인증된 사용자의 세션 하이재킹으로 이어질 수 있다.
쿠키를 가로채는 방법으로 애플리케이션 내 XSS 취약점을 이용하는 것이 있다.

> **XSS란?**<br/>
> XSS는 공격자가 웹 사이트에 악성 클라이언트 측 코드를 삽입할 수 있도록 하는 공격 기법이다.
> 이 코드를 실행하면 공격자가 액세스 제어를 우회하고 인증된 사용자로 웹 서버에 침투 가능하다.
> <br/><br/>XSS 공격은 웹 애플리케이션이 충분한 유효성 검사 또는 인코딩을 사용하지 않는 경우 발생한다.
> 사용자의 브라우저는 신뢰할 수 없는 악성 스크립트를 감지할 수 없으므로 쿠키, 세션 토큰 또는 기타 민감한 사이트의 특정 정보에 대한
> 액세스 권한을 부여하거나 악성 스크립트가 HTML 문서를 다시 작성하도록 한다.

`HttpOnly` 쿠키 속성은 자바스크립트를 통해 쿠키 값에 접근하는 것을 막아 XSS 공격을 방어하는 데 도움을 준다.

### Cross-site 요청 위조(CSRF)

CSRF 공격은 브라우저 요청이 세션 쿠키를 포함한 모든 쿠키를 자동으로 포함하기 때문에 작동한다.
예를 들어 어떤 사람이 은행 서버에 돈을 입금하는 실제 요청 대신에, 실제 이미지가 아닌 이미지를 보내는 공격 기법이다.

```html
<img src="http://bank.example.com/withdraw?account=bob&amount=1000000&for=mallory">
```

이 때 사용자가 은행 계좌에 로그인 중이고 쿠키가 유효한 상황이라면 해당 이미지를 포함하고 있는 HTML을 로드하자마자 돈이 송금될 것이다.
이런 일들이 벌어지는 것을 막기 위한 몇 가지 기술들이 있다.

- 프레임워크에 CSRF 보호 기능이 내장되어 있는지 확인하고 사용한다.
  - 지원하지 않으면, 모든 상태 변경 요청에 CSRF 토큰을 추가하고 백엔드에서 유효성 검사를 실시한다.
- 상태 저장 소프트웨어인 경우 **synchronizer token pattern** 사용
- 상태 비저장 소프트웨어인 경우 **double submit cookies** 사용
- 상태 변경 작업에 GET 요청을 사용하지 않는다.
  - 만약 GET 요청을 사용하는 경우 CSRF로부터 해당 리소스를 보호해야 한다.
- 세션 쿠키에 대해 SameSite 쿠키 속성을 고려한다.
- 매우 민감한 작업에 대해 **사용자 상호 작용 기반 보호** 구현을 고려한다.
- **사용자 지정 요청 헤더 사용**을 고려 한다.
- **표준 헤더로 출처 확인**을 고려 한다.

#### synchronizer token pattern

CSRF 토큰은 서버 측에서 생성돼야 한다. 사용자 세션 또는 각 요청에 대해 한 번 생성할 수 있다.
각 요청에 대해 한 번 생성하는 방식은 공격자가 도난당한 토큰을 악용할 수 있는 시간 범위가 최소화되기 때문에 세션 토큰보다 안전하다.
그러나 이로 인해 사용성은 떨어질 수 있다. "뒤로" 버튼을 눌렀을 때 이전 페이지로 가면서 서버에서는 CSRF 오탐 보안 이벤트가 발생한다.
반면 세션 토큰은 세션이 만료될 때까지 후속 요청에 지속적으로 사용할 수 있다.

클라이언트가 요청하면 서버는 사용자 세션에서 찾은 토큰과 비교하여 요청에 있는 토큰의 존재 및 유효성을 확인한다.
요청 내에서 토큰을 찾을 수 없거나 제공된 값이 사용자 세션 내 값과 일치하지 않으면 요청을 중단한다.

**CSRF 토큰은 쿠키를 이용해 전송하면 안 된다.**

CSRF 토큰은 숨겨진 필드, 헤더를 통해 추가할 수 있으며 form 태그 및 AJAX 호출과 함께 사용할 수 있다.
이 때 토큰이 서버 로그 또는 URL에서 누출되지 않았는지 확인해야 한다.
GET 요청의 CSRF 토큰은 여러 위치에서 노출될 수 있기 때문에 사용을 자제한다.

#### double submit cookies

서버 측에서 CSRF 토큰의 상태를 유지하는데 문제가 있다면 이중 제출 쿠키 방식을 고려할 수 있다.
이 기술은 구현하기 쉽고 stateless 하다.
이 기술은 쿠키 값과 요청 값이 일치하는지 확인하는 서버와 함께 쿠키와 요청 매개변수(request parameter) 모두에 임의의 값을 보낸다.
사용자가 방문하면 사이트는 암호학적으로 강력한 난수 값을 생성하고 세션 식별자와 별도로 사용자 컴퓨터에 쿠키로 설정한다.
그런 다음 사이트는 모든 트랜잭션 요청에 대해 이 난수 값을 숨겨진 양식으로 포함하도록 요구한다.
둘 다 서버 측에서 일치하는 경우 서버는 요청을 허락하고 일치하지 않는 경우 요청을 중단시킨다.

이 솔루션의 보안을 강화하려면 인증 쿠키가 아닌 암호화된 쿠키에 토큰을 포함시킨 다음 서버 측에서 이를
해독한 후, 숨겨진 토큰과 일치하는지 확인한다. **이것은 하위 도메인이 암호화 키와 같은 필수 정보 없이는
정상적으로 제작된 암호화된 쿠키를 덮어쓸 방법이 없기 때문에 동작한다.**

### CSRF 심층 방어 기술

#### SameSite 쿠키 속성

SameSite는 쿠키를 자사 또는 동일 사이트 컨텍스트로 제한해야 하는지 여부를 설정할 수 있다.

> **사이트란?**<br/>
> 사이트는 도메인 접미사와 바로 앞의 도메인 부분이 결합된 것이다. 예를 들어 `www.web.dev` 도메인은 `web.dev` 사이트의 일부이다.
> <br/><br/>
> 사용자가 `www.web.dev`에 있고 `static.web.dev`에서 이미지를 요청하는 경우, 이것은 same-site 요청이다.
> <br/><br/>
> 사용자가 `your-project.github.io`에 있고 `my-project.github.io`에서 이미지를 요청하는 경우 이것은 **사이트 간** 요청이다.


이 속성은 브라우저가 사이트 간 요청과 함께 쿠키를 보낼지 여부를 결정하는 데 도움을 준다.
SameSite 속성에 가능한 값은 세 가지다.

- Lax
- Strict
- None

`Strict`는 일반 링크를 따라가는 경우에도 브라우저에서 대상 사이트로 쿠키를 보내지 못하도록 한다.
예를 들어, GitHub와 유사한 웹 사이트에서 로그인 한 사용자가 기업 토론 포럼이나 이메일에 게시된 비공개 GitHub 프로젝트 링크를
팔로우하면 Github에서 세션 쿠키를 수신하지 않고 사용자가 프로젝트에 액세스한다. 은행 웹사이트는 거래 페이지가 외부 사이트에서
연결되는 것을 허용하지 않기 때문에 `Strict` 플래그가 가장 적합하다.

`Lax` 속성은 사용자가 외부 링크로 간 후, 사용자의 로그인 세션을 유지하려는 웹 사이트에 대한 보안과 사용성 간의 적절한 균형을 제공한다.
위의 GitHub 시나리오에서 세션 쿠키는 외부 웹사이트에서 일반 링크를 따라갈 때는 허용되지만 POST와 같은 CSRF가 발생하기 쉬운 요청 방식은
차단한다.

- SameSite를 사용하는 쿠키 예시

```http request
Set-Cookie: JSESSIONID=xxxxx; SameSite=Strict
Set-Cookie: JSESSIONID=xxxxx; SameSite=Lax
```

SamSite는 추가 계층 방어 개념으로 구현되어야 한다는 점에 유의해야 한다.
즉 SamSite를 사용했다고 해서 CSRF 토큰을 사용하지 않으면 안 된다. CSRF 토큰을 사용함은 물론 이러한 보안을
더욱 강력하게 만들어주는 보안 수단으로써 사용해야 한다.

`None`은 SameSite가 탄생하기 전의 쿠키와 동작 방식이 같다. `None`으로 설정된 경우 크로스 사이트 요청에도 항상 전송된다.
즉, 서드 파티 쿠키도 전송된다. 참고로 `None` 속성을 사용하려면 해당 쿠키는 반드시 `Secure`(HTTPS 요청)이여야 한다.


## . 참고 자료

> https://developer.mozilla.org/ko/docs/Web/HTTP/Cookies#secure%EA%B3%BC_httponly_%EC%BF%A0%ED%82%A4

> https://humanwhocodes.com/blog/2009/05/05/http-cookies-explained/

> https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html
