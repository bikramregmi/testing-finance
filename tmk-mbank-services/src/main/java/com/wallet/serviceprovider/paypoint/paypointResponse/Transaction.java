package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Transaction")
public class Transaction {

	@XmlElement(name="BillNumber")
	 private String BillNumber;

	@XmlElement(name="Key")
	private String Key;

	@XmlElement(name="Special1")
	    private String Special1;
	@XmlElement(name="Special2")
	    private String Special2;
	@XmlElement(name="SalesPointType")
	    private String SalesPointType;
	@XmlElement(name="Account")
	    private String Account;
	@XmlElement(name="DealerName")
	    private String DealerName;
	@XmlElement(name="ExternalStan")
	    private String ExternalStan;
	@XmlElement(name="PaySourceType")
	    private String PaySourceType;
	@XmlElement(name="ServiceCode")
	    private String ServiceCode;
	@XmlElement(name="CommissionAmount")
	    private String CommissionAmount;
	@XmlElement(name="Company")
	    private Company Company;
	@XmlElement(name="Currency")
	    private String Currency;
	@XmlElement(name="RefStan")
	    private String RefStan;
	@XmlElement(name="PaymentId")
	    private String PaymentId;
	@XmlElement(name="UserLogin")
	    private String UserLogin;
	@XmlElement(name="Amount")
	    private String Amount;
	@XmlElement(name="RegDate")
	    private String RegDate;
	@XmlElement(name="Stan")
	    private String Stan;
	@XmlElement(name="ResponseCode")
	    private String ResponseCode;
	@XmlElement(name="CurrencyCode")
	    private String CurrencyCode;
	@XmlElement(name="City")
	    private String City;
	@XmlElement(name="Status")
	    private String Status;
	@XmlElement(name="Address")
	    private String Address;
	@XmlElement(name="RequestKey")
	    private String RequestKey;
	@XmlElement(name="RequestKey")
	    private String Id;
	@XmlElement(name="NumberStatus")
	    private String NumberStatus;
	@XmlElement(name="DealerId")
	    private String DealerId;

		public String getBillNumber() {
			return BillNumber;
		}

		public void setBillNumber(String billNumber) {
			BillNumber = billNumber;
		}

		public String getKey() {
			return Key;
		}

		public void setKey(String key) {
			Key = key;
		}

		public String getSpecial1() {
			return Special1;
		}

		public void setSpecial1(String special1) {
			Special1 = special1;
		}

		public String getSpecial2() {
			return Special2;
		}

		public void setSpecial2(String special2) {
			Special2 = special2;
		}

		public String getSalesPointType() {
			return SalesPointType;
		}

		public void setSalesPointType(String salesPointType) {
			SalesPointType = salesPointType;
		}

		public String getAccount() {
			return Account;
		}

		public void setAccount(String account) {
			Account = account;
		}

		public String getDealerName() {
			return DealerName;
		}

		public void setDealerName(String dealerName) {
			DealerName = dealerName;
		}

		public String getExternalStan() {
			return ExternalStan;
		}

		public void setExternalStan(String externalStan) {
			ExternalStan = externalStan;
		}

		public String getPaySourceType() {
			return PaySourceType;
		}

		public void setPaySourceType(String paySourceType) {
			PaySourceType = paySourceType;
		}

		public String getServiceCode() {
			return ServiceCode;
		}

		public void setServiceCode(String serviceCode) {
			ServiceCode = serviceCode;
		}

		public String getCommissionAmount() {
			return CommissionAmount;
		}

		public void setCommissionAmount(String commissionAmount) {
			CommissionAmount = commissionAmount;
		}

		public Company getCompany() {
			return Company;
		}

		public void setCompany(Company company) {
			Company = company;
		}

		public String getCurrency() {
			return Currency;
		}

		public void setCurrency(String currency) {
			Currency = currency;
		}

		public String getRefStan() {
			return RefStan;
		}

		public void setRefStan(String refStan) {
			RefStan = refStan;
		}

		public String getPaymentId() {
			return PaymentId;
		}

		public void setPaymentId(String paymentId) {
			PaymentId = paymentId;
		}

		public String getUserLogin() {
			return UserLogin;
		}

		public void setUserLogin(String userLogin) {
			UserLogin = userLogin;
		}

		public String getAmount() {
			return Amount;
		}

		public void setAmount(String amount) {
			Amount = amount;
		}

		public String getRegDate() {
			return RegDate;
		}

		public void setRegDate(String regDate) {
			RegDate = regDate;
		}

		public String getStan() {
			return Stan;
		}

		public void setStan(String stan) {
			Stan = stan;
		}

		public String getResponseCode() {
			return ResponseCode;
		}

		public void setResponseCode(String responseCode) {
			ResponseCode = responseCode;
		}

		public String getCurrencyCode() {
			return CurrencyCode;
		}

		public void setCurrencyCode(String currencyCode) {
			CurrencyCode = currencyCode;
		}

		public String getCity() {
			return City;
		}

		public void setCity(String city) {
			City = city;
		}

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
		}

		public String getAddress() {
			return Address;
		}

		public void setAddress(String address) {
			Address = address;
		}

		public String getRequestKey() {
			return RequestKey;
		}

		public void setRequestKey(String requestKey) {
			RequestKey = requestKey;
		}

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getNumberStatus() {
			return NumberStatus;
		}

		public void setNumberStatus(String numberStatus) {
			NumberStatus = numberStatus;
		}

		public String getDealerId() {
			return DealerId;
		}

		public void setDealerId(String dealerId) {
			DealerId = dealerId;
		}
	
	    
}

