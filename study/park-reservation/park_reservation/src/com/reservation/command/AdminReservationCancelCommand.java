package com.reservation.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.MemberReservationDao;
import com.common.dao.ReservationDao;
import com.common.dto.MemberReservationDto;

public class AdminReservationCancelCommand implements ReservationCommand {
	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("rid");
		int rid = Integer.parseInt(id);
		int count = 0;
		int mFkId = 0;
		String parkName = null;
		
		
		
		MemberReservationDao mRDao = new MemberReservationDao();
		ReservationDao rDao = new ReservationDao();
		
		
		MemberReservationDto reservationDto = new MemberReservationDto();
		
		
		mFkId = mRDao.getMemberFkId(rid);
		count = mRDao.getReservationCount(rid);
		reservationDto = mRDao.findByOneMemberId(mFkId, rid);

		if(reservationDto.getParkName().equals("가야산")) {
			parkName = "kaya";
		}
		
		
		rDao.addReservationCount(count, parkName, reservationDto.getReservationDate());
		mRDao.deleteReservation(rid);
		
		
		return 0;
	}
}
