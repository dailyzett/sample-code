package com.cen.park.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeBoardVo {
	private int id;
	private String title;
	private String content;
	private Date notice_date;
	private int hit;
	private String park_name;
}
