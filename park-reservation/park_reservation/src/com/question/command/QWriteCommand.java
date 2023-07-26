package com.question.command;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.BoardDao;
import com.common.dao.MemberDao;
import com.common.dto.BoardDto;
import com.common.dto.MemberDto;

public class QWriteCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("sessionId");
		
		MemberDao mDao = MemberDao.getInstance();
		MemberDto mDto = new MemberDto.Builder().build();
		BoardDao bDao = new BoardDao();
		BoardDto bDto = new BoardDto();
		
		mDto = mDao.listOneMember(id);
		int m_id = mDao.getM_id(id);
		
		bDto.setWriterId(mDto.getUsername());
		bDto.setMemberFkId(m_id);
		bDto.setWriterName(mDto.getName());
		bDto.setWriterEmail(mDto.getEmail());
		
		bDto.setTitle(request.getParameter("title"));
		bDto.setContent(request.getParameter("content"));
		bDto.setParkName(request.getParameter("park"));
		
		bDto.setWriteDate();
		
		if("TRUE".equals(request.getAttribute("TOKEN_SAVE_CHECK"))) {
			bDao.writeBoard(bDto);
		}else {
			request.setAttribute("doublesubmit", true);
		}
		return 0;
	}

}
