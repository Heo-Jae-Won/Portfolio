package com.example.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EreplyVO {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date regDate;
	private int ercode;
	private int ecode;
	private String ercontent;
	private String erwriter;
	private int admincondition;
	private int usercondition;

	
	
	public int getAdmincondition() {
		return admincondition;
	}
	public void setAdmincondition(int admincondition) {
		this.admincondition = admincondition;
	}
	public int getUsercondition() {
		return usercondition;
	}
	public void setUsercondition(int usercondition) {
		this.usercondition = usercondition;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public int getErcode() {
		return ercode;
	}
	public void setErcode(int ercode) {
		this.ercode = ercode;
	}
	public int getEcode() {
		return ecode;
	}
	public void setEcode(int ecode) {
		this.ecode = ecode;
	}
	public String getErcontent() {
		return ercontent;
	}
	public void setErcontent(String ercontent) {
		this.ercontent = ercontent;
	}
	public String getErwriter() {
		return erwriter;
	}
	public void setErwriter(String erwriter) {
		this.erwriter = erwriter;
	}
	@Override
	public String toString() {
		return "EreplyVO [regDate=" + regDate + ", ercode=" + ercode + ", ecode=" + ecode + ", ercontent=" + ercontent
				+ ", erwriter=" + erwriter + "]";
	}
	
	
}
