package org.example.bankapp.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.example.bankapp.common.exception.*
import org.example.bankapp.domain.dto.PaymentRequestDto
import org.example.bankapp.domain.member.BalanceLimit
import org.example.bankapp.domain.member.Member
import org.example.bankapp.domain.member.MemberId
import org.example.bankapp.domain.member.Wallet
import org.example.bankapp.repository.payment.member.MemberRepository
import org.example.bankapp.service.payment.PayingMemberService
import org.springframework.data.repository.findByIdOrNull


class PayingMemberServiceTest : BehaviorSpec({
    Given("5만원을 송신하려는 사용자 정보가 주어질 때") {
        val paymentRequestDto = PaymentRequestDto(MemberId(23L), 50000)
        val memberRepository = mockk<MemberRepository>()
        val payingMemberService = PayingMemberService(memberRepository)

        When("사용자 정보를 찾을 수 없으면") {
            every { memberRepository.findByIdOrNull(any()) } returns null
            Then("EntityNotFoundException 예외를 던진다") {
                shouldThrow<MemberNotFoundException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }

        When("가입되어 있는 상태가 아니라면") {
            every { memberRepository.findByIdOrNull(any()) } returns Member(
                23L,
                "TestUser",
                false,
                BalanceLimit(50000, 100000, 200000),
                Wallet(60000)
            )
            Then("MemberNotRegisteredException 예외를 던진다") {
                shouldThrow<MemberNotRegisteredException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }

        When("현재 가지고 있는 잔액이 부족하다면") {
            every { memberRepository.findByIdOrNull(any()) } returns Member(
                23L,
                "TestUser",
                true,
                BalanceLimit(50000, 100000, 200000),
                Wallet(40000)
            )
            Then("InSufficientBalanceException 예외를 던진다") {
                shouldThrow<InSufficientBalanceException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }

        When("1회 송신 한도를 초과했다면") {
            every { memberRepository.findByIdOrNull(any()) } returns Member(
                23L,
                "TestUser",
                true,
                BalanceLimit(40000, 100000, 200000),
                Wallet(60000)
            )
            Then("SingleExceedLimitException 예외를 던진다") {
                shouldThrow<SingleExceedLimitException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }

        When("1일 송신 한도를 초과했다면") {
            every { memberRepository.findByIdOrNull(any()) } returns Member(
                23L,
                "TestUser",
                true,
                BalanceLimit(60000, 40000, 200000),
                Wallet(60000)
            )
            Then("DailyExceedLimitException 예외를 던진다") {
                shouldThrow<DailyExceedLimitException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }

        When("한 달 송신 한도를 초과했다면") {
            every { memberRepository.findByIdOrNull(any()) } returns Member(
                23L,
                "TestUser",
                true,
                BalanceLimit(60000, 100000, 40000),
                Wallet(60000)
            )
            Then("MonthlyExceedLimitException 예외를 던진다") {
                shouldThrow<MonthlyExceedLimitException> {
                    payingMemberService.createPayingMember(paymentRequestDto)
                }
            }
        }
    }
})