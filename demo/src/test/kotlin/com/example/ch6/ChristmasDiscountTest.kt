package com.example.ch6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDate

class ChristmasDiscountTest {

    private val clock = mock(Clock::class.java)
    private val christmasDiscount = ChristmasDiscount(clock)

    @Test
    fun `applyDiscount, when today is christmas`() {
        //given
        `when`(clock.getLocalDateNow()).thenReturn(LocalDate.of(2020, 12, 25))
        //when
        val result = christmasDiscount.applyDiscount(amount = 100.0)
        //then
        assertThat(result).isEqualTo(100.0 - (100.0 * 0.15))
    }

    @Test
    fun `applyDiscount, when today is not christmas`() {
        //given
        `when`(clock.getLocalDateNow()).thenReturn(LocalDate.of(2020, 12, 24))
        //when
        val result = christmasDiscount.applyDiscount(amount = 100.0)
        //then
        assertThat(result).isEqualTo(100.0)
    }

    @Test
    fun `applyDiscount, don't use dependency`() {
        //given

        //when
        //then
    }
}
