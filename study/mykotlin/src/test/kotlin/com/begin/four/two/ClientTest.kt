package com.begin.four.two

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ClientTest {
    @Test
    fun test() {
        val client1 = Client("오현석", 22)
        val client2 = Client("오현석", 22)
        println(client1 == client2)
    }

    @Test
    fun hashCodeTest() {
        val processed = hashSetOf(Client("오현석", 4122))
        println(processed.contains(Client("오현석", 4122)))
    }

    @Test
    fun copyTest() {
        val lee = Client("Lee", 322)
        println(lee.copy(postalCode = 4000))
    }
}