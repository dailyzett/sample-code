package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.item.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookRequest {

	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	private String author;
	private String isbn;

	public void setId(Long id) {
		this.id = id;
	}

	public Book toEntity() {
		Book book = new Book();
		book.setId(id);
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		book.setAuthor(author);
		book.setIsbn(isbn);
		return book;
	}
}
