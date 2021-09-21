package com.wallet.serviceprovider.bussewa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class PaymentConfirmation {

	private String securityCode;
	private String refId;
	private String ticketSerialNumber;
	private String signature;
	
	//signature = securityCode + refId + ticketSerailNumber +signature_code decrypted into SHA-256

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}

	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString() {
		return "PaymentConfirmation [securityCode=" + securityCode + ", refId=" + refId + ", ticketSerialNumber="
				+ ticketSerialNumber + ", signature=" + signature + "]";
	}

}
