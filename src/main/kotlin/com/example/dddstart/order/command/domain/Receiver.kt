package com.example.dddstart.order.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Receiver(
    @Column(name = "receiver_name")
    private val name: String = "",

    @Column(name = "receiver_phone")
    private val phoneNumber: String = ""
)