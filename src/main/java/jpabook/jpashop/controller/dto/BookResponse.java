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
public class BookResponse {

	private Long id;

	private String name;
	private int price;
	private int stockQuantity;

	private String author;
	private String isbn;

	public static BookResponse from(Book book) {
		return BookResponse.builder()
			.id(book.getId())
			.name(book.getName())
			.price(book.getPrice())
			.stockQuantity(book.getStockQuantity())
			.author(book.getAuthor())
			.isbn(book.getIsbn())
			.build();
	}
}
