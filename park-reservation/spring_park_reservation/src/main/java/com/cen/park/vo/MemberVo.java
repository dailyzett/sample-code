package com.cen.park.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberVo {
	private int m_id;
	private String username;
	private String pw;
	private String name;
	private String email;
	private String phone_1;
	private String phone_2;
	private String phone_3;
	private Timestamp regit_date;
	private String member_role;
}
