package com.jun.model

import jakarta.persistence.*

@Entity
@Table(name = "customer")
class Customer(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var email: String? = null,

    var pwd: String? = null,

    var role: String? = null,
)