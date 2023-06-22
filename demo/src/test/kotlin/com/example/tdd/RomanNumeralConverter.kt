package com.example.tdd

class RomanNumeralConverter {
    fun convert(romanNumber: String): Int {
        var finalNumber = 0
        var lastNeighbor = 0

        for (i in romanNumber.length - 1 downTo 0) {
            val current = table()[romanNumber[i].toString()] ?: 0

            var multiplier = 1
            if (current < lastNeighbor) {
                multiplier = -1
            }

            val currentNumeralToBeAdded = current * multiplier
            finalNumber += currentNumeralToBeAdded
            lastNeighbor = current
        }
        return finalNumber
    }

    private fun table(): Map<String, Int> {
        return mapOf(
            "I" to 1,
            "V" to 5,
            "X" to 10,
            "L" to 50,
            "C" to 100,
            "D" to 500,
            "M" to 1000
        )
    }
}
