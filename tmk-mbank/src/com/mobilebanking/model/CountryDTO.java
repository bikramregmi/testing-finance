package com.mobilebanking.model;

import java.util.List;

public class CountryDTO {
	
	private Long id;
	
	private String name;
	
	private String isoTwo;
	
	private String isoThree;
	
	private String dialCode;	
	
	private String status;
	
	private String currencyCode;
	
	private CurrencyDTO currencyDTO;
	
	private List<CurrencyDTO> secondaryCurrencyDTO;
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsoTwo() {
		return isoTwo;
	}

	public void setIsoTwo(String isoTwo) {
		this.isoTwo = isoTwo;
	}

	public String getIsoThree() {
		return isoThree;
	}

	public void setIsoThree(String isoThree) {
		this.isoThree = isoThree;
	}

	public String getDialCode() {
		return dialCode;
	}

	public void setDialCode(String dialCode) {
		this.dialCode = dialCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public CurrencyDTO getCurrencyDTO() {
		return currencyDTO;
	}

	public void setCurrencyDTO(CurrencyDTO currencyDTO) {
		this.currencyDTO = currencyDTO;
	}

	public List<CurrencyDTO> getSecondaryCurrencyDTO() {
		return secondaryCurrencyDTO;
	}

	public void setSecondaryCurrencyDTO(List<CurrencyDTO> secondaryCurrencyDTO) {
		this.secondaryCurrencyDTO = secondaryCurrencyDTO;
	}
	
	

}
