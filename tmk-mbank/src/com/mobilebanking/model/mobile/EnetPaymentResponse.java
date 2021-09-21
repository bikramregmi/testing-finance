package com.mobilebanking.model.mobile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnetPaymentResponse {
	
	@JsonProperty("responseDetail")
	private ResponseDetail responseDetail;
	
	@JsonProperty("transactionIdentifier")
	private String transactionIdentifier;

	public ResponseDetail getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(ResponseDetail responseDetail) {
		this.responseDetail = responseDetail;
	}

	public String getTransactionIdentifier() {
		return transactionIdentifier;
	}

	public void setTransactionIdentifier(String transactionIdentifier) {
		this.transactionIdentifier = transactionIdentifier;
	}

}
