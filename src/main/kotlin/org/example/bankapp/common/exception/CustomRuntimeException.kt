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
class DuplicatedPaybackException(message: String?) : RuntimeException(message)
class DuplicatedPaybackCancelException(message: String?) : RuntimeException(message)
class DuplicatedPaymentCancelException(message: String?) : RuntimeException(message)
class PaymentAlreadySuccessException(message: String?) : RuntimeException(message)
class EventExecutionTimeoutException(message: String?) : RuntimeException(message)
class AlreadyCancelledPaymentException(message: String?) : RuntimeException(message)
class AlreadyPaybackCancelledPaymentException(message: String?) : RuntimeException(message)
class PaymentOrderIsEmpty(message: String?) : RuntimeException(message)
class NotFoundPaymentSuccessEvent(message: String?) : RuntimeException(message)
class NotFoundPaymentEvent(message: String?) : RuntimeException(message)
class NotFoundPaybackOrder(message: String?) : RuntimeException(message)
class NotFoundPaybackEvent(message: String?) : RuntimeException(message)
class NotFoundPaymentCancelEvent(message: String?) : RuntimeException(message)
class NotNeedToPayback(message: String?) : RuntimeException(message)
class AlreadyPaybackSuccessException(message: String?) : RuntimeException(message)
class PaymentNotCompletedException(message: String?) : RuntimeException(message)
class PaybackNotCompletedException(message: String?) : RuntimeException(message)
class PaymentCancelNotCompletedException(message: String?) : RuntimeException(message)

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

            is NotFoundPaybackOrder -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "찾을 수 없는 페이백 주문"
            }

            is NotFoundPaybackEvent -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "찾을 수 없는 페이백 이벤트"
            }

            is NotNeedToPayback -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "페이백을 하지 않아도 되는 대상"
            }

            is AlreadyPaybackSuccessException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "이미 처리된 페이백 내역입니다"
            }

            is PaybackNotCompletedException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "페이백 이벤트가 완료되지 않아 환불이 불가능합니다."
            }

            is MemberNotRegisteredException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "등록되지 않은 사용자"
            }

            is AlreadyPaybackCancelledPaymentException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "이미 취소된 페이백 이력"
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

            is EventExecutionTimeoutException -> {
                status = HttpStatus.REQUEST_TIMEOUT.name
                httpStatus = HttpStatus.REQUEST_TIMEOUT
                message = "이벤트 실행 시간 초과되었습니다"
            }

            is AlreadyCancelledPaymentException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "이미 취소된 결제 건입니다."
            }

            is DuplicatedPaymentCancelException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "중복 취소 요청이 감지되었습니다"
            }

            is PaymentOrderIsEmpty -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "결제 내역을 찾을 수 없습니다"
            }

            is NotFoundPaymentSuccessEvent -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "결제 성공 내역을 찾을 수 없습니다"
            }

            is PaymentNotCompletedException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "완료되지 않은 결제건에 대한 환불은 진행할 수 없습니다"
            }

            is NotFoundPaymentEvent -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "결제 내역이 없습니다."
            }

            is NotFoundPaymentCancelEvent -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "결제 취소 내역이 없습니다"
            }

            is PaymentCancelNotCompletedException -> {
                status = HttpStatus.BAD_REQUEST.name
                httpStatus = HttpStatus.BAD_REQUEST
                message = "결제 취소가 완료되지 않은 건입니다"
            }

            else -> {
                status = "Unknown Exception"
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                message = ""
                ex.printStackTrace()
            }
        }

        val response = CommonResponse(status, message)
        return ResponseEntity(response, httpStatus)
    }
}