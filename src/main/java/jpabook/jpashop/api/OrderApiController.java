package jpabook.jpashop.api;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map.Entry;
import jpabook.jpashop.api.dto.OrderDto;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.query.OrderFlatDto;
import jpabook.jpashop.repository.order.query.OrderItemQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryDto;
import jpabook.jpashop.repository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

	private final OrderRepository orderRepository;
	private final OrderQueryRepository orderQueryRepository;
	private final OrderQueryService orderQueryService;

	@GetMapping("/api/v1/orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();

			List<OrderItem> orderItems = order.getOrderItems();
			orderItems.forEach(o -> o.getItem().getName());
		}
		return all;
	}

	@GetMapping("/api/v2/orders")
	public List<OrderDto> ordersV2() {
		List<Order> orders = orderRepository.findAllByString(new OrderSearch());
		return orders.stream()
			.map(OrderDto::new)
			.collect(toList());
	}

	@GetMapping("/api/v3/orders")
	public List<OrderDto> ordersV3() {
		List<Order> orders = orderRepository.findAllWithItem();
		return orders.stream()
			.map(OrderDto::new)
			.collect(toList());
	}

	@GetMapping("/api/v3.1/orders")
	public List<OrderDto> ordersV3_page(
		@RequestParam(value = "offset", defaultValue = "0") int offset,
		@RequestParam(value = "limit", defaultValue = "100") int limit) {

		return orderQueryService.ordersV3_page(offset, limit);
	}

	@GetMapping("/api/v4/orders")
	public List<OrderQueryDto> ordersV4() {
		return orderQueryRepository.findOrderQueryDtos();
	}

	@GetMapping("/api/v5/orders")
	public List<OrderQueryDto> ordersV5() {
		return orderQueryRepository.findAllByDto_optimization();
	}

	@GetMapping("api/v6/orders")
	public List<OrderQueryDto> ordersV6() {
		List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

		return flats.stream()
			.collect(
				groupingBy(OrderApiController::applyQueryDto,
					mapping(OrderApiController::applyItemQueryDto, toList())))
			.entrySet().stream()
			.map(OrderApiController::applyQueryDto).collect(toList());
	}

	private static OrderQueryDto applyQueryDto(OrderFlatDto o) {
		return new OrderQueryDto
			(o.getOrderId(), o.getName(), o.getOrderDate(), o.getAddress());
	}

	private static OrderItemQueryDto applyItemQueryDto(OrderFlatDto o) {
		return new OrderItemQueryDto
			(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount());
	}

	private static OrderQueryDto applyQueryDto(Entry<OrderQueryDto, List<OrderItemQueryDto>> e) {
		return new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey()
			.getOrderDate(), e.getKey().getAddress(), e.getValue());
	}
}
