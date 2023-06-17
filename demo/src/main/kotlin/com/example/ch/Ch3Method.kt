package com.example.ch

import java.lang.Character.isLetter

fun count(str: String?): Int {
    var words = 0
    var last = ' '

    //for loop
    //if words of sentence 's' or 'r', words++
    for (i in 0 until str!!.length) {
        if(!isLetter(str[i]) && (last == 's' || last == 'r')) {
            words++
        }

        last = str[i]
    }

    if (last == 's' || last == 'r') {
        words++
    }

    return words
}