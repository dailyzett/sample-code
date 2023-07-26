package com.begin.mykotlin

import com.begin.inter.Expr
import com.begin.inter.Num
import com.begin.inter.Sum
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.StringReader
import java.util.*

class Test2 {

    val binaryReps = TreeMap<Char, String>()
    val list = arrayListOf("10", "11", "1001")

    fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
    fun isNotDigit(c: Char) = c !in '0'..'9'
    fun recognize(c: Char) = when (c) {
        in '0'..'9' -> "It's a digit"
        in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
        else -> "I don't know..."
    }

    fun readNumber(reader: BufferedReader) {
        val number = try {
            Integer.parseInt(reader.readLine())
        } catch (e: java.lang.NumberFormatException) {
            null
        }
        println(number)
    }

    @Test
    fun readNumberTest() {
        val reader = BufferedReader(StringReader("Not a Number"))
        readNumber(reader)
    }


    @Test
    fun tryCatchTest() {
        val reader = BufferedReader(StringReader("239"))
        println(readNumber(reader))
    }


    @Test
    fun test1() {
        for ((index, element) in list.withIndex()) {
            println("$index: $element")
        }
    }

    @Test
    fun test0() {
        for (c in 'A'..'F') {
            val binary = Integer.toBinaryString(c.code)
            binaryReps[c] = binary
        }

        for ((letter, binary) in binaryReps) {
            println("$letter=$binary")
        }
    }

    fun eval(e: Expr): Int =
            when (e) {
                is Num ->
                    e.value
                is Sum ->
                    eval(e.right) + eval(e.left)
                else ->
                    throw IllegalArgumentException("Unknown expression")
            }

    fun evalWithLogging(e: Expr): Int =
            when (e) {
                is Num -> {
                    println("num: ${e.value}")
                    e.value
                }
                is Sum -> {
                    val left = evalWithLogging(e.left)
                    val right = evalWithLogging(e.right)
                    println("sum: $left + $right")
                    left + right
                }
                else -> throw IllegalArgumentException("Unknown expression")
            }

    fun fizzBuzz(i: Int) = when {
        i % 15 == 0 -> "FizzBuzz "
        i % 3 == 0 -> "Fizz "
        i % 5 == 0 -> "Buzz "
        else -> "$i "
    }
}
