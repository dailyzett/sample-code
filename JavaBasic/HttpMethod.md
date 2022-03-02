## Http Method

대표적으로 GET, POST, PUT, DELETE 4가지가 있다.

- GET : 조회
- POST : 등록
- PUT : 수정
- DELETE : 삭제


### GET

GET Method 는 데이터를 읽을 때(Read) 사용된다. GET 요청이 정상이라면 200 HTTP 응답 코드를 리턴한다.
에러가 발생하면 404(Not found) 나 400 (Bad Request) 에러가 발생한다.

- GET 메서드는 오로지 읽을 때만 사용한다.
- GET 요청은 idempotent 하다.
- 같은 요청을 여러 번해도 항상 같은 응답을 받을 수 있다.

>idempotent
> > 한글로 멱등성이라고 하며 여러 번 실행해도 그 결과가 같음을 의미하는 단어이다.

데이터를 조회하는 것이기 때문에 request 시 Body 와 Content-Type 이 비워져있다.
조회할 때는 URL 을 통해 파라미터를 받는다. 데이터 조회에 성공하면 Body 에 데이터를 저장하여 성공 응답을 리턴한다.

GET 은 캐싱이 가능하여 같은 데이터를 한번 더 조회할 때 저장한 값을 사용하기 때문에 속도가 빨라진다.


### POST

새로운 리소스를 생성(create) 할 때 사용된다. POST 요청이 정상이라면 201 HTTP 응답 코드를 리턴한다.

- POST 요청은 idempotent 하지 않다.
- 같은 POST 요청을 해도 같은 결과물이 나오는 것을 보장하지 않는다.

Request 시에 Body 값과 Content-Type 값이 필요하다 <br>
URL 을 통해서 데이터를 받지 않고 Body 를 통해서 받는다.


### PUT

리소스를 업데이트하기 위해 사용된다.

- PUT 요청은 idempotent 하다.

URL 을 통해서 어떤 데이터를 수정할 지 파라미터를 받고, 데이터 값은 Body 를 통해서 받는다.
<br> POST 와 다른점은 POST 는 Request 시 마다 데이터를 새로 생성하지만 PUT 은 사용자가 데이터를
지정하고 수정하는 것이기 때문에 같은 Request 를 반복해도 데이터가 계속 생성되지 않는다


#### PATCH

위에 소개가 안된 Method 중 PATCH 메서드도 있는데, PUT 과 비슷하지만 PUT 은 지정한 데이터를 전부
변경하는 반면 PATCH 는 데이터의 일부분이 변경된다.

### DELETE

지정된 리소스를 삭제하기 위해 사용된다.

데이터를 삭제하기 때문에 Request 시 Body 와 Content-type 이 비워져있다.

URL을 통해서 어떤 데이터를 삭제할 지 파라미터를 받는다.
<br> 삭제 성공 시 Body 값 없이 성공 응답만 리턴한다.

