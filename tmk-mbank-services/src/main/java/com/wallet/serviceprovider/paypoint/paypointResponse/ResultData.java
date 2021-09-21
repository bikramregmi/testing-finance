package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ResultData")
public class ResultData {
	
	@XmlElement(name = "DebtInfo")
	private DebtInfo debtInfo;

	@XmlElement(name = "InvoiceInfo")
    private InvoiceInfo invoiceInfo;
	
	@XmlElement(name = "Voucher")
	private Voucher voucher;

	public Voucher getVoucher() {
		return voucher;
	}

	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}

	public DebtInfo getDebtInfo() {
		return debtInfo;
	}

	public void setDebtInfo(DebtInfo debtInfo) {
		this.debtInfo = debtInfo;
	}

	public InvoiceInfo getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(InvoiceInfo invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}


}
