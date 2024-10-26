package jpabook.jpashop.controller;

import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.controller.dto.BookRequest;
import jpabook.jpashop.controller.dto.BookResponse;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@ResponseBody
	@PostMapping("/items/new")
	public ResponseEntity<BookResponse> create(@RequestBody BookRequest bookRequest) {
		Long id = itemService.saveItem(bookRequest.toEntity());
		log.info("book id={}", id);
		Item item = itemService.findOne(id);
		return new ResponseEntity<>(BookResponse.from((Book) item), HttpStatus.CREATED);
	}

	@ResponseBody
	@GetMapping("/items")
	public ResponseEntity<List<BookResponse>> list() {
		List<BookResponse> bookResponses = new ArrayList<>();
		for (Item item : itemService.findItems()) {
			bookResponses.add(BookResponse.from((Book) item));
		}
		return new ResponseEntity<>(bookResponses, HttpStatus.OK);
	}


	@PatchMapping("/items/{id}")
	public String update(@PathVariable("id") Long id,
		@RequestBody BookRequest bookRequest) {

		itemService.updateItem(id, bookRequest.getName(), bookRequest.getPrice(),
			bookRequest.getStockQuantity());
		log.info(":::Book Request Info:{}:::", bookRequest);
		return "redirect:/items";
	}
}
