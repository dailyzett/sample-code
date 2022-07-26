package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

	private Long id;
	private String name;
	private Address address;

	public static MemberResponse from(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.address(new Address(
				member.getAddress().getCity(),
				member.getAddress().getStreet(),
				member.getAddress().getZipcode()))
			.build();
	}
}
