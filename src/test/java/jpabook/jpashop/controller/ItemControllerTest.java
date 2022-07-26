package jpabook.jpashop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.controller.dto.BookRequest;
import jpabook.jpashop.service.ItemService;
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
class ItemControllerTest {

	@Autowired
	MockMvc mockMvc;

	ObjectMapper mapper = new ObjectMapper();

	@Autowired
	ItemService itemService;

	@Test
	@DisplayName("POST /items/new")
	void test1() throws Exception {
		//given
		BookRequest request = createBookInfo("테스트 북");

		//when
		mockMvc.perform(post("/items/new")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))

			//then
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.name").value(request.getName()))
			.andExpect(jsonPath("$.price").value(request.getPrice()))
			.andExpect(jsonPath("$.stockQuantity").value(request.getStockQuantity()))
			.andExpect(jsonPath("$.isbn").value(request.getIsbn()));
	}

	private BookRequest createBookInfo(String name) {
		return BookRequest.builder()
			.name(name)
			.price(10000)
			.stockQuantity(50)
			.author("author1")
			.isbn("3335-1131")
			.build();
	}
}