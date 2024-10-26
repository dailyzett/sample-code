package jpabook.jpashop.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ErrorResult {

	private String code;
	private String message;
}
