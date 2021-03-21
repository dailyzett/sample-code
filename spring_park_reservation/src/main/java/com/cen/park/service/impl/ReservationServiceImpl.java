package com.cen.park.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cen.park.dao.ReservationDao;
import com.cen.park.service.ReservationService;
import com.cen.park.vo.ParkVo;
import com.cen.park.vo.ReservationVo;

import lombok.RequiredArgsConstructor;

@Service("reservationService")
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationDao reservationDao;

	@Override
	public void getParkInfo(String park_name, Model model) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));

		Date start = new Date(cal.getTimeInMillis());

		cal.add(Calendar.DATE, 15);
		Date end = new Date(cal.getTimeInMillis());

		List<ParkVo> parkVo = reservationDao.findParkInfo(start, end, park_name);
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String> sArr = new ArrayList<>();

		for (int i = 0; i < parkVo.size(); i++) {
			String str = transFormat.format(parkVo.get(i).getReservation_date());
			String result = str.substring(str.length() - 2, str.length());
			sArr.add(result);
		}

		String[] arr = null;
		ArrayList<String> months = new ArrayList<String>();
		for (int i = 0; i < parkVo.size(); i++) {
			String str = transFormat.format(parkVo.get(i).getReservation_date());
			arr = str.split("-");
			months.add(arr[1]);
		}

		int mCount1 = 0;
		int mCount2 = 0;
		String beforeMonth = "";
		String afterMonth = "";
		for (int i = 0; i < months.size(); i++) {
			if (months.get(0).equals(months.get(i))) {
				beforeMonth = months.get(i);
				mCount1++;
			} else {
				afterMonth = months.get(i);
				mCount2++;
			}
		}

		model.addAttribute("dates", sArr);
		model.addAttribute("dtos", parkVo);
		model.addAttribute("mCount1", mCount1);
		model.addAttribute("mCount2", mCount2);
		model.addAttribute("beforeMonth", beforeMonth);
		model.addAttribute("afterMonth", afterMonth);
	}

	@Override
	public void setReservationStatus(Model model, HttpSession session) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String inputDate = request.getParameter("inputDate");
		String inputCount = request.getParameter("peopleCount");
		String inputPrice = request.getParameter("inputPrice");
		String parkName = request.getParameter("pn");
		

		int price = Integer.parseInt(inputPrice);
		int mId = (int) session.getAttribute("sessionM_id");
		int status = 1; // 예약을 했을 때 status는 1, 예약을 취소했을 때 status는 0
		int re_people = Integer.parseInt(inputCount); // 예약한 사람 인원
		int count = 0;
		Date commandDate = Date.valueOf(inputDate.trim());

		// 회원의 오라클 시퀀스 넘버를 찾는다
		count = reservationDao.getEnablePeopleCount(commandDate, parkName);

		ReservationVo vo = ReservationVo.builder().m_id_fk(mId).status(status).park_name(parkName).count(re_people).price(price).reservation_date(commandDate).build();

		if (count > 0) {
			if (count - re_people >= 0) {
				reservationDao.updateParkInfo(re_people, parkName, commandDate);
				reservationDao.insertReservationInfo(vo); // 해당 회원 예약 정보 업데이트
				model.addAttribute("msg", "예약이 완료되었습니다");
			} else {
				model.addAttribute("msg", "예약 가능 인원을 초과했습니다. 인원수를 확인하세요");
			}
		} else {
			model.addAttribute("reservationCheck", false);
		}
		model.addAttribute("url", "reservation?pn=" + parkName);
	}

	@Override
	public int getHistoryCount(int mId) {
		return reservationDao.findCountReservation(mId);
	}

	@Override
	public int getReservationCountAll() {
		return reservationDao.findAllCount();
	}

	@Override
	public void reservationHistoryView(Model model, HttpSession session) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int pageSize = 8;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}

		int currentPage = Integer.parseInt(pageNum);

		int startRow = (currentPage - 1) * pageSize + 1;
		int mId = (int) session.getAttribute("sessionM_id");
		int count = getHistoryCount(mId);
		List<ReservationVo> reservationVo = new ArrayList<ReservationVo>();

		if (count > 0) {
			reservationVo = reservationDao.findReservationHistory(mId, startRow, pageSize);
		}

		request.setAttribute("mrdtos", reservationVo);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);

	}

	@Override
	public void cancelAdmin(Model model, HttpSession session, int rId) {

		if (((String) session.getAttribute("sessionRole")).equals("admin")) {
			ReservationVo vo = reservationDao.findOneReservationInfo(rId);
			int count = vo.getCount();
			String park = vo.getPark_name();
			Date date = vo.getReservation_date();

			reservationDao.addParkCount(count, park, date);
			reservationDao.deleteReservation(rId);
			
			model.addAttribute("msg", "예약 취소가 완료되었습니다");
			model.addAttribute("url", "reservationManage");
		}else {
			model.addAttribute("msg", "관리자 권한이 없습니다");
			model.addAttribute("url", "home");
		}
	}

	@Override
	public void cancelHistory(Model model, HttpSession session, Date date) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String id = request.getParameter("rid");
		int rid = Integer.parseInt(id);
		int mFkId = reservationDao.findMemberIdByResrvationId(rid);
		int mId = (int) session.getAttribute("sessionM_id");
		String count2 = request.getParameter("reservation_count");
		int count = Integer.parseInt(count2);

		String park = reservationDao.findParkName(rid);

		if (mId == mFkId) {
			reservationDao.addParkCount(count, park, date);
			reservationDao.deleteReservation(rid);
			model.addAttribute("msg", "취소가 완료되었습니다");
			model.addAttribute("url", "history");
		} else {
			model.addAttribute("msg", "에약 취소는 본인만 가능합니다");
			model.addAttribute("url", "history");
		}

	}

	@Override
	public void reservationManageView(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		int pageSize = 7;
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = getReservationCountAll();
		List<ReservationVo> reservationVo = null;

		if (count > 0) {
			reservationVo = reservationDao.findAllReservationInfo(startRow, pageSize);
		}

		request.setAttribute("dtos", reservationVo);
		request.setAttribute("count", count);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);

	}
	
	@Override
	public void reservationSearch(Model model) {
		Map<String, Object> asMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) asMap.get("request");
		
		String id = request.getParameter("search");
		int rId = Integer.parseInt(id);
		
		ReservationVo vo = reservationDao.findOneReservationInfo(rId);		
		
		request.setAttribute("vo", vo);
	}
	
	
	
}
