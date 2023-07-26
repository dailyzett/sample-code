package com.begin.four.one

class ButtonByKotlin: View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) {
        super.restoreState(state)
    }
    inner class ButtonState : State {
        fun hello() {
            println("hi!")
            this@ButtonByKotlin
        }
    }
}