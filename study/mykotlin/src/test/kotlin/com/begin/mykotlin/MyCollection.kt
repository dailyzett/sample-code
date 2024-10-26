package com.begin.mykotlin

import org.junit.jupiter.api.Test
import strings.joinToString
import strings.lastChar

class MyCollection {

    @Test
    fun collectionTest() {
        val set = hashSetOf(1, 7, 53)
        println(set.javaClass)

        val strings = listOf("first", "second", "fourteenth")
        println(strings.last())
    }

    @Test
    fun joinToStringTest() {
        val list = listOf(1, 3, 5)
        println(joinToString(list))
    }

    @Test
    fun lastIndexTest() {
        println("Kotlin".lastChar())
    }

    @Test
    fun lastTest() {
        val list = arrayListOf(1, 2, 3)
        println(list.joinToString(" "))
        println(listOf("one", "two", "eight").joinToString(" "))
    }

    @Test
    fun viewTest() {
        val view: View = Button()
        view.click()
    }


    val String.lastChar: Char
        get() = get(length - 1)

    @Test
    fun extensionPropertyTest() {
        println("Kotlin".lastChar)
    }

    private fun hello(args: Array<String>) {
        val list = listOf("args: ", *args)
        println(list)
    }

    @Test
    fun mapTest() {
        val map = mapOf(1 to "one", 7 to "seven", 53 to "fifty-three")
        val (number, name) = 1 to "one"
        println("$number $name")
    }

    @Test
    fun forTest() {
        val collection = listOf("one", "three", "helloWorld")
        for ((index, element) in collection.withIndex()) {
            println("$index: $element")
        }
    }

    @Test
    fun stringTest() {
        println("12.345-6.A".split("\\.".toRegex()))
        println("12.345-6.A".split(".", "-"))
    }

    private fun parsePath(path: String) {
        val directory = path.substringBeforeLast("/")
        val fullName = path.substringAfterLast("/")
        val fileName = fullName.substringBeforeLast(".")
        val extension = fullName.substringAfterLast(".")

        println("Dir: $directory, fullName: $fullName, name: $fileName, ext: $extension")
    }

    @Test
    fun parsePathTest() {
        val path = "/Users/yole/kotlin-book/chapter.adoc"
        parsePath(path)
    }

    @Test
    fun test2() {
        val kotlinLogo =
                """| //
            .|//
            .|/\"""

        println(kotlinLogo.trimMargin("."))
    }


}