package com.example.ch

import java.util.*

fun passed(grade: Float): Boolean {
    if (grade < 1.0 || grade > 10.0) {
        throw IllegalArgumentException("Grade must be between 1.0 and 10.0")
    }
    return grade >= 5.0
}

fun unique(date: IntArray): IntArray {
    val values = TreeSet<Int>()
    for (i in date.indices) {
        values.add(date[i])
    }

    val count = values.size
    val out = IntArray(count)

    val iterator = values.iterator()
    var i = 0
    while (iterator.hasNext()) {
        out[count - ++i] = iterator.next()
    }
    return out
}

/**
 * indexOf()
 * @Param array?: 대상을 검색할 배열
 * @param valueToFind: 찾고자 하는 값
 * @param startIndex: 검색을 시작할 인덱스
 */
fun indexOf(array: IntArray?, valueToFind: Int, startIndex: Int): Int {
    if (array == null) {
        return -1
    }

    var s2 = startIndex
    if (startIndex < 0) {
        s2 = 0
    }
    for (i in s2 until array.size) {
        if (valueToFind == array[i]) {
            return i
        }
    }
    return -1
}