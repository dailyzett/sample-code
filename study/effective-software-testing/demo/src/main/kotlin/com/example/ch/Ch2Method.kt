package com.example.ch

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.max

val EMPTY_STRING_ARRAY = listOf<String>()
fun subStringBetween(str: String?, open: String?, close: String?): List<String>? {
    if (str == null || open == null || close == null) {
        return null
    }

    val strLen = str.length
    if (strLen == 0) {
        return EMPTY_STRING_ARRAY
    }

    val closeLen = close.length
    val openLen = open.length
    var pos = 0
    val list = mutableListOf<String>()

    while (pos < strLen - closeLen) {
        var start = str.indexOf(open, pos)
        if (start < 0) {
            break
        }

        start += openLen
        val end = str.indexOf(close, start)
        if (end < 0) {
            break
        }

        list.add(str.substring(start, end))
        pos = end + closeLen
    }

    if (list.isEmpty()) {
        return null
    }

    return list.toTypedArray().toList()
}

fun add(left: MutableList<Int>?, right: MutableList<Int>?): List<Int>? {
    if (left == null || right == null) {
        return null;
    }

    left.reverse()
    right.reverse()

    val result = LinkedList<Int>()
    var carry = 0

    //for loop. start from 0 to max(left.size, right.size) and i++
    for (i in 0 until max(left.size, right.size)) {
        val leftDigit = if (left.size > i) left[i] else 0
        val rightDigit = if (right.size > i) right[i] else 0

        if(leftDigit < 0 || leftDigit > 9 || rightDigit < 0 || rightDigit > 9) {
            throw IllegalArgumentException()
        }

        val sum = leftDigit + rightDigit + carry
        result.addFirst(sum % 10)
        carry = sum / 10
    }
    if (carry > 0) {
        result.addFirst(carry)
    }

    while(result.size > 1 && result[0] == 0) {
        result.removeAt(0)
    }
    return result
}