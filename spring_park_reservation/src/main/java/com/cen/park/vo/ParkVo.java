package com.cen.park.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkVo {
	private String park_name;
	private int enable_people;
	private Date reservation_date;
	private int park_price;
	private int max_people;
}
