package com.member.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberDao;
import com.common.dto.MemberDto;

public class AdminMemberModifyProcessCommand implements MemberCommand {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tempId = request.getParameter("m_id");
		int res = 0;
		int m_id = Integer.parseInt(tempId);
		MemberDao dao = MemberDao.getInstance();
		String username = request.getParameter("username");
		
		MemberDto oldDto = dao.findByMemberId(m_id);
		
		
		MemberDto dto = new MemberDto
				.Builder()
				.username(username)
				.email(request.getParameter("email"))
				.password(request.getParameter("pw"))
				.phone1(request.getParameter("phone1"))
				.phone2(request.getParameter("phone2"))
				.phone3(request.getParameter("phone3"))
				.name(request.getParameter("name"))
				.build();
		
		
		res = dao.confirmUsername(username);
		
		if(!oldDto.getUsername().equals(dto.getUsername())) {
			if(res == MemberDao.MEMBER_EXISTENT) {
				request.setAttribute("memberEx", "yes");
				request.setAttribute("oldname", oldDto.getUsername());
				return res;
			}else {
				dao.updateAdminMember(dto, m_id);
			}
		}else {
			dao.updateAdminMember(dto, m_id);
		}
		return 0;
	}
}
