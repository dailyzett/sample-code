package com.common.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MemberDto {
	private int m_id;
	private String username;
	private String password;
	private String email;
	private String phone1;
	private String phone2;
	private String phone3;
	private String name;
	private String regit_date2;
	private Timestamp regit_date;
	private int totalPrice;
	
	
	
	
	
	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getRegit_date2() {
		return regit_date2;
	}

	public int getM_id() {
		return m_id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone1() {
		return phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public String getName() {
		return name;
	}

	public Timestamp getRegit_date() {
		return regit_date;
	}

	private MemberDto(Builder builder) {
		this.m_id = builder.m_id;
		this.username = builder.username;
		this.password = builder.password;
		this.name = builder.name;
		this.email =builder.email;
		this.phone1 = builder.phone1;
		this.phone2 = builder.phone2;
		this.phone3 = builder.phone3;
		this.regit_date = builder.regit_date;
		this.regit_date2 = builder.regit_date2;
	}
	
	public static class Builder{
		private int m_id;
		private String username;
		private String password;
		private String email;
		private String phone1;
		private String phone2;
		private String phone3;
		private String name;
		private Timestamp regit_date;
		private String regit_date2;
		
		public Builder() {
			
		}
		
		public Builder m_id(int m_id) {
			this.m_id = m_id;
			return this;
		}
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		
		public Builder email(String email) {
			this.email = email;
			return this;
		}
		
		public Builder phone1(String phone1) {
			this.phone1 = phone1;
			return this;
		}
		
		public Builder phone2(String phone2) {
			this.phone2 = phone2;
			return this;
		}
		
		public Builder phone3(String phone3) {
			this.phone3 = phone3;
			return this;
		}
		
		public Builder regit_date() {
			this.regit_date = new Timestamp(System.currentTimeMillis());
			return this;
		}
		
		public Builder regit_date2(Timestamp date) {
			
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String formatDate = simpleDateFormat.format(date);
			this.regit_date2 = formatDate;
			return this;
		}
		
		
		public MemberDto build() {
			return new MemberDto(this);
		}
		
	}

	@Override
	public String toString() {
		return "MemberDto [username=" + username + ", password=" + password + ", email=" + email + ", phone1=" + phone1
				+ ", phone2=" + phone2 + ", phone3=" + phone3 + ", name=" + name + ", regit_date=" + regit_date + "]";
	}

}
