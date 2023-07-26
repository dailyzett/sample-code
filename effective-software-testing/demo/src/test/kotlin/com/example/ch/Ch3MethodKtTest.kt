package com.example.ch

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Ch3MethodKtTest {
    @Test
    fun `two Words Ending With S`() {
        assertThat(count("dogs cats")).isEqualTo(2)
    }

    @Test
    fun `no words At All`() {
        assertThat(count("dog cat")).isEqualTo(0)
    }

    @Test
    fun `words that end in R`() {
        val words = count("car bar")
        assertThat(words).isEqualTo(2)
    }
}