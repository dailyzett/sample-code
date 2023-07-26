package com.cen.park.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cen.park.dao.MemberDao;
import com.cen.park.service.MemberService;
import com.cen.park.service.NoticeBoardService;
import com.cen.park.vo.MemberVo;
import com.cen.park.vo.NoticeBoardVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final MemberService memberService;
	private final MemberDao memberDao;
	private final NoticeBoardService noticeBoardService;
	
	@GetMapping("/memberlist")
	public String memberList(String pageNum, Model model) {
		int pageSize = 8;
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = memberDao.getMemberCount();
		
		List<MemberVo> memberVos = memberService.memberListView(startRow, pageSize);
		
		model.addAttribute("dtos", memberVos);
		model.addAttribute("count", count);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageSize", pageSize);
		
		return "member/memberList";
	}
	
	@RequestMapping("/memberListSearch")
	public String memberListSearch(String pageNum, Model model, @RequestParam("search") String searchValue) {
		int pageSize = 8;
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = memberDao.getSearchMemberCount(searchValue);
		
		List<MemberVo> memberVos = memberService.memberListSearchView(startRow, pageSize, searchValue);
		
		model.addAttribute("dtos", memberVos);
		model.addAttribute("count", count);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageSize", pageSize);
		
		return "member/memberListSearch";
	}
	
	@RequestMapping("/adminMemberModify")
	public String adminMemberModify(String username, Model model) {
		MemberVo memberVo = memberService.memberModifyView(username);
		model.addAttribute("adminMem", memberVo);
		return "member/adminMemberModify";
	}
	
	@PostMapping("/adminMemberModifySuccess")
	public String adminMemberModifySuccess(Model model, MemberVo memberVo, String username) {
		
		String getMappingUsername = username;
		String postMappingUsername = memberVo.getUsername();
		int status = memberService.confirmMember(postMappingUsername, getMappingUsername);
		
		if(status == 1) {
			model.addAttribute("memberEx", "yes");
			return "forward:/adminMemberModify";
		}else {
			memberService.memberUpdateInfoById(memberVo);
			return "redirect:/memberlist";
		}
	}
	
	@GetMapping("/notice")
	public String noticeView(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		noticeBoardService.findAll(model);
		return "noticeBoard/noticeBoardList";
	}
	
	@GetMapping("/noticeWrite")
	public String noticeWriteView() {
		return "noticeBoard/noticeWrite";
	}
	
	
	@PostMapping("/noticeWrite")
	public String noticeWrite(NoticeBoardVo vo, HttpServletRequest request, Model model) {
		noticeBoardService.write(vo, model);
		return "redirect";
	}
	
	@GetMapping("/noticeDetail")
	public String noticeDetailView(@RequestParam("nid") int nid, Model model) {
		noticeBoardService.detailView(nid, model);
		return "noticeBoard/noticeBoardDetail";
	}
	
	@GetMapping("/modifyNotice")
	 public String modifyView(@RequestParam("id")int id, Model model) {
		noticeBoardService.detailView(id, model);
		return "noticeBoard/noticeBoardModify";
	}
	
	@PostMapping("/modifyNotice")
	public String modifyNotice(Model model, @RequestParam("id") int id, String title, String content, HttpSession session) {
		if(!((String)session.getAttribute("sessionRole")).equals("admin")) {
			model.addAttribute("msg", "관리자만 게시글을 수정할 수 있습니다.");
			model.addAttribute("url", "notice");
			return "redirect";
		}
		
		
		noticeBoardService.updateBoard(id, title, content, model);
		return "redirect";
	}
	
	@GetMapping("/deletenotice")
	public String deleteNotice(@RequestParam("id") int id) {
		noticeBoardService.deleteBoard(id);
		return "redirect:/notice";
	}
	
	@PostMapping("/noticeSearch")
	public String noticeSearch(String park, String search, Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		noticeBoardService.findSearch(model, search, park);
		return "noticeBoard/noticeBoardSearch";
	}
}
