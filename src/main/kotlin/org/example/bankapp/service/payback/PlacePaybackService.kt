package org.example.bankapp.service.payback

import org.example.bankapp.common.event.Events
import org.example.bankapp.domain.dto.PaybackEventsDto
import org.example.bankapp.domain.dto.PaybackRequestDto
import org.example.bankapp.domain.dto.PaybackResponseDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payback.PaybackEvent
import org.example.bankapp.domain.payback.PaybackEventId
import org.example.bankapp.repository.EventJdbcRepository
import org.example.bankapp.service.event.EventIdService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PlacePaybackService(
    private val eventIdService: EventIdService,
    private val eventJdbcRepository: EventJdbcRepository
) {
    @Transactional
    fun placePaybackEvent(paybackRequestDto: PaybackRequestDto): PaybackResponseDto {
        val paybackTargetId = MemberId(paybackRequestDto.memberId)
        val paybackPercent = paybackRequestDto.percent
        val paymentEventId = paybackRequestDto.paymentEventId
        val paybackEventId: PaybackEventId = eventIdService.createPaybackEventId(paybackRequestDto.memberId)

        val paybackEvent = PaybackEvent(
            id = paybackEventId,
            paybackTargetId = paybackTargetId,
            isPaybackDone = false,
            createdDt = LocalDateTime.now(),
        )

        eventJdbcRepository.insertPaybackEvent(paybackEvent)

        val paybackEventsDto = PaybackEventsDto(
            paybackEvent = paybackEvent,
            paybackPercent = paybackPercent,
            paymentEventId = paymentEventId
        )

        Events.raise(paybackEventsDto)
        return PaybackResponseDto(paybackEventId.id)
    }
}