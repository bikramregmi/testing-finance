package com.mobilebanking.fcm;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FcmResponse {

	@JsonProperty("multicast_id")
	private Integer multicastId;
	@JsonProperty("success")
	private Integer success;
	@JsonProperty("failure")
	private Integer failure;
	@JsonProperty("canonical_ids")
	private Integer canonicalIds;
	@JsonProperty("results")
	private List<Result> results = null;

	@JsonProperty("multicast_id")
	public Integer getMulticastId() {
	return multicastId;
	}

	@JsonProperty("multicast_id")
	public void setMulticastId(Integer multicastId) {
	this.multicastId = multicastId;
	}

	@JsonProperty("success")
	public Integer getSuccess() {
	return success;
	}

	@JsonProperty("success")
	public void setSuccess(Integer success) {
	this.success = success;
	}

	@JsonProperty("failure")
	public Integer getFailure() {
	return failure;
	}

	@JsonProperty("failure")
	public void setFailure(Integer failure) {
	this.failure = failure;
	}

	@JsonProperty("canonical_ids")
	public Integer getCanonicalIds() {
	return canonicalIds;
	}

	@JsonProperty("canonical_ids")
	public void setCanonicalIds(Integer canonicalIds) {
	this.canonicalIds = canonicalIds;
	}

	@JsonProperty("results")
	public List<Result> getResults() {
	return results;
	}

	@JsonProperty("results")
	public void setResults(List<Result> results) {
	this.results = results;
	}

}
