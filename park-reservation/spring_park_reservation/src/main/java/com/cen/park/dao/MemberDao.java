package com.cen.park.dao;

import java.util.List;

import com.cen.park.vo.MemberVo;

public interface MemberDao {
	public void insertMember(MemberVo memberVo);
	public String findByUsername(String username);
	public String findPasswordByUsername(String username);
	
	public int findMemberIdByUsername(String username);
	
	public MemberVo findMemberByUsername(String username);
	public MemberVo findMemberByID(int m_id);
	
	public void updateMember(MemberVo memberVo);
	public void updateMemberById(MemberVo memberVo);
	
	public int getMemberCount();
	public int getSearchMemberCount(String searchValue);
	
	public List<MemberVo> findMemberByPaging(int startRow, int maxContent);
	public List<MemberVo> findSearchMemberByPaging(int startRow, int maxContent, String searchValue);
	public void deleteMember(int m_id);
	
	public void disableQuestionConstraint();
	public void enableQuestionConstratin();
	public void disableReservationConstraint();
	public void enableReservationConstratin();
}
