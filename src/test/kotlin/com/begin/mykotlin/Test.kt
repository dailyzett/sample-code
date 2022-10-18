package com.begin.mykotlin

import com.begin.colors.Color
import com.begin.colors.Color.*
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun testA() {
        println(mix(BLUE, YELLOW))
    }
}


fun mix(c1: Color, c2: Color) =
    when(setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("dirty color")
    }


