package com.common.dto;

import java.sql.Date;

public class ReservationDto {
	private String reserveId;
	private int reserveEnable;
	private Date reserveDate;
	private int kayaMemberFk;
	
	
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public int getReserveEnable() {
		return reserveEnable;
	}
	public void setReserveEnable(int reserveEnable) {
		this.reserveEnable = reserveEnable;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public int getKayaMemberFk() {
		return kayaMemberFk;
	}
	public void setKayaMemberFk(int kayaMemberFk) {
		this.kayaMemberFk = kayaMemberFk;
	}
	
	
}
