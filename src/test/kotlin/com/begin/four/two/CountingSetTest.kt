package com.begin.four.two

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

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

}

class A {
    companion object {
        fun bar() {
            println("Hello Bar")
        }
    }
}