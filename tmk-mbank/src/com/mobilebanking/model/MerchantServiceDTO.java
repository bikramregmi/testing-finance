/**
 * 
 */
package com.mobilebanking.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author bibek
 *
 */
public class MerchantServiceDTO {
	
	private long id;
	
	private String url;
	
	private String merchant;
	
	private String uniqueIdentifier;
	
	private String service;
	
	private Status status;
	
	private String labelName;
	
	private String labelMaxLength;
	
	private String labelMinLength;
	
	private String labelSize;
	
	private String labelSample;
	
	private String labelPrefix;
	
	private String instructions;
	
	private boolean fixedlabelSize;
	
	private boolean priceInput;
	
	private String priceRange;
	
	private String notificationUrl;
	
	private Double minValue;
	
	private Double maxValue;
	
	private MultipartFile file;
	
	private String icon;
	
	private long categoryId;
	
	private String serviceCategoryName;
	
	private Long count;
	
	private boolean webView;
	
	public String getServiceCategoryName() {
		return serviceCategoryName;
	}

	public void setServiceCategoryName(String serviceCategoryName) {
		this.serviceCategoryName = serviceCategoryName;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public boolean isPriceInput() {
		return priceInput;
	}

	public void setPriceInput(boolean priceInput) {
		this.priceInput = priceInput;
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

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getLabelSize() {
		return labelSize;
	}

	public void setLabelSize(String labelSize) {
		this.labelSize = labelSize;
	}

	public boolean isFixedlabelSize() {
		return fixedlabelSize;
	}

	public void setFixedlabelSize(boolean fixedlabelSize) {
		this.fixedlabelSize = fixedlabelSize;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public boolean getWebView() {
		return webView;
	}

	public void setWebView(boolean webView) {
		this.webView = webView;
	}

}
