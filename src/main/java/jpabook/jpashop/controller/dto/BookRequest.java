package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.item.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	private String author;
	private String isbn;

	public Book toEntity() {
		Book book = new Book();
		book.setName(name);
		book.setPrice(price);
		book.setStockQuantity(stockQuantity);
		book.setAuthor(author);
		book.setIsbn(isbn);
		return book;
	}
}
