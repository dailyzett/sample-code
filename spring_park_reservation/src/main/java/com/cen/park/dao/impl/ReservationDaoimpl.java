package com.cen.park.dao.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.cen.park.dao.ReservationDao;
import com.cen.park.vo.ParkVo;
import com.cen.park.vo.ReservationVo;

import lombok.RequiredArgsConstructor;

@Repository("reservationDao")
@RequiredArgsConstructor
public class ReservationDaoimpl implements ReservationDao {
	
	private final SqlSession session;
	
	@Override
	public List<ParkVo> findParkInfo(Date start, Date end, String park_name){
		HashMap<String, String> map = new HashMap<String, String>();
	
		map.put("start", start.toString());
		map.put("end", end.toString());
		map.put("park_name", park_name);
		
		return session.selectList("reservationNs.selectParkInfo", map);
	}
	
	@Override
	public int getEnablePeopleCount(Date inputDate, String parkName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("inputDate", inputDate.toString());
		map.put("park_name", parkName);
		return session.selectOne("reservationNs.selectEnablePeople", map);
	}
	
	@Override
	public void updateParkInfo(int peopleCount, String parkName, Date inputDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("peopleCount", peopleCount);
		map.put("park_name", parkName.toString());
		map.put("inputDate", inputDate.toString());
	
		
		session.update("reservationNs.updateParkInfo", map);
	}
	
	@Override
	public void insertReservationInfo(ReservationVo vo) {
		session.insert("reservationNs.insertReservationInfo", vo);
	}
	
	@Override
	public List<ReservationVo> findReservationHistory(int mId, int seq, int rowNum ){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("m_id_fk", mId);
		map.put("seq", seq);
		map.put("rownum", rowNum);
		return session.selectList("reservationNs.selectAllReservationInfo", map);
	}
	
	@Override
	public int findCountReservation(int mId) {
		int m_id_fk = mId;
		return session.selectOne("reservationNs.selectCountReservation", m_id_fk);
	}
	
	@Override
	public void deleteReservation(int rId) {
		int r_id = rId;
		session.delete("deleteReservation", r_id);
	}
	
	@Override
	public void addParkCount(int peopleCount, String parkName, Date date) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("enable_people", peopleCount);
		map.put("park_name", parkName);
		map.put("reservation_date", date.toString());
		session.update("reservationNs.addParkStatus", map);
	}
	
	@Override
	public int findMemberIdByResrvationId(int r_id) {
		return session.selectOne("reservationNs.selectMemberIdByReservationId", r_id);
	}
	
	@Override
	public int findCountByReservationId(int r_id) {
		return session.selectOne("reservationNs.selectCountByReservationId", r_id);
	}
	
	@Override
	public String findParkName(int r_id) {
		return session.selectOne("reservationNs.selectParkByReservationId", r_id);
	}

	@Override
	public int findAllCount() {
		return session.selectOne("reservationNs.selectCountAll");
	}
	
	@Override
	public List<ReservationVo> findAllReservationInfo(int seq, int rownum){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("rownum", rownum);
		return session.selectList("reservationNs.selectAllParkByNoCondition", map);
	}
	
	@Override
	public ReservationVo findOneReservationInfo(int r_id) {
		return session.selectOne("reservationNs.selectReservationInfoByReservationId", r_id);
	}
}
