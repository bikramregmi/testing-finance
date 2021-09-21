package com.wallet.serviceprovider.paypoint.paypointResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Company")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

	@XmlElement(name="Name")
	private String Name;
	@XmlElement(name="Key")
    private String Key;
	@XmlElement(name="Order")
    private String Order;
	@XmlElement(name="ID")
    private String ID;
	@XmlElement(name="Group")
    private Group Group;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

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

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public Group getGroup() {
		return Group;
	}

	public void setGroup(Group group) {
		Group = group;
	}
    
    
	
}

