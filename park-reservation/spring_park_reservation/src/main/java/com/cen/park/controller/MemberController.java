package com.cen.park.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cen.park.service.MemberService;
import com.cen.park.service.QuestionBoardService;
import com.cen.park.vo.MemberVo;
import com.cen.park.vo.QuestionBoardVo;
import com.cen.park.vo.ReplyVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final QuestionBoardService questionBoardService;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/memberLogin")
	public String memberLogin() {
		return "member/memberLogin";
	}
	
	@GetMapping("/register")
	public String register() {
		return "member/memberRegister";
	}
	
	@GetMapping("/IDCheckForm")
	public String IDCheckForm() {
		return "member/usernameCheckForm";
	}
	
	@PostMapping("/memberDuplicateCheck")
	public String memberDuplicateCheck(HttpServletRequest request, HttpServletResponse response) {
		memberService.memberDuplicateValidatioin(request, response);
		return null;
	}
	
	@PostMapping("/joinSuccess")
	public String joinSuccess(MemberVo memberVo) {
		memberService.memberJoin(memberVo);
		return "member/joinSuccess";
	}
	
	@GetMapping("/login")
	public String loginView() {
		return "member/memberLogin";
	}
	
	
	@PostMapping("/login")
	public String login(String loginId, String loginPw, Model model, HttpSession session) {
		
		int status = memberService.memberLogin(loginId, loginPw);
		int m_id = -1;
		if(status == 2) {
			m_id = memberService.findMemberId(loginId);
			MemberVo vo = memberService.findMemberInfoById(m_id);
			session.setAttribute("sessionId", vo.getUsername());
			session.setAttribute("sessionM_id", vo.getM_id());
			session.setAttribute("sessionRole", vo.getMember_role());
			model.addAttribute("alreadyLogin", "yes");
			return "redirect:/home";
		}else if(status == 1) {
			model.addAttribute("loginStatus", "pwfail");
			return "member/memberLogin";
		}else if(status == 0) {
			model.addAttribute("loginStatus", "idfail");
			return "member/memberLogin";
		}else {
			return "home";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		model.addAttribute("logout", "yes");
		return "redirect:/home";
	}
	
	@RequestMapping("/listOne")
	public String listOne(HttpSession session, Model model) {
		String username = (String) session.getAttribute("sessionId");
		if(username == null) {
			model.addAttribute("notLogin", true);
		}else {
			model.addAttribute("notLogin", false);
			MemberVo memberVo = new MemberVo();
			memberVo = memberService.memberModifyView(username);
			
			
			model.addAttribute("member", memberVo);
		}
	
	
		return "member/listOne";
	}
	
	@GetMapping("/modifyMember")
	public String modifyMember() {
		return "member/modifyMember";
	}
	
	@GetMapping("/userPasswordCheck")
	public String userPasswordCheck() {
		return "member/userPasswordCheck";
	}

	@PostMapping("/userPasswordCheck")
	public String postUserPasswordCheck(HttpSession session, String pw, Model model) {
		String username = (String) session.getAttribute("sessionId");
		int status = memberService.memberPasswordCheck(username, pw);
		
		if(status == 1) {
			model.addAttribute("pwCheck", true);
			
			MemberVo memberVo = new MemberVo();
			memberVo = memberService.memberModifyView(username);
			
			model.addAttribute("member", memberVo);
			return "member/modifyMember";
		}else {
			model.addAttribute("pwCheck", false);
			return "member/userPasswordCheck";
		}
	}
	
	@PostMapping("/modifyProcess")
	public String modifyProcess(MemberVo memberVo, Model model) {
		memberService.memberUpdateInfo(memberVo);
		model.addAttribute("modifyOk", true);
		return "forward:/listOne";
	}
	
	
	@GetMapping("/question")
	public String question(String pageNum, Model model) {
		int pageSize = 8;
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
		int count = questionBoardService.getCount();
		
		List<QuestionBoardVo> vos = questionBoardService.questionBoardListView(startRow, pageSize);
		
		model.addAttribute("qdtos", vos);
		model.addAttribute("count", count);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageSize", pageSize);
		
		return "questionBoard/questionBoardList";
	}
	
	@GetMapping("/write")
	public String writeView(Model model, HttpSession session) {
		String username = (String)session.getAttribute("sessionId");
		if(username == null) {
			model.addAttribute("msg", "글쓰기는 회원만 가능합니다");
			model.addAttribute("url", "login");
			return "redirect";
		}
		MemberVo memberVo = memberService.findMemberInfoByUsername(username);
		model.addAttribute("writer_id", memberVo.getUsername());
		model.addAttribute("writer_name", memberVo.getName());
		model.addAttribute("writer_email", memberVo.getEmail());
		return "questionBoard/writeQBoard";
	}
	
	@GetMapping("/deletequestion")
	public String deleteQuestion(@RequestParam("qid") int qid) {
		questionBoardService.deleteBoard(qid);
		return "redirect:/question";
	}
	
	@PostMapping("/write")
	public String write(QuestionBoardVo vo) {
		questionBoardService.boardWrite(vo);
		return "redirect:/question";
	}
	
	@RequestMapping("/questionDetail")
	public String questionDetailView(@RequestParam("qid") String qid, Model model) {
		int qb_id = Integer.parseInt(qid);
		
		QuestionBoardVo vo = questionBoardService.boardDetailView(qb_id);
		int status = questionBoardService.getStatus(qb_id);
		
		
		model.addAttribute("qdto", vo);
		model.addAttribute("status", status);
		
		if(status == 1) {
			return "forward:/reply";
		}else {
			return "questionBoard/questionBoardDetail";
		}
		
		
	}
	
	@GetMapping("/modifyQuestion")
	public String modifyQuestionView(@RequestParam("qid") int qid, Model model, HttpSession session) {
		QuestionBoardVo vo = questionBoardService.boardDetailView(qid);
		model.addAttribute("vo", vo);
		return "questionBoard/modifyQBoard";
	}
	
	@PostMapping("/modifyQuestion")
	public String modifyQuestion(QuestionBoardVo vo, Model model) {
		questionBoardService.update(vo);
		model.addAttribute("modifyAlert", "1");
		return "forward:/questionDetail?qid=" + vo.getQb_id();
	}
	
	@GetMapping("/reply")
	public String replyView(Model model, @RequestParam("qid")int qb_id) {
		ReplyVo rVo = questionBoardService.findReplyViewByQId(qb_id);
		QuestionBoardVo qVo = questionBoardService.boardDetailView(qb_id);
		
		model.addAttribute("rdto", rVo);
		model.addAttribute("qdto", qVo);
		return "questionBoard/replyBoardDetail";
	}
	
	@PostMapping("/reply")
	public String reply(ReplyVo vo, Model model) {
		questionBoardService.addReply(vo);
		model.addAttribute("msg", "답변을 완료했습니다");
		model.addAttribute("url",  "reply?qid="+vo.getRe_fk_id());
		return "redirect";
	}
	
	@PostMapping("/questionSearch")
	public String questionSearch(String park, String condition, String search, String pageNum, Model model) {
		int pageSize = 8;

		if(pageNum == null) {
			pageNum = "1";
		}
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage - 1) * pageSize + 1;
				
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("pageSize", pageSize);
		
		if(condition.equals("제목")) {
			int count = questionBoardService.getCountTitle(search);
			List<QuestionBoardVo> vos = questionBoardService.searchTitle(search, park, startRow, pageSize);
			model.addAttribute("count", count);
			model.addAttribute("qdtos", vos);
			return "questionBoard/questionBoardTitleSearch";
		}else{
			int count = questionBoardService.getCountWriter(search);
			List<QuestionBoardVo> vos = questionBoardService.searchWriter(search, park, startRow, pageSize);
			model.addAttribute("count", count);
			model.addAttribute("qdtos", vos);
			return "questionBoard/questionBoardSubSearch";
		}
	}
	
	@GetMapping("/visit")
	public String visit() {
		return "common/Error404";
	}
	
	@GetMapping("/signOut")
	public String signOut() {
		return "member/signOutPasswordCheck";
	}
	
	@PostMapping("/signOut")
	public String signOutCheck(HttpSession session, Model model, String pw) {
		MemberVo vo = memberService.findMemberInfoById((int)session.getAttribute("sessionM_id"));
		String username = vo.getUsername();
		String password = pw;
		int m_id = vo.getM_id();
		
		memberService.signOut(username, password, m_id, model);
		return "redirect";
	}
	
	@GetMapping("/signOutForm")
	public String signOutSuccess(@RequestParam("id") int m_id, Model model) {
		MemberVo vo = memberService.findMemberInfoById(m_id);
		if(vo == null) {
			return "member/signOutSuccess";
		}else {
			model.addAttribute("msg", "잘못된 접근입니다");
			model.addAttribute("url", "home");
			return "redirect";
		}
	}
	
	@GetMapping("/findId")
	public String findId() {
		return "member/findId";
	}
}
