package jpabook.jpashop.controller;

import jpabook.jpashop.controller.dto.OrderResponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
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
public class OrderController {

	private final OrderService orderService;
	private final MemberService memberService;
	private final ItemService itemService;

	@PostMapping("/order")
	public ResponseEntity<OrderResponse> order(@RequestBody OrderRequest orderRequest) {

		Long orderId = orderService.order(orderRequest.getMemberId(), orderRequest.getItemId(),
			orderRequest.getCount());

		Member member = memberService.findOne(orderRequest.getMemberId());
		Item item = itemService.findOne(orderRequest.getItemId());
		return new ResponseEntity<>(OrderResponse.from(orderId, member, item), HttpStatus.CREATED);
	}
}
