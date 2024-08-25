package org.example.bankapp.domain.member

import jakarta.persistence.*
import org.example.bankapp.common.exception.*
import java.time.LocalDateTime

@Entity
@Table(name = "members")
class Members(
    @Id
    @Column(name = "id")
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

    fun subtractMemberBalanceInfo(amount: Int) {
        this.wallet = wallet.copy(currentBalance = this.wallet.currentBalance - amount)
        this.balanceLimit = balanceLimit.copy(
            dailyPaymentLimit = this.balanceLimit.dailyPaymentLimit - amount,
            monthlyPaymentLimit = this.balanceLimit.monthlyPaymentLimit - amount,
        )
    }

    fun addMemberBalanceInfo(amount: Int) {
        addMemberWalletBalance(amount)
        this.balanceLimit = balanceLimit.copy(
            dailyPaymentLimit = this.balanceLimit.dailyPaymentLimit + amount,
            monthlyPaymentLimit = this.balanceLimit.monthlyPaymentLimit + amount,
        )
    }

    fun addMemberWalletBalance(amount: Int): Int {
        this.wallet = wallet.copy(currentBalance = this.wallet.currentBalance + amount)
        return this.wallet.currentBalance
    }

    fun subtractMemberWalletBalance(amount: Int): Int {
        this.wallet = wallet.copy(currentBalance = this.wallet.currentBalance - amount)
        return this.wallet.currentBalance
    }

    fun getBalanceLimit(): BalanceLimit = balanceLimit
    fun getWallet(): Wallet = wallet
}