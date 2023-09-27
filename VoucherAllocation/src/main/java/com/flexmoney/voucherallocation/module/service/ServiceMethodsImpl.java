package com.flexmoney.voucherallocation.module.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.flexmoney.voucherallocation.module.exception.VoucherAllocationException;
import com.flexmoney.voucherallocation.module.pojo.Transaction;
import com.flexmoney.voucherallocation.module.pojo.Voucher;
import com.flexmoney.voucherallocation.module.pojo.VoucherAllocation;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

public class ServiceMethodsImpl implements ServiceMethods{

	public List<Transaction> readTransactionData(String fileLocation)
	{
		// TODO Auto-generated method stub
		List<Transaction> transactionData = new ArrayList<>();
		HashSet<Integer> transactionIdList = new HashSet<>();
		try
		{
			FileReader filereader = new FileReader(fileLocation);
			CSVReader reader = new CSVReaderBuilder(filereader)
								.withSkipLines(1)
								.build();
			List<String[]> allData = reader.readAll();
			if(allData.size() ==0)
				throw new VoucherAllocationException("Empty/Blank Transaction Input File.");
			//System.out.println("List iss::::"+allData.size());
			for (String[] row : allData) 
			{
				Transaction transaction = new Transaction();
				int i=0;
				for (String cell : row) {
					//System.out.print(cell + "\t");
					if(i==0)
					{
						//System.out.println("Cell id is::"+cell);
						transaction.setTransactionId(Integer.parseInt(cell.trim()));
					}
			        if(i==1)
			        {
			        	//System.out.println("Cell amount is::"+cell.trim());
			        	transaction.setTransactionAmt(Math.abs(Double.parseDouble(cell.trim())));
			        }
			        if(i==2)
			        {
			        	//System.out.println("Cell date is::"+cell);
			        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
			        	transaction.setTransactionCreatedDate(LocalDateTime.parse(cell.trim(), formatter));
			        }
			        //System.out.println();
			        i++;
			    }
			    //System.out.println("=====================================");
			    //System.out.println("Transaction id is::"+transaction.getTransactionId());
			    //System.out.println("Transaction amount is::"+transaction.getTransactionAmt());
			    //System.out.println("Transaction created date is::"+transaction.getTransactionCreatedDate());
			    //System.out.println("=====================================");
			    if(!transactionIdList.contains(transaction.getTransactionId()))
			    {
			    	transactionData.add(transaction);
			    	transactionIdList.add(transaction.getTransactionId());
			    }
			    //System.out.println();
			    reader.close();
			}
			//System.out.println("List transaction is:::"+transactionData.size());
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("Transaction file not found");
		}
		catch (VoucherAllocationException e) 
		{
			System.out.println("Transaction Input File is either blank or empty");
		}
		catch (Exception e) 
		{
			System.out.println("Error while processing transaction data");
		}
		return transactionData;
	}

	@Override
	public List<Voucher> readVoucherData(String fileLocation) 
	{
		List<Voucher> voucherData = new ArrayList<>();
		HashSet<String> voucherCodeList = new HashSet<>();
		try
		{
			FileReader filereader = new FileReader(fileLocation);
			CSVReader reader = new CSVReaderBuilder(filereader)
								.withSkipLines(1)
								.build();
			List<String[]> allData = reader.readAll();
			if(allData.size() ==0)
				throw new VoucherAllocationException("Empty/Blank Voucher Input File.");
			//System.out.println("List voucher rows iss::::"+allData.size());
			for (String[] row : allData) 
			{
				Voucher voucher = new Voucher();
				int i=0;
				for (String cell : row) {
					//System.out.println(cell + "\t");
					if(i==0)
					{
						//System.out.println("Cell voucher id is::"+cell.trim());
						voucher.setVoucherCode(cell.trim());
					}
			        if(i==1)
			        {
			        	//System.out.println("Cell voucher amount is::"+cell.trim());
			        	voucher.setVoucherAmt(Math.abs(Double.parseDouble(cell.trim())));
			        }
			        if(i==2)
			        {
			        	//System.out.println("Cell voucher expiry date is::"+cell);
			        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
			        	voucher.setVoucherExpiryDate(LocalDate.parse(cell.trim(), formatter));
			        }
			        //System.out.println();
			        i++;
			    }
			    //System.out.println("=====================================");
			    //System.out.println("Voucher id is::"+voucher.getVoucherCode());
			    //System.out.println("Voucher amount is::"+voucher.getVoucherAmt());
			    //System.out.println("Voucher expiry date is::"+voucher.getVoucherExpiryDate());
			    //System.out.println("=====================================");
			    if(!voucherCodeList.contains(voucher.getVoucherCode()))
			    {
			    	voucherData.add(voucher);
			    	voucherCodeList.add(voucher.getVoucherCode());
			    }
			  //  System.out.println();
			}
			//System.out.println("List voucher is:::"+voucherData.size());
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("Voucher file not found");
		}
		catch (VoucherAllocationException e) 
		{
			System.out.println("Voucher Input File is either blank or empty");
		}
		catch (Exception e) 
		{
			System.out.println("Error while processing voucher data");
		}
		return voucherData;
	}

