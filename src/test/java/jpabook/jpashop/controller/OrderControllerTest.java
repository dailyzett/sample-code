package jpabook.jpashop.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpabook.jpashop.controller.dto.BookRequest;
import jpabook.jpashop.controller.dto.MemberRequest;
import jpabook.jpashop.controller.dto.OrderResponse;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class OrderControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	MemberService memberService;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderRepository orderRepository;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("POST /order")
	void test1() throws Exception {
		//given
		int initialStock = 30;
		int initialPrice = 500;
		OrderRequest request = createTestOrder();

		//when
		MvcResult result = mockMvc.perform(post("/order")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request)))
			//then
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.memberId").value(request.getMemberId()))
			.andExpect(jsonPath("$.itemId").value(request.getItemId()))
			.andExpect(jsonPath("$.stockQuantity").value(initialStock - request.getCount()))
			.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		OrderResponse response = mapper.readValue(contentAsString, OrderResponse.class);
		Order order = orderRepository.findOne(response.getOrderId());

		assertThat(order.getTotalPrice()).isEqualTo(request.getCount() * initialPrice);
	}

	private OrderRequest createTestOrder() {
		return OrderRequest.builder()
			.memberId(findMemberId())
			.itemId(findItemId())
			.count(10) // 주문 수량
			.build();
	}

	private Long findMemberId() {
		MemberRequest memberRequest = createTestMemberInfo("kim");
		return memberService.join(memberRequest.toEntity());
	}

	private Long findItemId() {
		BookRequest bookRequest = createBookInfo("테스트북", "김민석", "333-23", 500, 30);
		return itemService.saveItem(bookRequest.toEntity());
	}

	private MemberRequest createTestMemberInfo(String name) {
		return MemberRequest.builder()
			.name(name)
			.city("서울")
			.zipcode("533-23")
			.street("관악구")
			.build();
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