package com.example.dddstart.order.command.domain

data class Address(
    private val address1: String = "",
    private val address2: String = "",
    private val zipcode: String = ""
)