package dto;

import java.sql.Timestamp;

public class MemberDto {
	private String username;
	private String password;
	private String email;
	private String phone1;
	private String phone2;
	private String phone3;
	private String name;
	private Timestamp regit_date;
	
	
	public MemberDto(String username, String password, String email, String phone1, String phone2, String phone3,
			String name, Timestamp regit_date) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.name = name;
		this.regit_date = regit_date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getRegit_date() {
		return regit_date;
	}
	public void setRegit_date(Timestamp regit_date) {
		this.regit_date = regit_date;
	}
	
	
	
	
		
}
