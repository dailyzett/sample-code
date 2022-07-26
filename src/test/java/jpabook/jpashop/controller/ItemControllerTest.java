package jpabook.jpashop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
		BookRequest request = createBookInfo("테스트 북", "author1", "3335-1131", 10000, 50);

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

	@Test
	@DisplayName("GET /items")
	void test2() throws Exception {
		//given
		BookRequest book1 = createBookInfo("테스트 북1", "author1", "3335-1131", 10000, 50);
		BookRequest book2 = createBookInfo("테스트 북2", "author1", "3335-1131", 10000, 50);
		itemService.saveItem(book1.toEntity());
		itemService.saveItem(book2.toEntity());

		//when
		mockMvc.perform(get("/items"))
			//then
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].name").value(book1.getName()))
			.andExpect(jsonPath("$[1].name").value(book2.getName()))
			.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	@DisplayName("PATCH /items/{id}")
	void test3() throws Exception {
		//given
		BookRequest book = createBookInfo("초기 북", "author1", "3335-1131", 10000, 50);
		Long id = itemService.saveItem(book.toEntity());
		BookRequest modifiedBook = createBookInfo("수정된 북", "수정된 author", "1234-56", 100, 15);

		//when
		mockMvc.perform(patch("/items/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(modifiedBook)))
			//then
			.andDo(print())
			.andExpect(status().is3xxRedirection());
	}

	private BookRequest createBookInfo(String name, String author, String isbn, int price,
		int stock) {
		return BookRequest.builder()
			.name(name)
			.price(price)
			.stockQuantity(stock)
			.author(author)
			.isbn(isbn)
			.build();
	}
}