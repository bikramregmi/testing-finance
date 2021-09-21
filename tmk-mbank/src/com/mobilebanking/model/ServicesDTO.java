package com.mobilebanking.model;

import java.util.ArrayList;
import java.util.List;

public class ServicesDTO {
	
	private long id;
	
	private String name;
	
	private String merchant;

	private long merchantId;
	
	private String uniqueIdentifier;
	
	private boolean priceinput;

	private long minimumvalue;

	private long maximumvalue;

	private boolean accrequired;

	private String accformat;

	private String accountno;
	
	private String url;

	private boolean realTimeSettlement;

	private String bankAccount;

	private String tokenKey;
	
	private String successURL;
	
	private String failureURL;



	private long minnimumvalue;
	
	private List<Double> priceRange = new ArrayList<Double>();

	
	
	
	public List<Double> getPriceRange() {
		return priceRange;
	}



	public void setPriceRange(List<Double> priceRange) {
		this.priceRange = priceRange;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}


	
	public long getId() {
		return id;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public boolean isPriceinput() {
		return priceinput;
	}



	public void setPriceinput(boolean priceinput) {
		this.priceinput = priceinput;
	}



	public long getMinimumvalue() {
		return minimumvalue;
	}



	public void setMinimumvalue(long minimumvalue) {
		this.minimumvalue = minimumvalue;
	}



	public long getMaximumvalue() {
		return maximumvalue;
	}



	public void setMaximumvalue(long maximumvalue) {
		this.maximumvalue = maximumvalue;
	}



	public boolean isAccrequired() {
		return accrequired;
	}



	public void setAccrequired(boolean accrequired) {
		this.accrequired = accrequired;
	}



	public String getAccformat() {
		return accformat;
	}



	public void setAccformat(String accformat) {
		this.accformat = accformat;
	}



	public String getAccountno() {
		return accountno;
	}



	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}



	public boolean isRealTimeSettlement() {
		return realTimeSettlement;
	}



	public void setRealTimeSettlement(boolean realTimeSettlement) {
		this.realTimeSettlement = realTimeSettlement;
	}



	public String getBankAccount() {
		return bankAccount;
	}



	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}



	public String getTokenKey() {
		return tokenKey;
	}



	public void setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
	}



	public String getSuccessURL() {
		return successURL;
	}



	public void setSuccessURL(String successURL) {
		this.successURL = successURL;
	}



	public String getFailureURL() {
		return failureURL;
	}



	public void setFailureURL(String failureURL) {
		this.failureURL = failureURL;
	}



	public long getMinnimumvalue() {
		return minnimumvalue;
	}



	public void setMinnimumvalue(long minnimumvalue) {
		this.minnimumvalue = minnimumvalue;
	}



	public void setId(long id) {
		this.id = id;
	}



	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}
	

}
