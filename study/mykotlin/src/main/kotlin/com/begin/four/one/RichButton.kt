package com.begin.four.one

open class RichButton : Clickable {
    fun disable() {}
    open fun animate() {}
    final override fun click() {}
}