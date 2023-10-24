package com.jun.model

import org.junit.jupiter.api.Test

class LoansTest {
    @Test
    fun loanTest() {
        var remainingLoanAmount = 20_106_591  // 대출 원금
        val annualInterestRate = 0.0584  // 연 이자율
        val monthlyPayment = 1_900_000  // 매달 지불 금액

        var totalPayment = 0
        
        for (month in 1..12) {
            // 매달 이자 계산
            val monthlyInterest = remainingLoanAmount * (annualInterestRate / 12)
            // 이자를 제외한 원금 상환액 계산
            val principalRepayment = monthlyPayment - monthlyInterest
            // 남은 대출금에서 원금 상환액 차감
            remainingLoanAmount -= principalRepayment.toInt()

            // 결과 출력
            println(
                """Month $month: 이자율 1 = ${monthlyInterest.format()}원 대출 금액 = ${
                    remainingLoanAmount.toDouble().format()
                }원"""
            )
        }
    }

    private fun Double.format(): String = String.format("%,.2f", this)
}