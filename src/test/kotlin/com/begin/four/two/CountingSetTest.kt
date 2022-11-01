package com.begin.four.two

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

internal class CountingSetTest {
    @Test
    fun test() {
        val cSet = CountingSet<Int>()
        cSet.addAll(listOf(1, 2, 3, 4))
        println("${cSet.objectsAdded} objects were added, ${cSet.size} remain")
    }

    @Test
    fun test2() {
        val result = CaseInsensitiveFileComparator.compare(
                File("/User"), File("/user")
        )
        Assertions.assertThat(result).isEqualTo(0)
    }

    @Test
    fun test3() {
        val files = listOf(File("/Z"), File("/a"))
        println(files.sortedWith(CaseInsensitiveFileComparator))
    }

    @Test
    fun test4() {
        val persons = listOf(Person("Bob"), Person("Hello"), Person("Alice"))
        println(persons.sortedWith(Person.nameComparator))
    }

    @Test
    fun test5() {
        A.bar()
    }

    private fun printMessagesWithPrefix(messages: Collection<String>, prefix: String) {
        messages.forEach { println("$prefix $it") }
    }

    @Test
    fun test6() {
        val errors = listOf("403 Forbidden", "404 Not Found")
        printMessagesWithPrefix(errors, "Error: ")
    }

    private fun printProblemCounts(responses: Collection<String>) {
        var clientErrors = 0
        var serverErrors = 0
        responses.forEach {
            if (it.startsWith("4")) {
                clientErrors++
            } else if (it.startsWith("5")) {
                serverErrors++
            }
        }
        println("$clientErrors client errors, $serverErrors server errors")
    }

    @Test
    fun test7() {
        val responses = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
        printProblemCounts(responses)
    }

    private fun salute() = println("Salute!")

    @Test
    fun test8() {
        run(::salute)
    }

    @Test
    fun test9() {
        val list = listOf(1, 2, 3, 4)
        println(list.filter { it % 2 == 0 })
    }

    @Test
    fun test10() {
        val list = listOf(1, 2, 3, 4)
        println(list.map { it * it })
    }

    @Test
    fun test11() {
        val people = listOf(Person3("Bob", 33), Person3("Alice", 29), Person3("Hello", 99))
        println(people.filter { it.age > 30 }.map { it.age })
        val numbers = mapOf(0 to "zero", 1 to "one")
        println(numbers.mapValues { it.value.uppercase(Locale.getDefault()) })
    }

    @Test
    fun test12() {
        val canBeInClub27 = {p: Person3 -> p.age <= 27}
        val people = listOf(Person3("Alice", 27), Person3("Bob", 31))
        println(people.all(canBeInClub27))
    }

    private fun alphabet() = buildString {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\nNow I know the alphabet!")
    }


    @Test
    fun alphaTest() {
        println(alphabet())
    }

    @Test
    fun test13() {

    }

    private fun printAllCaps(s: String?) {
        val allCaps: String? = s?.uppercase(Locale.getDefault())
    }

    @Test
    fun test14() {
        val ceo = Employee("Da Boss", null)
        val developer = Employee("Bob Smith", ceo)
        println(managerName(developer))
        println(managerName(ceo))
    }
}

class A {
    companion object {
        fun bar() {
            println("Hello Bar")
        }
    }
}