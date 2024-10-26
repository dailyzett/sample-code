package com.member.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberDao;
import com.common.dto.MemberDto;

public class MemberListCommand implements MemberCommand {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		
		
		int count = 0;
		MemberDao dao = MemberDao.getInstance();
		count = dao.getCount();
		
		ArrayList<MemberDto> dtos = new ArrayList<MemberDto>();
		
		if(count > 0) {
			dtos = dao.listAllMember(startRow, pageSize);
		}
		
		request.setAttribute("dtos", dtos);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		return 0;
	}

}
