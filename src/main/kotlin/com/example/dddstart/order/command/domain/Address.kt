package com.example.dddstart.order.command.domain

import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    private val address1: String = "",
    private val address2: String = "",
    private val zipcode: String = ""
)