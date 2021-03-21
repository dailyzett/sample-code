package com.cen.park.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationVo {
	private int r_id;
	private int m_id_fk;
	private int status;
	private int count;
	private int price;
	private String park_name;
	private Date reservation_date;
}
