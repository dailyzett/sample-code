package com.begin.three

import com.begin.four.one.Button
import com.begin.four.two.*
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

    @Test
    fun test6() {
        Button().click()
        Button().showOff()
    }

    @Test
    fun test7() {
        val button = Button()
        button.showOff()
        button.setFocus(true)
        button.click()
    }

    @Test
    fun sealedClassTest() {
        println(eval(Expr.Sum(Expr.Num(3), Expr.Num(5))))
    }

    @Test
    fun test10() {
        val user = User("Alice")
        user.address = "kotlin@naver.com"
    }

    @Test
    fun test11() {
        val lengthCounter = LengthCounter(3)
        lengthCounter.addWord("Hello!")
        println(lengthCounter.paramCounter)
    }

    @Test
    fun test12() {
        println(Client("Hello", 33))
    }
}