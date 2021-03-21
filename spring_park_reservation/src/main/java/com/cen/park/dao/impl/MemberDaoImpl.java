package com.cen.park.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cen.park.dao.MemberDao;
import com.cen.park.vo.MemberVo;

import lombok.RequiredArgsConstructor;

@Repository("memberDao")
@RequiredArgsConstructor
public class MemberDaoImpl implements MemberDao{
	
	@Qualifier("sqlSessionFactory")
	private final SqlSession session;

	@Override
	public void insertMember(MemberVo memberVo) {
		session.update("memberNS.insertMember", memberVo);
	}

	@Override
	public String findByUsername(String username) {
		return session.selectOne("memberNS.selectUsernameByUsername", username);
	}

	@Override
	public String findPasswordByUsername(String username) {
		String password = session.selectOne("memberNS.selectPasswordByUsername", username);
		return password; 
	}

	@Override
	public MemberVo findMemberByUsername(String username) {
		return session.selectOne("memberNS.selectMemberByUsername", username);
	}

	@Override
	public void updateMember(MemberVo memberVo) {
		session.update("memberNS.updateMemberInfo" ,memberVo);
	}

	@Override
	public int getMemberCount() {
		return session.selectOne("memberNS.selectMemberCount");
	}

	@Override
	public List<MemberVo> findMemberByPaging(int startRow, int maxContent) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", startRow);
		map.put("rownum", maxContent);
		List<MemberVo> vos =  session.selectList("memberNS.selectAllMemberPaging", map);
		return vos;
	}

	@Override
	public List<MemberVo> findSearchMemberByPaging(int startRow, int maxContent, String searchValue) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", startRow);
		map.put("rownum", maxContent);
		map.put("value", searchValue);
		List<MemberVo> vos =  session.selectList("memberNS.selectSearchMemberPaging", map);
		return vos;
	}

	@Override
	public int getSearchMemberCount(String searchValue) {
		return session.selectOne("memberNS.selectSearchMemberCount", searchValue);
	}

	@Override
	public MemberVo findMemberByID(int m_id) {
		return session.selectOne("memberNS.selectMemberByID", m_id);
	}

	@Override
	public void updateMemberById(MemberVo memberVo) {
		session.update("memberNS.updateMemberInfoById", memberVo);
	}

	@Override
	public int findMemberIdByUsername(String username) {
		return session.selectOne("memberNS.selectIdByUsername", username);
	}
	
	@Override
	public void deleteMember(int m_id) {
		disableQuestionConstraint();
		disableReservationConstraint();
		session.delete("memberNS.deleteMember", m_id);
	}
	
	@Override
	public void disableQuestionConstraint() {
		session.update("memberNS.disableQuestionConstraint");
	}
	
	@Override
	public void enableQuestionConstratin() {
		session.update("memberNS.enableQuestionConstraint");
	}
	
	@Override
	public void disableReservationConstraint() {
		session.update("memberNS.disableReservationConstraint");
	}
	
	@Override
	public void enableReservationConstratin() {
		session.update("memberNS.enableReservationConstraint");
	}
	
}
