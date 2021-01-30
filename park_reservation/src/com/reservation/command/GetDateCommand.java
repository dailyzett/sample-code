package com.reservation.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.dao.ReservationDao;
import com.common.dto.ReservationDto;


public class GetDateCommand implements ReservationCommand{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response) {
		
		ReservationDao rDao = new ReservationDao();
		ArrayList<ReservationDto> dtos = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
	
		Date start = new Date(cal.getTimeInMillis());
		
		cal.add(Calendar.DATE, 15);
		Date end = new Date(cal.getTimeInMillis());
		
		dtos = rDao.getDates(start, end);
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> sArr = new ArrayList<>();
				
				
		for(int i = 0; i < dtos.size(); i++) {
			String str = transFormat.format(dtos.get(i).getReserveDate());
			String result = str.substring(str.length()-2, str.length());
			sArr.add(result);
		}
		
		String[] arr = null;
		ArrayList<String> months = new ArrayList<String>();
		for(int i = 0; i < dtos.size(); i++) {
			String str = transFormat.format(dtos.get(i).getReserveDate());
			arr = str.split("-");
			months.add(arr[1]);
		}
		
		int mCount1 = 0;
		int mCount2 = 0;
		String beforeMonth = "";
		String afterMonth = "";
		for(int i = 0; i < months.size(); i++) {
			if(months.get(0).equals(months.get(i))) {
				beforeMonth = months.get(i);
				mCount1++;
			}else {
				afterMonth = months.get(i);
				mCount2++;
			}
		}


		request.setAttribute("dates", sArr);
		request.setAttribute("dtos", dtos);
		request.setAttribute("mCount1", mCount1);
		request.setAttribute("mCount2", mCount2);
		request.setAttribute("beforeMonth", beforeMonth);
		request.setAttribute("afterMonth", afterMonth);
		return 0;
		
	
	}
	
}
