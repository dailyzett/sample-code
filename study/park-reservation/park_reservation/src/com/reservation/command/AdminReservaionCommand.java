package com.reservation.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberReservationDao;
import com.common.dto.MemberReservationDto;

public class AdminReservaionCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		MemberReservationDao memberReservationDao = new MemberReservationDao();
		int pageSize = 7;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = memberReservationDao.getCountAll();
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();		
		
		
		if(count > 0) {
			dtos = memberReservationDao.findByAll(startRow, pageSize);
		}
		
		
		request.setAttribute("dtos", dtos);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		return 0;
	}
	
}
