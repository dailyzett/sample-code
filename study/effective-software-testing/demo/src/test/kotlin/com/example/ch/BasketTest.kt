package com.example.ch

import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal.valueOf

class BasketTest {
    private val basket = Basket()

    @Test
    fun addProduct() {
        basket.add(Product("TV", valueOf(10)), 2)
        basket.add(Product("Playstation", valueOf(100)), 1)

        assertThat(basket.totalValue).isEqualByComparingTo(valueOf(10 * 2 + 100 * 1))
    }

    @Test
    fun addSameProductTwice() {
        //given
        val p = Product("TV", valueOf(10))
        basket.add(p, 2)
        basket.add(p, 3)

        assertThat(basket.totalValue).isEqualTo(valueOf(10 * 5))
    }

    @Test
    fun removeProducts() {
        basket.add(Product("TV", valueOf(100)), 1)

        val p = Product("Playstation", valueOf(10))
        basket.add(p, 2)
        basket.remove(p)

        assertThat(basket.totalValue).isEqualByComparingTo(valueOf(100))
    }



}