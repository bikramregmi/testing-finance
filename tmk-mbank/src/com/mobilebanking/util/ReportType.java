package com.mobilebanking.util;

public enum ReportType {
        	CUSTOMER_REGISTRATION("customer registration report"),
        	BANK_ACCOUNT_DETAIL("bank account detail report"),
        	BUSINESS_SUMMARY_REPORT("business summary report"),
        	REVENUE_SHARING("revenue sharing report"),
        	BANK_BALANCE_CHECKING("bank balance checking"),
	        SMS_BUSINESS_REPORT("sms business report"),
	        REVERSAL_TRANSACTION("reversal transaction report"),
	        PAYPOINT_REPORT("paypoint report");
	
	  private String value;
	  
	  ReportType(String value) {
		  this.value=value;
	  }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	} 
	
	public String getValue() {
		return value;
	}
	  
}
