package com.jun.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "customer")
class Customer(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "customer_id") var id: Int = 0,

    val name: String? = null,

    private
    val email: String? = null,

    @Column(name = "mobile_number")
    val mobileNumber: String? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var pwd: String? = null,

    val role: String? = null,

    @Column(name = "create_dt")
    var createDt: String? = null,
)