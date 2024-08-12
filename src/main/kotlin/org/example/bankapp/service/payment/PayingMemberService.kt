package org.example.bankapp.service.payment

import org.example.bankapp.common.exception.MemberNotFoundException
import org.example.bankapp.common.exception.UnknownException
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.Member
import org.example.bankapp.repository.member.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PayingMemberService(
    private val memberRepository: MemberRepository
) {

    fun createPayingMember(paymentRequestDto: PaymentRequestDto): Member {
        val memberData: Member = memberRepository.findByIdOrNull(paymentRequestDto.payingMemberId.id)
            ?: throw MemberNotFoundException("")

        if (canMemberMakePayment(memberData, paymentRequestDto)) return memberData
        else throw UnknownException("")
    }

    private fun canMemberMakePayment(memberData: Member, paymentRequestDto: PaymentRequestDto): Boolean {
        val isRegistered = memberData.isMemberRegistered()
        val isBalanceSufficient = memberData.isBalanceSufficient(paymentRequestDto.amount)
        val isBalanceLimitNotExceeded = memberData.isBalanceLimitNotExceeded(paymentRequestDto.amount)

        return isRegistered && isBalanceSufficient && isBalanceLimitNotExceeded
    }
}