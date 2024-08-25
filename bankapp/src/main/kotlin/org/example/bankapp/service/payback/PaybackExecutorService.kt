package org.example.bankapp.service.payback

import org.example.bankapp.common.exception.MemberNotFoundException
import org.example.bankapp.common.exception.MemberNotRegisteredException
import org.example.bankapp.common.exception.NotFoundPaybackOrder
import org.example.bankapp.common.exception.NotNeedToPayback
import org.example.bankapp.domain.dto.PaybackEventsDto
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.payback.PaybackOrderStatus.*
import org.example.bankapp.domain.payback.PaybackOrders
import org.example.bankapp.repository.member.MemberRepository
import org.example.bankapp.repository.payback.PaybackEventRepository
import org.example.bankapp.repository.payback.PaybackOrderRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PaybackExecutorService(
    private val paybackEventRepository: PaybackEventRepository,
    private val paybackOrderRepository: PaybackOrderRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun addPaybackAmount(event: PaybackEventsDto) {
        val paybackEventId = event.paybackEvents.id
        val paymentEventId = event.paymentEventId
        val paybackPercent = event.paybackPercent

        val foundPaybackOrder = paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(paymentEventId)
            ?: throw NotFoundPaybackOrder("")

        if (foundPaybackOrder.paybackOrderStatus == NOT_NEED_TO_PAYBACK) throw NotNeedToPayback("")

        val paybackAmount = calculatePaybackAmount(foundPaybackOrder, paybackPercent)
        updateMemberCurrentBalance(paybackAmount, foundPaybackOrder.paybackTargetId)

        val paybackOrders = PaybackOrders(
            paybackEventId = paybackEventId,
            paymentEventId = foundPaybackOrder.paymentEventId,
            paybackOrderStatus = SUCCESS,
            paymentAmount = foundPaybackOrder.paymentAmount,
            paybackAmount = paybackAmount,
            paybackTargetId = foundPaybackOrder.paybackTargetId
        )

        paybackOrderRepository.save(paybackOrders)

        val foundPaybackEvent = paybackEventRepository.findByIdOrNull(paybackEventId)!!
        foundPaybackEvent.updateToPaybackDoneTrue()
    }

    private fun calculatePaybackAmount(paybackOrders: PaybackOrders, paybackPercent: Int): Int {
        return (paybackOrders.paymentAmount * (paybackPercent.toDouble() / 100.0)).toInt()
    }

    private fun updateMemberCurrentBalance(paybackAmount: Int, paybackTargetId: MemberId) {
        val foundMember = memberRepository.findByIdOrNull(paybackTargetId.id) ?: throw MemberNotFoundException("")
        if (!foundMember.isRegistered) throw MemberNotRegisteredException("")
        foundMember.addMemberWalletBalance(paybackAmount)
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun fail(event: PaybackEventsDto) {
        val paybackEventId = event.paybackEvents.id
        val paymentEventId = event.paymentEventId

        val foundPaybackOrder = paybackOrderRepository.findTopByPaymentEventIdOrderByIdDesc(paymentEventId)
            ?: throw NotFoundPaybackOrder("")

        val paybackOrders = PaybackOrders(
            paybackEventId = paybackEventId,
            paymentEventId = paymentEventId,
            paybackOrderStatus = FAILED,
            paymentAmount = foundPaybackOrder.paymentAmount,
            paybackAmount = 0,
            paybackTargetId = foundPaybackOrder.paybackTargetId
        )

        paybackOrderRepository.save(paybackOrders)
    }
}