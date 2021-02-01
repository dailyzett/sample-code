package com.reservation.command;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.ReservationDao;

public class SetReservationStatusCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		String inputDate = request.getParameter("inputDate");
		String inputCount = request.getParameter("peopleCount");
		String inputPrice = request.getParameter("inputPrice");
		String parkName = request.getParameter("pn");
		
		Date commandDate = Date.valueOf(inputDate);
		int re_people = Integer.parseInt(inputCount);
		
		
		ReservationDao rDao = new ReservationDao();
		rDao.updateParkInfo(commandDate, parkName, re_people);
		
		
		return 0;
		}
	
}
