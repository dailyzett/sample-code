# JPA SHOP

- JPA 연습 개발 리포지토리

## 사용 기술

- Java 17
- Spring Boot
- JPA
- JaCoCo
- GitHub Actions
- H2 DB

## 동작 요약

모든 요청과 응답은 _JSON_ 으로 주고 받음.

<details>
    <summary><b>회원 가입</b><br/></summary>

POST: `localhost:8080/members/new`

![img.png](img.png)
</details>

<details>
  <summary><b>회원 리스트 조회</b><br/></summary>

GET: `localhost:8080/members/`

![img_2.png](img_2.png)
</details>

<details>
  <summary><b>아이템 정보 수정</b><br/></summary>

PATCH: `localhost:8080/items/{id}`

![img_3.png](img_3.png)

</details>

<details>
  <summary><b>상품 주문</b><br/></summary>

POST: `localhost:8080/order`

![img_4.png](img_4.png)
</details>

## 기술적 이슈 해결 과정

<details>
  <summary><b>컨트롤러 예외 테스트 처리하기</b><br/></summary>

```java
public class MemberRequest {

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;
```

- MemberRequest DTO 의 name 값이 없으면 예외가 터져야한다.
- 하지만 컨트롤러에서 _BindingResult.hasError()_ 형식으로 예외를 던지면 아래의 코드가 _NestedServletException_
을 던지면서 테스트가 정상적으로 실행되지 않는다.

```java
@Test
@DisplayName("POST: members/new 회원이름 입력값 없을 때")
void exceptionTest1() throws Exception {
    //given
    MemberRequest request = createHaveNotNameFieldMemberInfo();

    //when
    mockMvc.perform(post("/members/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))

```

이를 해결하기 위해 컨트롤러에서 예외가 터지면 이것을 잡아서 일단 정상 처리 시켜주고
사용자가 작성한 코드에 따라 다음 행동을 결정하는 _@ExceptionHandler_ 를 사용.

```java
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> bindFieldErrorExceptionHandler(BindException e) {
        ErrorResult errorResult = new ErrorResult("FieldErrorException",
            String.valueOf(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()));
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }
}
```

이제 DTO 객체에서 _name_ 필드를 입력하지 않으면 _ExceptionHandler_ 가 작동해서 _ErrorResult_ 객체에
예외가 발생한 원인과 디폴트 메시지를 넣게 된다.

결과적으로 _controller_ 코드에서는 _BindingResult_ 의 if 처리 구문이 없어져서 코드는 깔끔해지고,
테스트 코드도 정상적으로 작동한다.

```java
@PostMapping("/members/new")
public ResponseEntity<MemberResponse> create(
    @Validated @RequestBody MemberRequest memberRequest) {

    Long joinId = memberService.join(memberRequest.toEntity());
    Member member = memberService.findOne(joinId);
    log.info("member info={}", member.toString());
    return new ResponseEntity<>(MemberResponse.from(member), HttpStatus.CREATED);
}
```

```java
@Test
@DisplayName("POST: members/new 회원이름 입력값 없을 때")
void exceptionTest1() throws Exception {
    //given
    MemberRequest request = createHaveNotNameFieldMemberInfo();

    //when
    mockMvc.perform(post("/members/new")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))

        //then
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("FieldErrorException"))
        .andExpect(jsonPath("$.message").value("회원 이름은 필수 입니다"));
}
```
</details>

<details>
  <summary><b>빌드 과정에서의 @Builder.default 경고</b><br/></summary>

- 깃허브 액션의 CI 빌드 도중 아래의 waring 출력

```text
/home/runner/work/jpashop/jpashop/src/main/java/jpabook/jpashop/domain/Member.java:38: warning: @Builder will ignore the initializing expression entirely. If you want the initializing expression to serve as default, add @Builder.Default. If it is not supposed to be settable during building, make the field final.
> Task :compileJava
	private List<Order> orders = new ArrayList<>();
	                    ^
1 warning
```

> **이유:**

- 회원 가입할 때 _Member_ 엔티티의 _orders_ 정보는 입력하지 않는다.
- 롬복의 _@Builder_ 는 필드를 사용하는 생성자와 각 필드의 setter 메서드로 구성된 inner 클래스를
하나 만들어서 내부에서 원본 클래스의 인스턴스를 반환한다.
- _Order_ 는 사용자 정의 객체이므로 이런 객체 타입을 초기화하는 코드는 _@Builder_ 에 당연히 존재하지 않는다.
`new ArrayList<>()` 로 초기화했다고 하더라도 _@Builder_ 가 적용되는 순간 null 로 초기화 된다.
- 따라서 정상적인 empty List 로 초기화하려면 _@Builder.Default_ 어노테이션이 필요.

```java
@Builder.Default
@OneToMany(mappedBy = "member")
private List<Order> orders = new ArrayList<>();
```

</details>

<details>
  <summary><b>회원 정보 조회 시 유연한 확장 고려</b><br/></summary>

```java
@GetMapping("api/v1/members")
public List<Member> membersV1() {
    return memberService.findMembers();
}
```

멤버 객체들을 찾아 그대로 멤버 엔티티를 리턴하면 코드를 작성할 땐 편하지만 큰 문제가 발생한다.

1. 조회에 필요 없는 필드값들도 같이 들어간다.
2. JSON API 명세에 값이 추가되었을 때 유연한 확장이 불가능하다.

```json
[
  "count": 2 // ????
    {
        "id": 1,
        "name": "new-hello",
        "address": null,
        "orders": []
    },
    {
        "id": 2,
        "name": "new-hello2",
        "address": null,
        "orders": []
    }
]
```

멤버 엔티티 리스트들을 그대로 반환해서 보여주다보니 모든 필드가 그대로 보여진다.
또한 API 명세가 멤버 필드만 출력하는 것이 아니라 총 몇개인지 `count`를 출력해달라고 한다면
JSON 스펙이 깨져버리는 문제가 발생한다.

따라서 확장을 고려하기 위해 `Result<T>`가 필요하고 필요한 정보만을 보여주기 위해 `MemberDto` 가 필요하다.

```java
@GetMapping("api/v2/members")
public Result membersV2() {
    List<Member> members = memberService.findMembers();
    List<MemberDto> collect = members.stream()
        .map(m -> new MemberDto(m.getName()))
        .collect(Collectors.toList());

    return new Result(collect.size(), collect);
}

@Data
@AllArgsConstructor
static class Result<T> {

    private int count;
    private T data;
}

@Data
@AllArgsConstructor
static class MemberDto {

    private String name;
}
```

멤버를 조회 후 stream api로 Member 엔티티들을 MemberDto 로 바꾸는 코드이다.
코드 변경 후 ResponseEntity 는 아래와 같이 출력된다.

추후에 API 명세가 바뀌더라도 Result 클래스에 확장이 필요한 필드만 추가하면 되고
Member 엔티티에서 필요한 정보만을 담는 `MemberDto`가 있기 때문에 확장 및 정보 은닉에 유리해졌다.

```json
{
    "count": 2,
    "data": [
        {
            "name": "new-hello"
        },
        {
            "name": "new-hello2"
        }
    ]
}
```


</details>