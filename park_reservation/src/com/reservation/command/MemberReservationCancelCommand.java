package com.reservation.command;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;
import com.common.dao.MemberReservationDao;
import com.common.dao.ReservationDao;
import com.common.dto.MemberReservationDto;

public class MemberReservationCancelCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("rid");
		int rid = Integer.parseInt(id);
		int mFkId = 0;
		int mId = 0;
		int count = 0;
		String parkName = null;
		Date date = null;
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("sessionId");
		
		MemberReservationDao mRDao = new MemberReservationDao();
		ReservationDao rDao = new ReservationDao();
		MemberDao mDao = new MemberDao();
		
		MemberReservationDto reservationDto = new MemberReservationDto();
		
		mId = mDao.getM_id(username);
		mFkId = mRDao.findByRId(rid);
		count = mRDao.getReservationCount(rid);
		reservationDto = mRDao.findByOneMemberId(mId, rid);
		
		if(reservationDto.getParkName().equals("가야산")) {
			parkName = "kaya";
		}
		
		
		
		
		if(mId == mFkId) {
			rDao.addReservationCount(count, parkName, reservationDto.getReservationDate());
			mRDao.deleteReservation(rid);
			request.setAttribute("userCheck", true);
		}else {
			request.setAttribute("userCheck", false);
		}
		
		return 0;
	}

}
