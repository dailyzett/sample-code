package com.cen.park.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.cen.park.vo.MemberVo;

public interface MemberService {
	public void memberJoin(MemberVo memberVo);
	public void memberDuplicateValidatioin(HttpServletRequest request, HttpServletResponse response);
	public int memberLogin(String username, String password);
	
	public MemberVo memberModifyView(String username);
	public MemberVo findMemberInfoById(int m_id);
	public MemberVo findMemberInfoByUsername(String username);
	
	public int findMemberId(String username);
	
	public int memberPasswordCheck(String username, String password);
	public void memberUpdateInfo(MemberVo memberVo);
	public void memberUpdateInfoById(MemberVo memberVo);
	public List<MemberVo> memberListView(int startRow, int maxContent);
	public List<MemberVo> memberListSearchView(int startRow, int maxContent, String searchValue);
	
	public int confirmMember(String post, String get);

	public void signOut(String username, String password, int m_id, Model model);

}
