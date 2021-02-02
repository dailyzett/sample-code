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
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("sessionId");
			
		MemberDao dao = new MemberDao();
		MemberReservationDao mRDao = new MemberReservationDao();
		int mId = -1;
		ArrayList<MemberReservationDto> dtos = new ArrayList<MemberReservationDto>();
		
		mId = dao.getM_id(username);		
		dtos = mRDao.findByMemberId(mId);
		
		

		request.setAttribute("mrdtos", dtos);
		
		return 0;
	}

}
