package com.member.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberCommand {
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
