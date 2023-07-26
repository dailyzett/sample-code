package com.common.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BoardDto {
	private int id;
	private String writerName;
	private String parkName;
	private int memberFkId;
	private String writerId;
	private String stringFormatDate;
	private String writerEmail;
	private String title;
	private String content;
	private Timestamp writeDate;
	private int group;
	private int step;
	private int indent;
	private int hit;
	
	private int status;
	
	
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStringFormatDate(String stringFormatDate) {
		this.stringFormatDate = stringFormatDate;
	}

	public String getStringFormatDate() {
		return stringFormatDate;
	}

	public void setStringFormatDate(Timestamp writeDate) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String formatDate = simpleDateFormat.format(writeDate);
		stringFormatDate = formatDate;
	}
	
	

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public int getMemberFkId() {
		return memberFkId;
	}

	public void setMemberFkId(int memberFkId) {
		this.memberFkId = memberFkId;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getWriterEmail() {
		return writerEmail;
	}

	public void setWriterEmail(String writerEmail) {
		this.writerEmail = writerEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getWriteDate() {
		return writeDate;
	}

	public void setWriteDate() {
		this.writeDate = new Timestamp(System.currentTimeMillis());
	}
	

	
	public void setWriteDate(Timestamp date) {
		this.writeDate = date;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	

}
