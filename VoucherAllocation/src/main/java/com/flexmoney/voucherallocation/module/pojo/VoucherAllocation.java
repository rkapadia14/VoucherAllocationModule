package com.flexmoney.voucherallocation.module.pojo;

public class VoucherAllocation {
	int transactionId;
	double transactionAmt;
	double voucherAmt;
	String voucherCode;
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public double getTransactionAmt() {
		return transactionAmt;
	}
	public void setTransactionAmt(double transactionAmt) {
		this.transactionAmt = transactionAmt;
	}
	public double getVoucherAmt() {
		return voucherAmt;
	}
	public void setVoucherAmt(double voucherAmt) {
		this.voucherAmt = voucherAmt;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public VoucherAllocation(int transactionId, double transactionAmt, double voucherAmt, String voucherCode) {
		super();
		this.transactionId = transactionId;
		this.transactionAmt = transactionAmt;
		this.voucherAmt = voucherAmt;
		this.voucherCode = voucherCode;
	}
	
	

}
