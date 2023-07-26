package com.cen.park.dao;

import java.sql.Date;
import java.util.List;

import com.cen.park.vo.ParkVo;
import com.cen.park.vo.ReservationVo;

public interface ReservationDao {

	public List<ParkVo> findParkInfo(Date start, Date end, String park_name);

	public int getEnablePeopleCount(Date inputDate, String parkName);

	public void updateParkInfo(int peopleCount, String parkName, Date inputDate);

	public void insertReservationInfo(ReservationVo vo);
	
	public List<ReservationVo> findReservationHistory(int mId, int seq, int rowNum);

	public int findCountReservation(int mId);

	public void deleteReservation(int rId);

	public void addParkCount(int peopleCount, String parkName, Date date);

	public int findMemberIdByResrvationId(int r_id);

	public int findCountByReservationId(int r_id);
	
	public String findParkName(int r_id);

	public int findAllCount();

	public List<ReservationVo> findAllReservationInfo(int seq, int rownum);

	public ReservationVo findOneReservationInfo(int r_id);
	
}
