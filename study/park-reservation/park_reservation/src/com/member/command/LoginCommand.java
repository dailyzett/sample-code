package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;

public class LoginCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int res = 0;
		String id = request.getParameter("loginId");
		String pw = request.getParameter("loginPw");
		
		MemberDao dao = MemberDao.getInstance();
		int r = dao.loginMember(id, pw);
		
		if(r == MemberDao.MEMBER_LOGIN_SUCCESS) {
			HttpSession session = request.getSession();
			session.setAttribute("sessionId", id);
			res = 1;
			
		}else if(r == MemberDao.MEMBER_LOGIN_PW_NO_GOOD) {
			request.setAttribute("loginStatus", "pwfail");
			request.setAttribute("tempId", id);
			res = 2;
			
		}else if(r == MemberDao.MEMBER_LOGIN_IS_NOT) {
			request.setAttribute("loginStatus", "idfail");
			res = 2;
		}
		return res;
	}
}
