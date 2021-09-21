package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Group")
public class Group {
	@XmlElement(name="Key")
	  private String Key;
	@XmlElement(name="Order")
	    private String Order;

		public String getKey() {
			return Key;
		}

		public void setKey(String key) {
			Key = key;
		}

		public String getOrder() {
			return Order;
		}

		public void setOrder(String order) {
			Order = order;
		}
	    
}
