package com.begin.four.one

class Button : Clickable, Focusable {
    override fun click() = println("I'm Button Implemented Clickable and Focusable")
    override fun showOff() {
        super<Focusable>.showOff()
        super<Clickable>.showOff()
    }
}