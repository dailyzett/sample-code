package com.jun.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Accounts(

    @Column(name = "customer_id")
    var customerId: Int = 0,

    @Id
    @Column(name = "account_number")
    var accountNumber: Long = 0,

    @Column(name = "account_type")
    var accountType: String? = null,

    @Column(name = "branch_address")
    var branchAddress: String? = null,

    @Column(name = "create_dt")
    var createDt: String? = null,
) {
}