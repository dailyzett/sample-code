package com.cen.park.service;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

public interface ReservationService {
	public void getParkInfo(String park_name, Model model);
	public void setReservationStatus(Model model, HttpSession session);
	public int getHistoryCount(int mId);
	public void reservationHistoryView(Model model, HttpSession session);
	public void cancelHistory(Model model, HttpSession session, Date date);
	public int getReservationCountAll();
	public void reservationManageView(Model model);
	public void cancelAdmin(Model model, HttpSession session, int rId);
	public void reservationSearch(Model model);
}
