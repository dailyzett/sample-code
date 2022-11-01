package com.begin.six

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class AddressKtTest {

    @Test
    fun countryName() {
        val person = Person("Dmitry", null)
        println(person.countryName())
    }

    @Test
    fun printShippingLabel() {
        val address = Address("Elsestr. 47", 80687, "Munich", "Germany")
        val jetbrains = Company("JetBrains", address)
        val person = Person("Dmitry", jetbrains)

        printShippingLabel(person)
    }
}