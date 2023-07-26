package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

	private Long orderId;

	private Long memberId;
	private String memberName;

	private Long itemId;
	private String itemName;
	private int stockQuantity;

	public static OrderResponse from(Long orderId, Member member, Item item) {
		return OrderResponse.builder()
			.orderId(orderId)
			.memberId(member.getId())
			.memberName(member.getName())
			.itemId(item.getId())
			.itemName(item.getName())
			.stockQuantity(item.getStockQuantity())
			.build();
	}
}
