package com.cen.park.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cen.park.service.ReservationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReservationController {
	
	private final ReservationService reservationService;
	
	
	@GetMapping("/reservation")
	public String reservation(@RequestParam("pn") String parkName, Model model){
			reservationService.getParkInfo(parkName, model);
			if(parkName.equals("kaya")) {
				return "reservation/reserveKaya";
			}else if(parkName.equals("kelong")) {
				return "reservation/reserveKelong";
			}else if(parkName.equals("naejang")){
				return "reservation/reserveNaejang";
			}else {
				return "reservation/reserveSeolak";
			}
	}
	
	@PostMapping("/reservation")
	public String reservation(HttpServletRequest request, HttpSession session, Model model) throws IOException {
		model.addAttribute("request", request);
		reservationService.setReservationStatus(model, session);
		return "redirect";
	}
	
	@GetMapping("/history")
	public String historyView(HttpServletRequest request, HttpSession session, Model model) {
		
		if(session.getAttribute("sessionId") == null) {
			model.addAttribute("msg", "로그인이 필요합니다");
			model.addAttribute("url", "login");
			return "redirect";
		}else {
		
			model.addAttribute("request", request);
			reservationService.reservationHistoryView(model, session);
			return "reservation/history";
		}
	}
	
	@PostMapping("/cancel")
	public String reservationCancel(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam("reservation_date")Date date) {

		model.addAttribute("request", request);
		reservationService.cancelHistory(model, session, date);
		
		return "redirect";
	}
	
	@GetMapping("/reservationManage")
	public String reservationManageView(Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		reservationService.reservationManageView(model);
		return "reservation/reservationManage";
	}
	
	@GetMapping("/cancelAdmin")
	public String cancelAdmin(@RequestParam("rid") int rId, HttpSession session, Model model) {
		reservationService.cancelAdmin(model, session, rId);
		return "redirect";
	}
	
	@PostMapping("/reservationListSearch")
	public String reservationSearch(HttpSession session, Model model, HttpServletRequest request) {
		model.addAttribute("request", request);
		reservationService.reservationSearch(model);
		return "reservation/reservationManageSearch";
	}
}
