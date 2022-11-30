package com.example.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PayVO {
	private int payprice;
	private int sellercondition,buyercondition;
	private String paytype,payemail,paycode,buyer,seller,pcode;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date regDate;
	public int getPayprice() {
		return payprice;
	}
	
	public void setPayprice(int payprice) {
		this.payprice = payprice;
	}
	
	public int getSellercondition() {
		return sellercondition;
	}
	
	public void setSellercondition(int sellercondition) {
		this.sellercondition = sellercondition;
	}
	public int getBuyercondition() {
		return buyercondition;
	}
	public void setBuyercondition(int buyercondition) {
		this.buyercondition = buyercondition;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getPayemail() {
		return payemail;
	}
	public void setPayemail(String payemail) {
		this.payemail = payemail;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "PayVO [payprice=" + payprice + ", sellercondition=" + sellercondition + ", buyercondition="
				+ buyercondition + ", paytype=" + paytype + ", payemail=" + payemail + ", paycode=" + paycode
				+ ", buyer=" + buyer + ", seller=" + seller + ", pcode=" + pcode + ", regDate=" + regDate + "]";
	}
	
	
	
	

}
