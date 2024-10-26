package jpabook.jpashop.exception;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler
	public ResponseEntity<ErrorResult> bindFieldErrorExceptionHandler(BindException e) {
		ErrorResult errorResult = new ErrorResult("FieldErrorException",
			String.valueOf(Objects.requireNonNull(e.getFieldError()).getDefaultMessage()));
		return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
	}
}
