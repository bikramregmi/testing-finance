package com.mobilebanking.model;

public enum ServicesList {
	NTC_POSTPAID("Ntc Postpaid Topup"), NTC_PREPAID("Ntc Prepaid Topup"), NTC_CDMA_PREPAID("Ntc CDMA Prepaid Topup"),
	NTC_CDMA_POSTPAID("Ntc CDMA Topup"),
	NTC_ADSL_UNLIMITED("Ntc ADSL Unlimited"), NTC_ADSL_LIMITED("Adsl Limited");
	
	private final String value;
    private ServicesList(String value) {
        this.value = value;
    }

    public String getName() {
        return value;
    }
}
