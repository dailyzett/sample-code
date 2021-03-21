package com.cen.park.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionBoardVo {
	private int qb_id;
	private int qb_fk_id;
	private String qb_title;
	private String qb_content;
	private Timestamp qb_date;
	private int qb_hit;
	
	private String writer_id;
	private String writer_name;
	private String writer_email;
	private String park_name;
	
	private int status;
	
}
