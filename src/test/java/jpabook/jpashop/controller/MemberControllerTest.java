package jpabook.jpashop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.controller.dto.MemberRequest;
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

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("POST: members/new")
	void test1() throws Exception {
		//given
		MemberRequest request = createTestMemberInfo();

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

	private MemberRequest createHaveNotNameFieldMemberInfo() {
		return MemberRequest.builder()
			.city("서울")
			.zipcode("533-32")
			.build();
	}

	private MemberRequest createTestMemberInfo() {
		return MemberRequest.builder()
			.name("테스트용 이름")
			.city("서울")
			.zipcode("533-23")
			.street("관악구")
			.build();
	}
}