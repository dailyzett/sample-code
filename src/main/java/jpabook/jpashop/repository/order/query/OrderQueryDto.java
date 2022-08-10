package jpabook.jpashop.repository.order.query;

import java.time.LocalDateTime;
import java.util.List;
import jpabook.jpashop.domain.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {


	private Long orderId;
	private String name;
	private LocalDateTime orderDate;
	private Address address;
	private List<OrderItemQueryDto> orderItems;

	public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
		Address address) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.address = address;
	}

	public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
		Address address, List<OrderItemQueryDto> orderItems) {
		this.orderId = orderId;
		this.name = name;
		this.orderDate = orderDate;
		this.address = address;
		this.orderItems = orderItems;
	}
}
