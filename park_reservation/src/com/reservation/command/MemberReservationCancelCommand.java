package com.reservation.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberReservationDao;

public class MemberReservationCancelCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("rid");
		int rid = Integer.parseInt(id);
		
		
		MemberReservationDao mRDao = new MemberReservationDao();
		mRDao.deleteReservation(rid);
		
		
		return 0;
	}

}
