package com.common.dto;

import java.sql.Date;

public class MemberReservationDto {
	private int rId;
	private int mIdFk;
	private Date reservationDate;
	private int status;
	private int count;
	private int price;
	private String parkName;
	
	
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public int getmIdFk() {
		return mIdFk;
	}
	public void setmIdFk(int mIdFk) {
		this.mIdFk = mIdFk;
	}
	public Date getReservationDate() {
		return reservationDate;
	}
	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
