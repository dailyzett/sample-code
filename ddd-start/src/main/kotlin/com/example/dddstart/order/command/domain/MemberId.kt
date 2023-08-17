package com.example.dddstart.order.command.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class MemberId(
    @Column(name = "member_id")
    private val id: String
) {
}