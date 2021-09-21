/**
 * 
 */
package com.mobilebanking.model.error;

/**
 * @author user
 *
 */
public class BankOAuthClientError {
	
	private String redirectUrl;
	
	private String bank;
	
	private boolean valid;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
