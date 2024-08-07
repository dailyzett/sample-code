package org.example.bankapp.domain.member

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class MemberId(
    @Column(name = "member_id")
    val id: Long
) : Serializable
