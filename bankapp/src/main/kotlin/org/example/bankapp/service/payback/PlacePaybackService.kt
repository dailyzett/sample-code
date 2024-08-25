package org.example.bankapp.service.payback

import org.example.bankapp.common.event.Events
import org.example.bankapp.common.exception.AlreadyPaybackCancelledPaymentException
import org.example.bankapp.common.exception.AlreadyPaybackSuccessException
import org.example.bankapp.domain.dto.*
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payback.PaybackCancelEvents
import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.domain.payback.PaybackEvents
import org.example.bankapp.domain.payback.PaybackOrderStatus
import org.example.bankapp.repository.EventJdbcRepository
import org.example.bankapp.repository.payback.PaybackCancelEventRepository
import org.example.bankapp.repository.payback.PaybackOrderRepository
import org.example.bankapp.service.event.EventIdService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PlacePaybackService(
    private val eventIdService: EventIdService,
    private val eventJdbcRepository: EventJdbcRepository,
    private val paybackCancelEventRepository: PaybackCancelEventRepository,
    private val paybackOrderRepository: PaybackOrderRepository,
) {
    @Transactional
    fun placePaybackEvent(paybackRequestDto: PaybackRequestDto): PaybackResponseDto {
        val paybackTargetId = MemberId(paybackRequestDto.memberId)
        val paybackPercent = paybackRequestDto.percent
        val paymentEventId = paybackRequestDto.paymentEventId
        val paybackEventId: PaybackEventId = eventIdService.createPaybackEventId(paybackRequestDto.memberId)

        val paybackEvents = PaybackEvents(
            id = paybackEventId,
            paybackTargetId = paybackTargetId,
            isPaybackDone = false,
            createdDt = LocalDateTime.now(),
        )

        paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(paymentEventId)
            ?.let { if (it.paybackOrderStatus == PaybackOrderStatus.SUCCESS) throw AlreadyPaybackSuccessException("") }

        eventJdbcRepository.insertPaybackEvent(paybackEvents)

        val paybackEventsDto = PaybackEventsDto(
            paybackEvents = paybackEvents,
            paybackPercent = paybackPercent,
            paymentEventId = paymentEventId
        )

        Events.raise(paybackEventsDto)
        return PaybackResponseDto(paybackEventId.id)
    }

    @Transactional
    fun placePaybackCancelEvent(paybackCancelRequestDto: PaybackCancelRequestDto): PaybackCancelResponseDto {
        val cancellingMemberId = paybackCancelRequestDto.cancellingMemberId
        val paybackCancelEventId = eventIdService.createPaybackCancelEventId(cancellingMemberId.id)

        val paybackEventId = PaybackEventId(paybackCancelRequestDto.paybackEventId)
        val paybackCancelEvents = PaybackCancelEvents(
            id = paybackCancelEventId,
            isCancelDone = false,
            paybackEventId = paybackEventId
        )

        paybackCancelEventRepository.countByPaybackEventId(paybackEventId)
            .let { if (it > 0) throw AlreadyPaybackCancelledPaymentException("") }

        eventJdbcRepository.insertPaybackCancelEvent(paybackCancelEvents)

        val paybackCancelEventsDto = PaybackCancelEventsDto(paybackCancelEvents)
        Events.raise(paybackCancelEventsDto)
        return PaybackCancelResponseDto(paybackCancelEventId)
    }
}