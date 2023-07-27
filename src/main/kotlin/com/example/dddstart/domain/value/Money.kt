package com.example.dddstart.domain.value

data class Money(
    private val value: Int
) {
    operator fun plus(other: Money): Money {
        return Money(value + other.value)
    }

    operator fun times(other: Int): Money {
        return Money(value * other)
    }
}