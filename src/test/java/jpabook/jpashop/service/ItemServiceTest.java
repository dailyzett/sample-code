package jpabook.jpashop.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ItemServiceTest {

	@Autowired
	ItemService itemService;

	@Autowired
	EntityManager em;

	Book book = null;

	@BeforeEach
	void initData() {
		book = new Book();
		book.setAuthor("test-author");
		book.setIsbn("233-test");
		book.setName("test-book");
		book.setPrice(3000);
		book.addStock(10);
	}

	@Test
	@DisplayName("아이템 저장")
	void saveItem() {
		//given
		//when
		itemService.saveItem(book);
		Long id = book.getId();
		em.flush();
		em.clear();

		//then
		Item one = itemService.findOne(id);
		assertThat(one.getName()).isEqualTo(book.getName());
		assertThat(one.getPrice()).isEqualTo(book.getPrice());
		assertThat(one.getStockQuantity()).isEqualTo(book.getStockQuantity());
	}

	@Test
	@DisplayName("아이템 수정")
	void updateItem() {
		//given
		Long id = itemService.saveItem(book);
		em.flush();
		em.clear();

		//when
		Item item = itemService.updateItem(id, "수정된 책이름", 100, 50);

		//then
		assertThat(item.getName()).isEqualTo("수정된 책이름");
		assertThat(item.getPrice()).isEqualTo(100);
		assertThat(item.getStockQuantity()).isEqualTo(50);
	}

	@Test
	@DisplayName("아이템 목록 출력")
	void findItems() {
		//given
		Book book2 = new Book();
		book2.setAuthor("test-author2");
		book2.setIsbn("233-test2");
		book2.setName("test-book2");
		book2.setPrice(30000);
		book2.addStock(15);

		//when
		itemService.saveItem(book);
		itemService.saveItem(book2);
		em.flush();
		em.clear();

		//then
		List<Item> items = itemService.findItems();
		assertThat(items).hasSize(2);
		assertThat(items.get(0).getName()).isEqualTo(book.getName());
		assertThat(items.get(1).getName()).isEqualTo(book2.getName());
	}

	@Test
	@DisplayName("재고가 마이너스 될 때")
	void removeStock() {
		//given
		itemService.saveItem(book);
		//when
		assertThatThrownBy(() -> {
			//then
			book.removeStock(50);
		}).isInstanceOf(NotEnoughStockException.class);
	}
}