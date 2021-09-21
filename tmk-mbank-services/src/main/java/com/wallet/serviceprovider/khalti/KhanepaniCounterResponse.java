package com.wallet.serviceprovider.khalti;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KhanepaniCounterResponse {
	
	private List<KhanepaniCounter> counters;
	
	private String status;

	public List<KhanepaniCounter> getCounters() {
		return counters;
	}

	public String getStatus() {
		return status;
	}

	public void setCounters(List<KhanepaniCounter> counters) {
		this.counters = counters;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
