package com.cen.park.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cen.park.dao.MemberDao;
import com.cen.park.service.MemberService;
import com.cen.park.vo.MemberVo;

import lombok.RequiredArgsConstructor;

@Service("memberService")
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDao memberDao;

	@Override
	public void memberJoin(MemberVo memberVo) {
		memberDao.insertMember(memberVo);
	}

	@Override
	public void memberDuplicateValidatioin(HttpServletRequest request, HttpServletResponse response) {
		String requestId = request.getParameter("id");
		String findId = memberDao.findByUsername(requestId);
		try {
			PrintWriter printWriter = response.getWriter();
			if (findId == null) {
				printWriter.write("1"); // 1이면 회원가입 가능
			} else {
				printWriter.write("0"); // 0이면 회원가입 불가능
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int memberLogin(String username, String password) {

		// 0: 아이디 찾을 수 없음
		// 1: 비밀번호 틀림
		// 2: 로그인 성공

		int status = 0;

		String dbUsername = memberDao.findByUsername(username);
		String dbPassword = null;
		if (dbUsername == null) {
			status = 0;
		} else {
			dbPassword = memberDao.findPasswordByUsername(dbUsername);
			if (dbPassword.equals(password)) {
				status = 2;
			} else {
				status = 1;
			}
		}
		return status;
	}

	@Override
	public MemberVo memberModifyView(String username) {
		MemberVo memberVo = new MemberVo();
		memberVo = memberDao.findMemberByUsername(username);
		return memberVo;
	}

	@Override
	public int memberPasswordCheck(String username, String password) {
		int status = 0;
		String dbPassword = memberDao.findPasswordByUsername(username);

		if (dbPassword.equals(password)) {
			status = 1;
		} else {
			status = 0;
		}
		return status;
	}

	@Override
	public void memberUpdateInfo(MemberVo memberVo) {
		memberDao.updateMember(memberVo);
	}

	@Override
	public List<MemberVo> memberListView(int startRow, int pageSize) {

		int count = memberDao.getMemberCount();

		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		if (count > 0) {
			memberVos = memberDao.findMemberByPaging(startRow, pageSize);
		}
		return memberVos;
	}

	@Override
	public List<MemberVo> memberListSearchView(int startRow, int maxContent, String searchValue) {
		int count = memberDao.getSearchMemberCount(searchValue);

		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		if (count > 0) {
			memberVos = memberDao.findSearchMemberByPaging(startRow, maxContent, searchValue);
		}
		return memberVos;
	}

	@Override
	public int confirmMember(String postUsername, String getUsername) {
		String dbUsername = memberDao.findByUsername(postUsername);

		// 1: 회원 이미 존재
		// 0 : 회원 없음
		int status = 0;

		if (!postUsername.equals(getUsername)) {
			if (dbUsername == null) {
				status = 0;
			} else {
				status = 1;
			}
		}else {
			status = 0;
		}

		return status;
	}

	@Override
	public void memberUpdateInfoById(MemberVo memberVo) {
		memberDao.updateMemberById(memberVo);
	}

	@Override
	public MemberVo findMemberInfoById(int m_id) {
		return memberDao.findMemberByID(m_id);
	}

	@Override
	public MemberVo findMemberInfoByUsername(String username) {
		return memberDao.findMemberByUsername(username);
	}

	@Override
	public int findMemberId(String username) {
		return memberDao.findMemberIdByUsername(username);
	}

	@Override
	public void signOut(String username, String password, int m_id, Model model) {
		int status = memberPasswordCheck(username, password);
		
		if(status == 1) {
			memberDao.deleteMember(m_id);
			model.addAttribute("url", "signOutForm?id=" + m_id);
		}else {
			model.addAttribute("msg", "비밀번호가 일치하지않습니다");
			model.addAttribute("url", "signOut");
		}
	}

}
