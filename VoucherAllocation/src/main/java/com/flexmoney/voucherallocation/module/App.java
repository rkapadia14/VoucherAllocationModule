package com.flexmoney.voucherallocation.module;

import java.io.FileNotFoundException;
import java.util.List;

import com.flexmoney.voucherallocation.module.exception.VoucherAllocationException;
import com.flexmoney.voucherallocation.module.pojo.Transaction;
import com.flexmoney.voucherallocation.module.pojo.Voucher;
import com.flexmoney.voucherallocation.module.pojo.VoucherAllocation;
import com.flexmoney.voucherallocation.module.service.ServiceMethods;
import com.flexmoney.voucherallocation.module.service.ServiceMethodsImpl;

/**
 * Voucher Allocation Program
 * @author Rinali Kapadia
 * 
 */
public class App 
{
    public static void main( String[] args)
    {
    	System.out.println("======================================");
    	System.out.println("Welcome to Voucher Allocation Program!");
    	String transactionFileLocation = args[0].trim().replaceAll("/", "\\");
        transactionFileLocation = args[0].replaceAll("\"", "\\");
        String voucherFileLocation = args[1].trim().replaceAll("/", "\\").trim();
        voucherFileLocation = args[1].replaceAll("\"", "\\");
        String voucherAllocationFileLocation = args[2].trim().replaceAll("/", "\\");
        voucherAllocationFileLocation = args[2].replaceAll("\"", "\\");
        
        //String transactionFileLocation = "C:\\Users\\lenovo\\Documents\\SampleInput\\transaction.csv";
        //String voucherFileLocation = "C:\\Users\\lenovo\\Documents\\SampleInput\\voucher.csv";
        //String voucherAllocationFileLocation = "C:\\Users\\lenovo\\Documents\\SampleInput\\voucherallocation.csv";
        System.out.println("Your transaction input file is: "+transactionFileLocation);
        System.out.println("Your voucher input file is: "+voucherFileLocation);
        System.out.println("Your final output will be at: "+voucherAllocationFileLocation);
        try
        {
	        if((!transactionFileLocation.substring(transactionFileLocation.length() -4, transactionFileLocation.length()).equalsIgnoreCase(".csv"))
	        || (!voucherFileLocation.substring(voucherFileLocation.length() -4, voucherFileLocation.length()).equalsIgnoreCase(".csv")) 
	    	|| (!voucherAllocationFileLocation.substring(voucherAllocationFileLocation.length() -4, voucherAllocationFileLocation.length()).equalsIgnoreCase(".csv")))
	        	throw new VoucherAllocationException("Input and output files should be in .csv format only");
        
	        ServiceMethods service = new ServiceMethodsImpl();
	        List<Transaction> transactionDetails = service.readTransactionData(transactionFileLocation);
	        List<Voucher> voucherDetails = service.readVoucherData(voucherFileLocation);
	        if(transactionDetails.size() > 0 && voucherDetails.size() >0)
	        {
	        	List<VoucherAllocation> voucherAllocationDetails = service.findVoucherAllocation(transactionDetails,voucherDetails);
	        	service.writeVoucherAllocationDetails(voucherAllocationFileLocation, voucherAllocationDetails);
	        	System.out.println("Thank you! Completed Voucher Allocation Request Successfully. Kindly check the output file at the given location:" +voucherAllocationFileLocation);
	        }
	        System.out.println("======================================");
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
        	System.out.println("No input files found. Please check if file path is provided in the given order: 1. transaction.csv,  2. voucher.csv, 3. voucherallocation.csv in the format::C:\\Documents\\SampleInput\\transaction.csv");
        }
        catch(VoucherAllocationException ex)
        {
        	System.out.println(ex.getMessage());
        }
        catch(Exception ex)
        {
        	System.out.println("Sorry cannot process voucher allocation request since " + ex.getLocalizedMessage());
        }
    }	
}
