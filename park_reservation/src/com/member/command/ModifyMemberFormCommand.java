package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;
import com.common.dto.MemberDto;

public class ModifyMemberFormCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int r = 0;
		HttpSession session = request.getSession();
		MemberDto dto = new MemberDto.Builder().build();
		String id = (String) session.getAttribute("sessionId");
		
	
		MemberDao dao = MemberDao.getInstance();
		dto = dao.listOneMember(id);
		
		request.setAttribute("member", dto);
		return r;
	}
}
