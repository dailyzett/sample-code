package com.begin.four.one;


import org.jetbrains.annotations.NotNull;

public class ButtonByJava implements View {

	@NotNull
	@Override
	public State getCurrentState() {
		return new ButtonState();
	}

	@Override
	public void restoreState(@NotNull State state) {
		View.super.restoreState(state);
	}

	public static class ButtonState implements State {}
}
