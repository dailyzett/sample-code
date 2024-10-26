package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * xToOne(ManyToOne, OneToOne) Order Order -> Member Order -> Delivery
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	private final OrderSimpleRepository orderSimpleRepository;

	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();
		}
		return all;
	}

	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> ordersV2() {
		return orderRepository.findAllByString(new OrderSearch()).stream()
			.map(SimpleOrderDto::new)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/v3/simple-orders")
	public Result<List<SimpleOrderDto>> ordersV3() {
		List<SimpleOrderDto> collect = orderRepository.findAllWithMemberDelivery().stream()
			.map(SimpleOrderDto::new)
			.collect(Collectors.toList());

		return new Result<>(collect);
	}

	@GetMapping("/api/v4/simple-orders")
	public Result<List<OrderSimpleQueryDto>> ordersV4() {
		List<OrderSimpleQueryDto> orderDtos = orderSimpleRepository.findOrderDtos();
		return new Result<>(orderDtos);
	}

	@Data
	@AllArgsConstructor
	static class Result<T> {
		private T data;
	}

	@Data
	static class SimpleOrderDto {
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;

		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderStatus = order.getStatus();
			orderDate = order.getOrderDate();
			address = order.getDelivery().getAddress();
		}
	}
}
