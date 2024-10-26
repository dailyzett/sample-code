package com.reservation.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;
import com.common.dao.MemberReservationDao;
import com.common.dto.MemberReservationDto;

public class MemberReservationHistoryCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		
		int startRow = (currentPage - 1) * pageSize + 1;
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("sessionId");
			
		MemberDao dao = new MemberDao();
		MemberReservationDao mRDao = new MemberReservationDao();
		int mId = -1;
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		
		mId = dao.getM_id(username);
		int count = mRDao.getCount(mId);
		
		if(count > 0) {
			dtos = mRDao.findByMemberId(mId, startRow, pageSize);
		}

		request.setAttribute("mrdtos", dtos);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		
		return 0;
	}

}
