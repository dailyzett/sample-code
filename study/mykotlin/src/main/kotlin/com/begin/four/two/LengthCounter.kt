package com.begin.four.two

class LengthCounter(var paramCounter: Int) {
    fun addWord(word: String) {
        paramCounter += word.length
    }
}