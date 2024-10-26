package com.cen.park.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyVo {
	private int re_board_id;
	private String content;
	private int re_fk_id;
}
