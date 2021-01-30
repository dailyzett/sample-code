package com.question.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.BoardDao;
import com.common.dto.BoardDto;

public class QSearchCommand implements QCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		String parkName = request.getParameter("park");
		String condition = request.getParameter("condition");
		String title = request.getParameter("search");
		
		ArrayList<BoardDto> dtos = new ArrayList<BoardDto>();
		
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		
		
		int count = 0;
		BoardDao dao = new BoardDao();
		count = dao.getTitleSearchCount(title);
		
		int count2 = 0;
		count2 = dao.getSubSearchCount(title);
		
		if(condition.equals("제목")) {
			dtos = dao.searchQBoardTitle(startRow, pageSize, parkName, title);
			request.setAttribute("count", count);
			request.setAttribute("qdtos", dtos);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageSize", pageSize);
			return 1;
		}else if(condition.equals("작성자")) {
			dtos = dao.searchQBoardSub(startRow, pageSize, parkName, title);
			request.setAttribute("count2", count2);
			request.setAttribute("qdtos", dtos);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("pageSize", pageSize);
			return 2;
		}else {
			return 0;
		}
		
		
		
		
		
	
		
	}

}
