package com.begin.four.two

class User(val name: String) {
    var address: String = "unspecified"
        set(value: String) {
            println("$value")
            field = value
        }
}