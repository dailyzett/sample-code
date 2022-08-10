package jpabook.jpashop.api.dto;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import lombok.Data;

@Data
public class OrderDto {

	private Long orderId;
	private String name;
	private LocalDateTime orderDate;
	private Address address;
	private List<OrderItemDto> orderItems;

	public OrderDto(Order order) {
		orderId = order.getId();
		name = order.getMember().getName();
		orderDate = order.getOrderDate();
		address = order.getDelivery().getAddress();
		orderItems = order.getOrderItems().stream()
			.map(OrderItemDto::new)
			.collect(toList());
	}
}
