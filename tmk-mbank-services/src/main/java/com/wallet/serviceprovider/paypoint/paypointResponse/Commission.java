package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
public class Commission {
	@XmlAttribute(name ="type")
	 private String type;
	@XmlAttribute(name ="val")
	  private String val;
	@XmlAttribute(name ="op")
	  private String op;
	@XmlAttribute(name ="paysource")
	  private String paySource;
	@XmlValue
	  private String content;
	
	  public Commission() {}
	  
	  public String getType() { return type; }
	  
	  public void setType(String type) {
	    this.type = type;
	  }
	  
	  public String getVal() { return val; }
	  
	  public void setVal(String val) {
	    this.val = val;
	  }
	  
	  public String getOp() { return op; }
	  
	  public void setOp(String op) {
	    this.op = op;
	  }
	  
	  public String getPaySource() { return paySource; }
	  
	  public void setPaySource(String paySource) {
	    this.paySource = paySource;
	  }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
