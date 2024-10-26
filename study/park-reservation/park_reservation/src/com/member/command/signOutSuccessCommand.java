package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;

public class signOutSuccessCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("sessionId");
		MemberDao dao = MemberDao.getInstance();
		dao.deleteMember(id);
		
		return 0;
	}

}
