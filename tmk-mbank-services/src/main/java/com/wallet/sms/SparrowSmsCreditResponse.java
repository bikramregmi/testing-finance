/**
 * 
 */
package com.wallet.sms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author bibek
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SparrowSmsCreditResponse {
	
	double credits_consumed;
	double last_balance_added;
	String response_code;
	double credits_available;
	double minimum_credit;
	public double getCredits_consumed() {
		return credits_consumed;
	}
	public void setCredits_consumed(double credits_consumed) {
		this.credits_consumed = credits_consumed;
	}
	public double getLast_balance_added() {
		return last_balance_added;
	}
	public void setLast_balance_added(double last_balance_added) {
		this.last_balance_added = last_balance_added;
	}
	public String getResponse_code() {
		return response_code;
	}
	public void setResponse_code(String response_code) {
		this.response_code = response_code;
	}
	public double getCredits_available() {
		return credits_available;
	}
	public void setCredits_available(double credits_available) {
		this.credits_available = credits_available;
	}
	public double getMinimum_credit() {
		return minimum_credit;
	}
	public void setMinimum_credit(double minimum_credit) {
		this.minimum_credit = minimum_credit;
	}
}
