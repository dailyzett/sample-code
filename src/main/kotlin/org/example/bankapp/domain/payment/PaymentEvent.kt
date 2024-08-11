package org.example.bankapp.domain.payment

import jakarta.persistence.*
import org.example.bankapp.domain.dto.PaymentRequestDto
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "T_PAYMENT_EVENT")
class PaymentEvent(
    @EmbeddedId
    val id: PaymentEventId,

    @Column(name = "is_payment_done")
    private var isPaymentDone: Boolean,

    @Embedded
    val payingMember: PayingMember,

    @Column(name = "created_dt")
    val createdDt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(id: PaymentEventId, paymentRequestDto: PaymentRequestDto) : this(
        id = id,
        isPaymentDone = false,
        payingMember = PayingMember(paymentRequestDto.payingMemberId),
        createdDt = LocalDateTime.now(),
    )

    fun changePaymentDoneState() {
        isPaymentDone = true
    }

    fun getIsPaymentDone(): Boolean = isPaymentDone
}

@Embeddable
data class PaymentEventId(
    val id: String
) : Serializable