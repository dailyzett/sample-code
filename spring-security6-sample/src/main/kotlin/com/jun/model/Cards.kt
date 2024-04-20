package com.jun.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.sql.Date

@Entity
@Table(name = "cards")
class Cards(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "card_id")
    var cardId: Int = 0,

    @Column(name = "customer_id")
    var customerId: Int = 0,

    @Column(name = "card_number")
    var cardNumber: String? = null,

    @Column(name = "card_type")
    var cardType: String? = null,

    @Column(name = "total_limit")
    var totalLimit: Int = 0,

    @Column(name = "amount_used")
    var amountUsed: Int = 0,

    @Column(name = "available_amount")
    var availableAmount: Int = 0,

    @Column(name = "create_dt")
    var createDt: Date? = null,
) {
    override fun toString(): String {
        return "Cards(cardId=$cardId, customerId=$customerId, cardNumber=$cardNumber, cardType=$cardType, totalLimit=$totalLimit, amountUsed=$amountUsed, availableAmount=$availableAmount, createDt=$createDt)"
    }
}