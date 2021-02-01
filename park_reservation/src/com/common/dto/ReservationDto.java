package com.common.dto;

import java.sql.Date;

public class ReservationDto {
	private String parkName;
	private int enablePeople;
	private Date reservationDate;
	private int parkPrice;
	private int memberFk;
	private int maxPeople;
	
	

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getEnablePeople() {
		return enablePeople;
	}

	public void setEnablePeople(int enablePeople) {
		this.enablePeople = enablePeople;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public int getParkPrice() {
		return parkPrice;
	}

	public void setParkPrice(int parkPrice) {
		this.parkPrice = parkPrice;
	}

	public int getMemberFk() {
		return memberFk;
	}

	public void setMemberFk(int memberFk) {
		this.memberFk = memberFk;
	}

}
