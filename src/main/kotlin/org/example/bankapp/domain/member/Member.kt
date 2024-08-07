package org.example.bankapp.domain.member

import jakarta.persistence.*
import org.example.bankapp.common.exception.*
import java.time.LocalDateTime

@Entity
@Table(name = "T_MEMBER")
class Member(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "user_name")
    val userName: String,

    @Column(name = "is_registered")
    val isRegistered: Boolean,

    @Embedded
    private var balanceLimit: BalanceLimit,

    @Embedded
    private var wallet: Wallet,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_dt")
    val updatedDt: LocalDateTime? = null
) {
    fun isMemberRegistered(): Boolean {
        if (isRegistered) return true
        else throw MemberNotRegisteredException("")
    }

    fun isBalanceSufficient(amount: Int): Boolean {
        if (wallet.currentBalance < amount) throw InSufficientBalanceException("")
        return true
    }

    fun isBalanceLimitNotExceeded(amount: Int): Boolean {
        when {
            amount > balanceLimit.singlePaymentLimit -> throw SingleExceedLimitException("")
            amount > balanceLimit.dailyPaymentLimit -> throw DailyExceedLimitException("")
            amount > balanceLimit.monthlyPaymentLimit -> throw MonthlyExceedLimitException("")
        }
        return true
    }

    fun updateMemberBalanceInfo(amount: Int) {
        this.wallet = wallet.copy(currentBalance = this.wallet.currentBalance - amount)
        this.balanceLimit = balanceLimit.copy(
            dailyPaymentLimit = this.balanceLimit.dailyPaymentLimit - amount,
            monthlyPaymentLimit = this.balanceLimit.monthlyPaymentLimit - amount,
        )
    }

    fun getBalanceLimit(): BalanceLimit = balanceLimit
    fun getWallet(): Wallet = wallet
}