	@Override
	public List<VoucherAllocation> findVoucherAllocation(List<Transaction> transactionDetails, List<Voucher> voucherDetails) 
	{
		// TODO Auto-generated method stub
		 
		List<Transaction> transactionDetailsClone = transactionDetails; 
		List<VoucherAllocation> finalVoucherAllocationData = new ArrayList<VoucherAllocation>();
		HashMap<String, Double> voucherMapAmtData = new HashMap<>();
		HashMap<String, LocalDate> voucherMapExpiryDtData = new HashMap<>();
		try
		{
			for(Voucher v : voucherDetails)
			{
				if(!voucherMapAmtData.containsKey(v.getVoucherCode())) 
				{
					voucherMapAmtData.put(v.getVoucherCode(), v.getVoucherAmt());
				}
				if(!voucherMapExpiryDtData.containsKey(v.getVoucherCode())) 
				{
					voucherMapExpiryDtData.put(v.getVoucherCode(), v.getVoucherExpiryDate());
				}
			}
			for(Transaction t : transactionDetailsClone)
			{
				double originalTransactionAmt = t.getTransactionAmt();
				double sumVoucherAmts = 0;
				//System.out.println("Here for transaction Id"+t.getTransactionId());
				
				for(Voucher v : voucherDetails)
				{
					//System.out.println("!!!!!");
					//System.out.println("Here 1 for transaction Id"+t.getTransactionId()+" with transaction amt" + t.getTransactionAmt());	
					//System.out.println("Here 1 voucher code is::" + v.getVoucherCode() +" with voucher Amt is::"+v.getVoucherAmt());
					//System.out.println("!!!!!");
					if(t.getTransactionAmt()>0 &&  v.getVoucherAmt() <= t.getTransactionAmt()
							&& voucherMapAmtData.containsKey(v.getVoucherCode()) 
							&& (v.getVoucherExpiryDate().isAfter(t.getTransactionCreatedDate().toLocalDate())  
								|| v.getVoucherExpiryDate().isEqual(t.getTransactionCreatedDate().toLocalDate()))
							&& voucherMapExpiryDtData.containsKey(v.getVoucherCode())
							//&& (v.getVoucherExpiryDate().isBefore(LocalDate.now()) || v.getVoucherExpiryDate().isEqual(LocalDate.now())  ) 
							&& sumVoucherAmts <=50000)
					{
						//System.out.println("Adding voucher:::"+v.getVoucherCode() +" for transaction id::" + t.getTransactionId());
						finalVoucherAllocationData.add(new VoucherAllocation(t.getTransactionId(), originalTransactionAmt, v.getVoucherAmt(), v.getVoucherCode()));
						t.setTransactionAmt(t.getTransactionAmt()-v.getVoucherAmt());
						voucherMapAmtData.remove(v.getVoucherCode());
						voucherMapExpiryDtData.remove(v.getVoucherCode());
						sumVoucherAmts = sumVoucherAmts + v.getVoucherAmt();
					}
				}
			}
			//System.out.println("Final Voucher Allocation Data size is:::"+finalVoucherAllocationData.size());
			System.out.println("Total Voucher Allocations Done:" +finalVoucherAllocationData.size());
			System.out.println("Details of Voucher Allocation are as follows:-");
			for(VoucherAllocation va: finalVoucherAllocationData)
			{
				System.out.println("For Transaction Id:" + va.getTransactionId() + ", Voucher Code available is:"+va.getVoucherCode());
				//System.out.println("****");
				//System.out.println("Transaction Id is::"+va.getTransactionId());
				//System.out.println("Voucher Code is::"+va.getVoucherCode());
				//System.out.println("****");
			}
		}
		catch (Exception e) {
			System.out.println("Error while voucher allocation" + e.getMessage());
		}
		return finalVoucherAllocationData;
	}

	@Override
	public void writeVoucherAllocationDetails(String voucherAllocationFileLocation, List<VoucherAllocation> voucherAllocationDetails) 
	{
		File file = new File(voucherAllocationFileLocation);
		FileWriter outputfile;
		try 
		{
			outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile, ',',
                     CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                     CSVWriter.DEFAULT_LINE_END);
			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] {"txnId", "txnAmount", "voucherAmount", "voucherCode"});
			for(VoucherAllocation va: voucherAllocationDetails)
			{
				//System.out.println("VA is::"+va.getTransactionId());
				data.add(new String[] { String.valueOf(va.getTransactionId()), String.valueOf(Math.round(va.getTransactionAmt())), String.valueOf(Math.round(va.getVoucherAmt())) ,va.getVoucherCode()});
			}
			writer.writeAll(data);
			writer.close();
			
		} 
		catch (IOException e) 
		{
			System.out.println("IO Exception while writing::"+e.getMessage());
		}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Exception while writing::"+e.getMessage());
		}
	}
}
