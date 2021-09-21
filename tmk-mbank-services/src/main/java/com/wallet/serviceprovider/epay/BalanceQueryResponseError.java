package com.wallet.serviceprovider.epay;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ERROR")
public class BalanceQueryResponseError {

	private String sqlcode;
	private String sqlerrm;
	
	public String getSqlcode() {
		return sqlcode;
	}
	@XmlAttribute(name="SQLCODE")
	public void setSqlcode(String sqlcode) {
		this.sqlcode = sqlcode;
	}
	public String getSqlerrm() {
		return sqlerrm;
	}
	@XmlAttribute(name="SQLERRM")
	public void setSqlerrm(String sqlerrm) {
		this.sqlerrm = sqlerrm;
	}
	
/*	
	 @Override
	    public String toString() {
	        return "Error{" +
	                "sqlcode=" + sqlcode +
	                ", sqlerrm='" + sqlerrm + '\'' +
	                '}';
	    }*/
	
}
