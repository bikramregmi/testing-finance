package com.wallet.serviceprovider.epay;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="document")
public class BalanceQueryResponse {

	private RequestQueryResponse KKM_PG_GATE;

	public RequestQueryResponse getKkmPgGate() {
		return KKM_PG_GATE;
	}
	@XmlElement
	public void setKkmPgGate(RequestQueryResponse KKM_PG_GATE) {
		this.KKM_PG_GATE = KKM_PG_GATE;
	}
	
	
	
}
