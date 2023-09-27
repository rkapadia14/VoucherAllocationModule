package com.flexmoney.voucherallocation.module.pojo;

import java.time.LocalDateTime;

public class Transaction {
	int transactionId;
	double transactionAmt;
	LocalDateTime transactionCreatedDate;
	
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
	
	public LocalDateTime getTransactionCreatedDate() {
		return transactionCreatedDate;
	}
	
	public void setTransactionCreatedDate(LocalDateTime transactionCreatedDate) {
		this.transactionCreatedDate = transactionCreatedDate;
	}

	
}
