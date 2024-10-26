package com.reservation.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberReservationDao;
import com.common.dto.MemberReservationDto;

public class MemberReservationSearchCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("search");
		int rId = Integer.parseInt(id);
		
		MemberReservationDao memberReservationDao = new MemberReservationDao();
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = memberReservationDao.getCountByRId(rId);
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();		
		
		
		dtos = memberReservationDao.findByRId(rId, startRow, pageSize);		
		
		
		request.setAttribute("dtos", dtos);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		return 0;
	}

}
