package jpabook.jpashop.controller.dto;

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
	private String city;
	private String street;
	private String zipcode;

	public static MemberResponse from(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.city(member.getAddress().getCity())
			.street(member.getAddress().getStreet())
			.zipcode(member.getAddress().getZipcode())
			.build();
	}
}
