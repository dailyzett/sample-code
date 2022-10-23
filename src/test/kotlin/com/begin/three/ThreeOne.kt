package com.begin.three

import org.junit.jupiter.api.Test
import strings.UNIX_LINE_SEPARATOR
import strings.joinToString
import strings.performOperation
import strings.reportOperationCount

class ThreeOne {
    @Test
    fun test1() {
        val list = arrayListOf(1, 7, 53)
        val map = hashMapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
        val set = hashSetOf(1, 7, 53)

        println(set.javaClass)
    }

    @Test
    fun test2() {
        val strings = listOf("first", "second", "fourteenth")
        println(strings.last())
    }

    @Test
    fun test3() {
        val list = listOf(1, 2, 3)
        println(list)
    }

    @Test
    fun test4() {
        val list = listOf(1, 2, 3, 4)
        println(joinToString(list, ", ", "", ""))
        println(joinToString(list, postfix = ";", prefix = "# "))
    }

    @Test
    fun test5() {
        performOperation()
        reportOperationCount()
        println("Hello! $UNIX_LINE_SEPARATOR World")
    }


}