package com.reservation.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReservationCommand {
	public int execute(HttpServletRequest request, HttpServletResponse response);
}
