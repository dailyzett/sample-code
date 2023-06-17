package com.example.ch

import net.jqwik.api.*
import net.jqwik.api.constraints.FloatRange
import net.jqwik.api.constraints.IntRange
import net.jqwik.api.constraints.Size
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


/**
 * fail: 1.0 ~ 5.0 까지의 범위에 있는 모든 수에 대해 프로그램은 false 를 반환한다.
 * pass: 5.0 ~ 10.0 까지의 범위에 있는 모든 수에 대해 프로그램은 true 를 반환한다.
 * invalid: 유효하지 않은 등급에 대해, IllegalArgumentException 이 발생한다.
 */
class Ch5_1MethodKtTest {
    @Property
    fun fail(
        @ForAll
        @FloatRange(min = 1f, max = 5f, maxIncluded = false) grade: Float
    ) {
        assertThat(passed(grade)).isFalse()
    }

    @Property
    fun pass(@ForAll @FloatRange(min = 5f, max = 10f, maxIncluded = true) grade: Float) {
        assertThat(passed(grade)).isTrue()
    }


    @Property
    fun invalid(
        @ForAll("invalidGrades") grade: Float
    ) {
        assertThatThrownBy { passed(grade) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Provide
    fun invalidGrades(): Arbitrary<Float> {
        //oneOf 10 보다 큰 값을 생성하는 만큼 1보다 작은 값을 생성한다.
        return Arbitraries.oneOf(
            Arbitraries.floats().lessThan(1f),
            Arbitraries.floats().greaterThan(10f)
        )
    }

    @Test
    fun unique_test() {
        //given
        val data = intArrayOf(4, 4, 4, 5, 6, 10)
        //when
        val result = unique(data)
        //then
        result.forEach {
            print("$it ")
        }
    }

    @Property
    fun unique(
        @ForAll
        @Size(value = 100)
        numbers: List<@IntRange(min = 1, max = 20) Int>
    ) {
        val doubles: IntArray = convertListToArray(numbers)
        val result = unique(doubles)

        assertThat(result).contains(doubles.toTypedArray())
            .doesNotHaveDuplicates()
            .isSortedAccordingTo(Comparator.reverseOrder())
    }

    //use lambda
    private fun convertListToArray(numbers: List<Int>): IntArray {
        return numbers
            .stream()
            .mapToInt { x -> x }
            .toArray()
    }

    @Property
    fun `index of should find first value`(
        @ForAll @Size(value = 100) numbers: MutableList<@IntRange(min = -1000, max = 1000) Int>,
        @ForAll @IntRange(min = 1001, max = 2000) value: Int,
        @ForAll @IntRange(max = 99) indexToAddElement: Int,
        @ForAll @IntRange(max = 99) startIndex: Int
    ){
        numbers.add(indexToAddElement, value)
        val array = convertListToArray2(numbers)
        val expectedIndex: Int = if (indexToAddElement >= startIndex) indexToAddElement else -1
        println("${indexOf(array, value, startIndex)}, expected: $expectedIndex true: ${indexOf(array, value, startIndex) == expectedIndex}")
    }

    private fun convertListToArray2(numbers: List<Int>): IntArray {
        return numbers
            .stream()
            .mapToInt { x -> x }
            .toArray()
    }
}