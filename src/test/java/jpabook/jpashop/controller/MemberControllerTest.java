package jpabook.jpashop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.controller.dto.MemberRequest;
import jpabook.jpashop.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	MemberService memberService;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("POST: members/new")
	void test1() throws Exception {
		//given
		MemberRequest request = createTestMemberInfo("테스트용 이름");

		//when
		mockMvc.perform(post("/members/new")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
			//then
			.andDo(print())
			.andExpect(jsonPath("$.name").value(request.getName()))
			.andExpect(jsonPath("$.city").value(request.getCity()))
			.andExpect(jsonPath("$.street").value(request.getStreet()));
	}

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

	@Test
	@DisplayName("GET: /members")
	void test2() throws Exception {
		//given
		MemberRequest kim = createTestMemberInfo("kim");
		MemberRequest park = createTestMemberInfo("park");
		memberService.join(kim.toEntity());
		memberService.join(park.toEntity());

		//when
		mockMvc.perform(get("/members")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))

			//then
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(2))
			.andExpect(jsonPath("$[0].name").value(kim.getName()))
			.andExpect(jsonPath("$[1].name").value(park.getName()));
	}

	private MemberRequest createHaveNotNameFieldMemberInfo() {
		return MemberRequest.builder()
			.city("서울")
			.zipcode("533-32")
			.build();
	}

	private MemberRequest createTestMemberInfo(String name) {
		return MemberRequest.builder()
			.name(name)
			.city("서울")
			.zipcode("533-23")
			.street("관악구")
			.build();
	}
}