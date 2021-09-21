package com.mobilebanking.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="WHOLE")
public class AmlElementEuDTO {
	
	protected List<Element> others;
	//@XmlElementWrapper(name="user")
	@XmlAnyElement(lax=true)
	public List<Element>  getOthers() {
		if( others == null ) others = new ArrayList<Element>();
		return others;
	}


	public void setOthers(List  others) {
		this.others = others;
	}

}
