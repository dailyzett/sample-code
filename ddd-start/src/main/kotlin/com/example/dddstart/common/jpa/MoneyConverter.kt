package com.example.dddstart.common.jpa

import com.example.dddstart.order.command.domain.Money
import jakarta.persistence.AttributeConverter

class MoneyConverter : AttributeConverter<Money, Int> {
    override fun convertToDatabaseColumn(money: Money?): Int? {
        return money?.getValue()
    }

    override fun convertToEntityAttribute(value: Int?): Money? {
        return if (value == null) null else Money(value)
    }
}