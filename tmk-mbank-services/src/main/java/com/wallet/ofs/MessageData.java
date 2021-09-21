package com.wallet.ofs;

public class MessageData {


	/*TRANSACTION_TYPE:1:1=ACCC,
			DEBIT_ACCT_NO:1:1=NPR1011000030002,
			DEBIT_CURRENCY:1:1=524,
			DEBIT_AMOUNT:1:1=1025,
			CREDIT_ACCT_NO:1:1=00210000023013,
			AT_UNIQUE_ID:1:1=0000010602130440,
			DEBIT_THEIR_REF:1:1=NAWA-5025,
			ATM_BIN_NO:1:1=989898,
			ATM_TERM_NO:1:1=HAMRO12,
			CARD_NO:1:1=9851049352,
			ATM_TIMESTAMP:1:1=0502140316*/
	
	private String TRANSACTION_TYPE ="ACCC";
	
	private String DEBIT_ACCT_NO ="NPR1011000030002";
	
	private String CREDIT_ACCT_NO="00210000023013";
	
	private String DEBIT_AMOUNT;
	
	private String DEBIT_CURRENCY="524";
	
	private String AT_UNIQUE_ID="0000010602130440";
	
	private String DEBIT_THEIR_REF;
	
	private String ATM_BIN_NO;
	
	private String ATM_TERM_NO;
	
	private String CARD_NO;
	
	private String ATM_TIMESTAMP;
	
	
	public MessageData() {
		
		  this.TRANSACTION_TYPE ="ACCC";
		
		this.DEBIT_ACCT_NO ="NPR1011000030002";
		
		this.CREDIT_ACCT_NO="00210000023013";
		
		this.DEBIT_CURRENCY="524";
		
		this.AT_UNIQUE_ID="0000010602130440";
		
		
	}

	public String getTRANSACTION_TYPE() {
		return TRANSACTION_TYPE;
	}

	public void setTRANSACTION_TYPE(String tRANSACTION_TYPE) {
		TRANSACTION_TYPE = tRANSACTION_TYPE;
	}

	public String getDEBIT_ACCT_NO() {
		return DEBIT_ACCT_NO;
	}

	public void setDEBIT_ACCT_NO(String dEBIT_ACCT_NO) {
		DEBIT_ACCT_NO = dEBIT_ACCT_NO;
	}

	public String getCREDIT_ACCT_NO() {
		return CREDIT_ACCT_NO;
	}

	public void setCREDIT_ACCT_NO(String cREDIT_ACCT_NO) {
		CREDIT_ACCT_NO = cREDIT_ACCT_NO;
	}

	public String getDEBIT_CURRENCY() {
		return DEBIT_CURRENCY;
	}

	public void setDEBIT_CURRENCY(String dEBIT_CURRENCY) {
		DEBIT_CURRENCY = dEBIT_CURRENCY;
	}

	public String getAT_UNIQUE_ID() {
		return AT_UNIQUE_ID;
	}

	public void setAT_UNIQUE_ID(String aT_UNIQUE_ID) {
		AT_UNIQUE_ID = aT_UNIQUE_ID;
	}

	public String getDEBIT_THEIR_REF() {
		return DEBIT_THEIR_REF;
	}

	public void setDEBIT_THEIR_REF(String dEBIT_THEIR_REF) {
		DEBIT_THEIR_REF = dEBIT_THEIR_REF;
	}

	public String getATM_BIN_NO() {
		return ATM_BIN_NO;
	}

	public void setATM_BIN_NO(String aTM_BIN_NO) {
		ATM_BIN_NO = aTM_BIN_NO;
	}

	public String getATM_TERM_NO() {
		return ATM_TERM_NO;
	}

	public void setATM_TERM_NO(String aTM_TERM_NO) {
		ATM_TERM_NO = aTM_TERM_NO;
	}

	public String getCARD_NO() {
		return CARD_NO;
	}

	public void setCARD_NO(String cARD_NO) {
		CARD_NO = cARD_NO;
	}

	public String getATM_TIMESTAMP() {
		return ATM_TIMESTAMP;
	}

	public void setATM_TIMESTAMP(String aTM_TIMESTAMP) {
		ATM_TIMESTAMP = aTM_TIMESTAMP;
	}

	public String getDEBIT_AMOUNT() {
		return DEBIT_AMOUNT;
	}

	public void setDEBIT_AMOUNT(String dEBIT_AMOUNT) {
		DEBIT_AMOUNT = dEBIT_AMOUNT;
	}

}
