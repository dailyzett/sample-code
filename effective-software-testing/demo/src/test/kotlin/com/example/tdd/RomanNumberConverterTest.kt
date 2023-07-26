package com.example.tdd

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class RomanNumberConverterTest {
    @Test
    fun `should understand symbolI`() {
        val roman = RomanNumeralConverter()
        val convert: Int = roman.convert("I")
        assertThat(convert).isEqualTo(1)
    }

    @Test
    fun `should understand symbol V`() {
        //given
        val roman = RomanNumeralConverter()
        //when
        val convert: Int = roman.convert("V")
        //then
        assertThat(convert).isEqualTo(5)
    }

    @ParameterizedTest
    @CsvSource(
        "I, 1",
        "V, 5",
        "X, 10",
        "L, 50",
        "C, 100",
        "D, 500",
        "M, 1000"
    )
    fun `should understand one char numbers`(romanNumeral: String, expectedNumberAfterConversion: Int) {
        val roman = RomanNumeralConverter()
        val convertNumber = roman.convert(romanNumeral)
        assertThat(convertNumber).isEqualTo(expectedNumberAfterConversion)
    }

    @Test
    fun `should Understand Multiple Char Numbers`() {
        //given
        val roman = RomanNumeralConverter()
        //when
        val convert = roman.convert("II")
        //then
        assertThat(convert).isEqualTo(2)
    }

    @ParameterizedTest
    @CsvSource(
        "II, 2",
        "III, 3",
        "VI, 6",
        "XVIII, 18",
        "XXIII, 23",
        "DCCLSVI, 776"
    )
    fun `should Understand Multiple Char Number`(romanNumeral: String, expectedNumberAfterConversion: Int) {
        val roman = RomanNumeralConverter()
        val convert = roman.convert(romanNumeral)
        assertThat(convert).isEqualTo(expectedNumberAfterConversion)
    }

    @ParameterizedTest
    @CsvSource(
        "IV, 4",
        "XIV, 14",
        "XL, 40",
        "XLI, 41",
        "CCXCIV, 294",
    )
    fun `should Understand Subtractive Notation`(romanNumeral: String, expectedNumberAfterConversion: Int) {
        val roman = RomanNumeralConverter()
        val convert = roman.convert(romanNumeral)
        assertThat(convert).isEqualTo(expectedNumberAfterConversion)
    }
}