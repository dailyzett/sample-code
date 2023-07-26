package com.begin.four.one

interface State: java.io.Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}