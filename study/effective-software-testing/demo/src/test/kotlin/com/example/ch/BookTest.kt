package com.example.ch

import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.Combinators
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Provide
import org.junit.jupiter.api.Assertions.*

class BookTest {

    @Property
    fun differentBooks(
        @ForAll("books") book: Book
    ) {
        println(book)
    }

    @Provide
    fun books(): Arbitrary<Book> {
        val titles = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(10).ofMaxLength(100)
        val authors = Arbitraries.strings().withCharRange('a', 'z').ofMinLength(5).ofMaxLength(21)
        val qtyOfPages = Arbitraries.integers().between(0, 450)

        return Combinators.combine(titles, authors, qtyOfPages)
            .`as` { title: String, author: String, pages: Int -> Book(title, author, pages) }
    }
}