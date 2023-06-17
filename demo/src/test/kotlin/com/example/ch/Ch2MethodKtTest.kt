package com.example.ch

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.*
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Ch2MethodKtTest {
    @Test
    fun simpleCase() {
        //given
        val str = "abcd"
        val open = "a"
        val close = "d"

        //when
        val result = subStringBetween(str, open, close)

        //then
        assertThat(result).isEqualTo(listOf("bc"))
    }

    @Test
    fun simpleCase_2() {
        //given
        val str = "abcdabcdab"
        val open = "a"
        val close = "d"

        //when
        val result = subStringBetween(str, open, close)

        //then
        assertThat(result).isEqualTo(listOf("bc", "bc"))
    }

    @Test
    fun simpleCase_3() {
        //given
        val str = "abcdabcdab"
        val open = "ab"
        val close = "d"
        //when
        val result = subStringBetween(str, open, close)
        //then
        assertThat(result).isEqualTo(listOf("c", "c"))
    }

    @Test
    fun strIsNullOrEmpty() {
        assertThat(subStringBetween(null, "a", "b")).isNull()
        assertThat(subStringBetween("", "a", "b")).isEqualTo(listOf<String>())
    }

    @Test
    fun strOfLength1() {
        assertThat(subStringBetween("a", "a", "b")).isEqualTo(null)
        assertThat(subStringBetween("a", "b", "a")).isEqualTo(null)
    }

    @Test
    fun openAndCloseOfLength1() {
        assertThat(subStringBetween("abc", "a", "c")).isEqualTo(listOf("b"))
        assertThat(subStringBetween("abcabyt byrc", "a", "c")).isEqualTo(listOf("b", "byt byr"))
    }

    @Test
    fun openAndCloseTagsOfDifferentSizes() {
        assertThat(subStringBetween("a abb ddc ca abbcc", "a a", "c c"))
            .isEqualTo(listOf("bb dd"))
    }

    @ParameterizedTest
    @MethodSource("testCases")
    fun `should return correct result`(left: MutableList<Int>?, right: MutableList<Int>?, expected: MutableList<Int>?) {
        assertThat(add(left, right)).isEqualTo(expected)
    }

    private fun testCases(): Stream<Arguments> {
        return Stream.of(
            of(null, numbers(7, 2), null), // T1
            of(numbers(), numbers(7,2), numbers(7, 2)), // T2
            of(numbers(9,8), null, null), //T3
            of(numbers(9,8), numbers(), numbers(9,8)), //T4

            of(numbers(1), numbers(2), numbers(3)), //T5
            of(numbers(9), numbers(2), numbers(1, 1)), //T6

            of(numbers(0,0,1,2), numbers(0,2,3), numbers(3,5))
        )
    }

    private fun numbers(vararg nums: Int?): List<Int> {
        return nums.filterNotNull()
    }

    @ParameterizedTest
    @MethodSource("digitOutOfRange")
    fun `should Throw Exception Method When Digits Are Out Of Range`(left: MutableList<Int>?, right: MutableList<Int>?) {
        assertThatThrownBy { add(left, right) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    private fun digitOutOfRange(): Stream<Arguments> {
        return Stream.of(
            of(numbers(1, -1, 1), numbers(1)),
        )
    }
}