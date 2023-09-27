package com.flexmoney.voucherallocation.module.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.flexmoney.voucherallocation.module.exception.VoucherAllocationException;
import com.flexmoney.voucherallocation.module.pojo.Transaction;
import com.flexmoney.voucherallocation.module.pojo.Voucher;
import com.flexmoney.voucherallocation.module.pojo.VoucherAllocation;

public interface ServiceMethods {
	public List<Transaction> readTransactionData(String fileLocation);
	public List<Voucher> readVoucherData(String fileLocation);
	public List<VoucherAllocation> findVoucherAllocation(List<Transaction> transactionDetails, List<Voucher> voucherDetails);
	public void writeVoucherAllocationDetails(String voucherAllocationFileLocation, List<VoucherAllocation> voucherAllocationDetails);
}
