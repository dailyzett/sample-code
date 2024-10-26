package jpabook.jpashop.controller.dto;

import javax.validation.constraints.NotEmpty;
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
public class MemberRequest {

	@NotEmpty(message = "회원 이름은 필수 입니다")
	private String name;

	private String city;
	private String street;
	private String zipcode;

	public Member toEntity() {
		return Member.builder()
			.name(this.name)
			.address(new Address(city, street, zipcode))
			.build();
	}
}
