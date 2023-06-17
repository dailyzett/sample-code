package com.example.ch

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Ch3_1MethodKtTest {
    @ParameterizedTest
    @MethodSource("generator")
    fun test(originalStr: String?, size: Int, padString: String?, expectedStr: String?) {
        Assertions.assertThat(leftPad(originalStr, size, padString))
            .isEqualTo(expectedStr)
    }

    private fun generator(): Stream<Arguments> {
        return Stream.of(
            of(null, 10, "-", null),
            of("", 5, "-", "-----"),
            of("abc", "-1", "-", "abc"),
            of("abc", 5, null, "  abc"),
            of("abc", 5, "", "  abc"),
            of("abc", 5, "-", "--abc"),
            of("abc", 3, "-", "abc"),
            of("abc", 0, "-", "abc"),
            of("abc", 2, "-", "abc"),

            of("abc", 5, "--", "--abc"),
            of("abc", 5, "---", "--abc"),
            of("abc", 5, "-", "--abc")
        )
    }

    @Test
    fun `same instance return`() {
       val str = "sameStr"
       assertThat(leftPad(str, 5, "-")).isSameAs(str)
    }
}