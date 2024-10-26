package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberDao;
import com.common.dto.MemberDto;

public class AdminMemberModifyFormCommand implements MemberCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberDao dao = MemberDao.getInstance();
		
		
		String id = request.getParameter("username");
		int m_id = dao.getM_id(id);
		MemberDto dto = new MemberDto.Builder().build();
		dto = dao.listOneMember(id);
		

		request.setAttribute("adminMem", dto);
		request.setAttribute("m_id", m_id);
		return 0;
	}

}
