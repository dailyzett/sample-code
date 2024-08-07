package org.example.bankapp.common.exception

import org.example.bankapp.domain.dto.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class MemberNotFoundException(message: String?) : RuntimeException(message)
class MemberNotRegisteredException(message: String?) : RuntimeException(message)
class InSufficientBalanceException(message: String?) : RuntimeException(message)
class SingleExceedLimitException(message: String?) : RuntimeException(message)
class DailyExceedLimitException(message: String?) : RuntimeException(message)
class MonthlyExceedLimitException(message: String?) : RuntimeException(message)
class UnknownException(message: String?) : RuntimeException(message)
class DuplicatedPaymentException(message: String?) : RuntimeException(message)
class PaymentAlreadySuccessException(message: String?) : RuntimeException(message)
class PaymentExecutionTimeoutException(message: String?) : RuntimeException(message)


@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<CommonResponse> {
        val status: String
        val httpStatus: HttpStatus
        val message: String

        when (ex) {
            is MemberNotFoundException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "찾을 수 없는 사용자"
            }

            is MemberNotRegisteredException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "등록되지 않은 사용자"
            }

            is InSufficientBalanceException -> {
                status = HttpStatus.PAYMENT_REQUIRED.name
                httpStatus = HttpStatus.PAYMENT_REQUIRED
                message = "잔액이 부족합니다"
            }

            is SingleExceedLimitException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "한번에 지불할 수 있는 한계를 초과하였습니다"
            }

            is DailyExceedLimitException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "하루 지불 한도를 초과하였습니다"
            }

            is MonthlyExceedLimitException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "월 지불 한도를 초과하였습니다"
            }

            is UnknownException -> {
                status = "Unknown Exception"
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                message = "알 수 없는 에러"
            }

            is DuplicatedPaymentException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "중복 지불이 감지되었습니다"
            }

            is PaymentAlreadySuccessException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "지불이 이미 성공했습니다"
            }

            is PaymentExecutionTimeoutException -> {
                status = HttpStatus.REQUEST_TIMEOUT.name
                httpStatus = HttpStatus.REQUEST_TIMEOUT
                message = "지불 실행이 시간 초과되었습니다"
            }

            else -> {
                status = "UnKnown Exception"
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                message = "알 수 없는 에러"
            }
        }

        val response = CommonResponse(status, message)
        return ResponseEntity(response, httpStatus)
    }
}