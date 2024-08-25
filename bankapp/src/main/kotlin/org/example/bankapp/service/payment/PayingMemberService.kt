package org.example.bankapp.service.payment

import org.example.bankapp.common.exception.MemberNotFoundException
import org.example.bankapp.common.exception.UnknownException
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.Members
import org.example.bankapp.repository.member.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PayingMemberService(
    private val memberRepository: MemberRepository
) {

    fun createPayingMember(paymentRequestDto: PaymentRequestDto): Members {
        val membersData: Members = memberRepository.findByIdOrNull(paymentRequestDto.payingMemberId.id)
            ?: throw MemberNotFoundException("")

        if (canMemberMakePayment(membersData, paymentRequestDto)) return membersData
        else throw UnknownException("")
    }

    private fun canMemberMakePayment(membersData: Members, paymentRequestDto: PaymentRequestDto): Boolean {
        val isRegistered = membersData.isMemberRegistered()
        val isBalanceSufficient = membersData.isBalanceSufficient(paymentRequestDto.amount)
        val isBalanceLimitNotExceeded = membersData.isBalanceLimitNotExceeded(paymentRequestDto.amount)

        return isRegistered && isBalanceSufficient && isBalanceLimitNotExceeded
    }
}