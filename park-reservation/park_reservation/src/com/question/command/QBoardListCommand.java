package com.question.command;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.BoardDao;
import com.common.dao.MemberDao;
import com.common.dto.BoardDto;
import com.common.dto.MemberDto;

public class QBoardListCommand implements QCommand {

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		
		
		int count = 0;
		BoardDao dao = new BoardDao();
		count = dao.getCount();
		
		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();
		
		if(count > 0) {
			dtos = dao.findAllQBoard(startRow, pageSize);
		}
		
		request.setAttribute("qdtos", dtos);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		
		return 0;
	}
	
}
