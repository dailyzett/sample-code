package com.example.dddstart.domain.value

import com.example.dddstart.order.command.domain.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MoneyTest {

    @Test
    fun `test money`() {
        val money = Money(1000)
        val money2 = Money(2000)

        assertThat(money + money2).isEqualTo(Money(3000))
        assertThat(money * 3).isEqualTo(Money(3000))
    }
}