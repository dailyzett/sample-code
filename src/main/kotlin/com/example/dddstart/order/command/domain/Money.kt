package com.example.dddstart.order.command.domain

data class Money(
    private val value: Int
) {
    operator fun plus(other: Money): Money {
        return Money(value + other.value)
    }

    operator fun times(other: Int): Money {
        return Money(value * other)
    }

    fun getValue(): Int {
        return value
    }
}