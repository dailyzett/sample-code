# JPA SHOP

- JPA 연습 개발 리포지토리

## 사용 기술

- Java 17
- Spring Boot
- JPA
- JaCoCo
- GitHub Actions
- H2 DB

## 규칙

- 목표 테스트 커버리지 : **70% 이상**

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