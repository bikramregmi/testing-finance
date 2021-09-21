/**
 * 
 */
package com.mobilebanking.model.error;

/**
 * @author bibek
 *
 */
public class MerchantServiceError {
	
	private boolean valid;
	
	private String service;
	
	private String merchant;
	
	private String uniqueIdentifier;
	
	private String url;
	
	private String labelName;
	
	private String labelMaxLength;
	
	private String labelMinLength;
	
	private String labelSample;
	
	private String labelPrefix;
	
	private String instructions;
	
	private String priceRange;
	
	private String notificationUrl;
	
	private String serviceCategory;
	
	private String minValue;
	
	private String maximumValue;
	
	private String labelSize;
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getLabelMaxLength() {
		return labelMaxLength;
	}

	public void setLabelMaxLength(String labelMaxLength) {
		this.labelMaxLength = labelMaxLength;
	}

	public String getLabelMinLength() {
		return labelMinLength;
	}

	public void setLabelMinLength(String labelMinLength) {
		this.labelMinLength = labelMinLength;
	}

	public String getLabelSample() {
		return labelSample;
	}

	public void setLabelSample(String labelSample) {
		this.labelSample = labelSample;
	}

	public String getLabelPrefix() {
		return labelPrefix;
	}

	public void setLabelPrefix(String labelPrefix) {
		this.labelPrefix = labelPrefix;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getNotificationUrl() {
		return notificationUrl;
	}

	public void setNotificationUrl(String notificationUrl) {
		this.notificationUrl = notificationUrl;
	}

	public String getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	
	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(String maximumValue) {
		this.maximumValue = maximumValue;
	}

	public String getLabelSize() {
		return labelSize;
	}

	public void setLabelSize(String labelSize) {
		this.labelSize = labelSize;
	}
	
}
