package com.reservation.command;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.common.dao.MemberDao;
import com.common.dao.MemberReservationDao;
import com.common.dao.ReservationDao;

public class SetReservationStatusCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();

		String mUsername = (String)session.getAttribute("sessionId");
		String inputDate = request.getParameter("inputDate");
		String inputCount = request.getParameter("peopleCount");
		String inputPrice = request.getParameter("inputPrice");
		String parkName = request.getParameter("pn");
		
		int price = Integer.parseInt(inputPrice);
		int mId = -1;
		int status = 1; // 예약을 했을 때 status는 1, 예약을 취소했을 때 status는 0
		int re_people = Integer.parseInt(inputCount); // 예약한 사람 인원
		int count = 0;
		Date commandDate = Date.valueOf(inputDate);
		
		ReservationDao rDao = new ReservationDao();
		MemberReservationDao mDao = new MemberReservationDao();
		MemberDao dao = new MemberDao();
		
		mId = dao.getM_id(mUsername); // 회원의 오라클 시퀀스 넘버를 찾는다
		count = rDao.checkEnableReservation(commandDate);
		
		
		if("TRUE".equals(request.getAttribute("TOKEN_SAVE_CHECK"))) {
			if(count > 0) {
				if(count - re_people >= 0) {
					rDao.updateParkInfo(commandDate, parkName, re_people); // 공원 예약 정보 업데이트
					mDao.setMemberReservation(mId, commandDate, status, price, re_people, parkName); // 해당 회원 예약 정보 업데이트
					request.setAttribute("reservationCheck", true);
					request.setAttribute("overMax", false);
				}
				else {
					request.setAttribute("overMax", true);
				}
			}else {
				request.setAttribute("reservationCheck", false);
			}
		}else {
			request.setAttribute("doublesubmit", true);
		}
		
		
		
		if(parkName.equals("kaya")) {
			return ReservationDao.KAYA;
		}else if(parkName.equals("kelong")) {
			return ReservationDao.GYERYOUNG;
		}else if(parkName.equals("naejang")) {
			return ReservationDao.NAEJANG;
		}else {
			return ReservationDao.SEORAK;
		}
		
	}
	
}
