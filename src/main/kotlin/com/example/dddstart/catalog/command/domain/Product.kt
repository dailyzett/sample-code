package com.example.dddstart.catalog.command.domain

import com.example.dddstart.common.jpa.MoneyConverter
import com.example.dddstart.order.command.domain.Money
import jakarta.persistence.Convert
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "product")
class Product {

    @EmbeddedId
    private val id: ProductId? = null

    private val name: String? = null

    @Convert(converter = MoneyConverter::class)
    private val price: Money? = null

    private val detail: String? = null
}
