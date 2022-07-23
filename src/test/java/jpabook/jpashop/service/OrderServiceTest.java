package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

	@PersistenceContext
	EntityManager em;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderRepository orderRepository;

	@Test
	@DisplayName("상품 주문")
	void test1() {
		//given
		Member member = createMember();
		Book book = createBook("책 이름1", 10000, 10);
		int orderCount = 2;

		//when
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		//then
		Order getOrder = orderRepository.findOne(orderId);
		assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
		assertThat(1).isEqualTo(getOrder.getOrderItems().size());
		assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice());
		assertThat(8).isEqualTo(book.getStockQuantity());
	}

	@Test
	@DisplayName("상품 주문_재고수량초과")
	void exceptionTest1() {
		//given
		Member member = createMember();
		Book book = createBook("책 이름1", 10000, 10);

		int orderCount = 11;

		//when
		assertThatThrownBy(() -> {
			//then
			orderService.order(member.getId(), book.getId(), orderCount);
		}).isInstanceOf(NotEnoughStockException.class);
	}

	@Test
	@DisplayName("주문 취소")
	void test2() {
		//given
		Member member = createMember();
		Book book = createBook("정의", 500, 15);
		int orderCount = 5;
		Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

		//when
		orderService.cancelOrder(orderId);

		//then
		Order getOrder = orderRepository.findOne(orderId);
		assertThat(OrderStatus.CANCEL).isEqualTo(getOrder.getStatus());
		assertThat(15).isEqualTo(book.getStockQuantity());
	}

	private Book createBook(String name, int price, int stockQuantity) {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		em.persist(book);
		return book;
	}

	private Member createMember() {
		Member member = new Member();
		member.setName("회원1");
		member.setAddress(new Address("서울", "강가", "123-456"));
		em.persist(member);
		return member;
	}
}