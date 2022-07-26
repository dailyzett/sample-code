package jpabook.jpashop.controller;

import jpabook.jpashop.controller.dto.BookRequest;
import jpabook.jpashop.controller.dto.BookResponse;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@PostMapping("/items/new")
	public ResponseEntity<BookResponse> create(@RequestBody BookRequest bookRequest) {
		Long id = itemService.saveItem(bookRequest.toEntity());
		log.info("book id={}", id);
		Item item = itemService.findOne(id);
		return new ResponseEntity<>(BookResponse.from((Book) item), HttpStatus.CREATED);
	}
}
