package com.question.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface QCommand {
	public int execute(HttpServletRequest request, HttpServletResponse response);

}
