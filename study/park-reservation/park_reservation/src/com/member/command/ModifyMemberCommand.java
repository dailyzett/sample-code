package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;
import com.common.dto.MemberDto;

public class ModifyMemberCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int r = 0;
		HttpSession session = request.getSession();
		
		MemberDto dto = new MemberDto
				.Builder()
				.username((String)session.getAttribute("sessionId"))
				.email(request.getParameter("email"))
				.password(request.getParameter("pw"))
				.phone1(request.getParameter("phone1"))
				.phone2(request.getParameter("phone2"))
				.phone3(request.getParameter("phone3"))
				.name(request.getParameter("name"))
				.build();
		
		MemberDao dao = MemberDao.getInstance();
		
		
		int res = dao.updateMember(dto);
		if(res > 0) {
			r = 1;
			request.setAttribute("modifyOk", true);
		}else {
			r = 2;
			request.setAttribute("modifyOk", false);
		}
		return r;
	}

}
