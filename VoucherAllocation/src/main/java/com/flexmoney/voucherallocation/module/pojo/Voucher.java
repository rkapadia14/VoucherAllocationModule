package com.flexmoney.voucherallocation.module.pojo;

import java.time.LocalDate;

public class Voucher {
	String voucherCode;
	double voucherAmt;
	LocalDate voucherExpiryDate;
	
	
	public LocalDate getVoucherExpiryDate() {
		return voucherExpiryDate;
	}
	public void setVoucherExpiryDate(LocalDate voucherExpiryDate) {
		this.voucherExpiryDate = voucherExpiryDate;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public double getVoucherAmt() {
		return voucherAmt;
	}
	public void setVoucherAmt(double voucherAmt) {
		this.voucherAmt = voucherAmt;
	}
	
	
	

}
