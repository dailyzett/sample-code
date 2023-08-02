package com.example.dddstart.catalog.command.domain

import jakarta.persistence.Access
import jakarta.persistence.AccessType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
@Access(AccessType.FIELD)
data class ProductId(
    @Column(name = "product_id")
    private val id: String? = null
) : Serializable
