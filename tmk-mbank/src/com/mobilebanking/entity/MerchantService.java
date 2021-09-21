/**
 * 
 */
package com.mobilebanking.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Entity
@Table(name="merchantServices")
public class MerchantService extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	/*@ManyToOne(fetch=FetchType.EAGER)
	private Merchant merchant;*/
	
	@Column
	private String service;
	
	@Column
	private String notificationUrl;
	
	@Column
	private Double minValue;
	
	@Column
	private Double maximumValue;
	
	@Column
	private String url;
	
	@Column
	private String uniqueIdentifier;
	
	@Column
	private Status status;
	
	@Column
	private String labelName;
	
	@Column
	private String labelMaxLength;
	
	@Column
	private String labelMinLength;
	
	@Column
	private String labelSample;
	
	@Column
	private String labelPrefix;
	
	@Column
	private String instructions;
	
	@Column
	private boolean priceInput;
	
	@Column
	private String priceRange;
	
	@Column
	private String accountNumber;
	
	@Column
	private boolean fixedlabelSize;
	
	@Column
	private String labelFizedSize;
	
	@OneToOne 
	private Document documents;
	
	@ManyToOne
	private ServiceCategory serviceCatagory;
	
	@ElementCollection
	private Set<MerchantManager> merchantManagers = new HashSet<MerchantManager>();
	
	private Boolean webView;
	
//	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "merchantServices")
//	@Column(nullable = true)
//	List<ServiceCategory> serviceCatagory;

	
	/*public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}*/

	public String getService() {
		return service;
	}

	public void setServices(String service) {
		this.service = service;
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

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setService(String service) {
		this.service = service;
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

	public Set<MerchantManager> getMerchantManagers() {
		return merchantManagers;
	}

	public void setMerchantManagers(Set<MerchantManager> merchantManagers) {
		this.merchantManagers = merchantManagers;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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


	public Document getDocuments() {
		return documents;
	}

	public void setDocuments(Document documents) {
		this.documents = documents;
	}

	public Double getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(Double maximumValue) {
		this.maximumValue = maximumValue;
	}

	public ServiceCategory getServiceCatagory() {
		return serviceCatagory;
	}

	public void setServiceCatagory(ServiceCategory serviceCatagory) {
		this.serviceCatagory = serviceCatagory;
	}

	public boolean isFixedlabelSize() {
		return fixedlabelSize;
	}

	public void setFixedlabelSize(boolean fixedlabelSize) {
		this.fixedlabelSize = fixedlabelSize;
	}

	public String getLabelFizedSize() {
		return labelFizedSize;
	}

	public void setLabelFizedSize(String labelFizedSize) {
		this.labelFizedSize = labelFizedSize;
	}

	public Boolean getWebView() {
		return webView;
	}

	public void setWebView(Boolean webView) {
		this.webView = webView;
	}

	

}
