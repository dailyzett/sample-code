package com.jun.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.sql.Date

@Entity
@Table(name = "loans")
class Loans(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "loan_number")
    private var loanNumber: Int = 0,

    @Column(name = "customer_id")
    var customerId: Int = 0,

    @Column(name = "start_dt")
    var startDt: Date? = null,

    @Column(name = "loan_type")
    var loanType: String? = null,

    @Column(name = "total_loan")
    var totalLoan: Int = 0,

    @Column(name = "amount_paid")
    var amountPaid: Int = 0,

    @Column(name = "outstanding_amount")
    var outstandingAmount: Int = 0,

    @Column(name = "create_dt")
    var createDt: String? = null
)