/*package com.wallet.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionOfElements;

@Entity
public class DishHome extends AbstractEntity<Long> {

	private static final long serialVersionUID = -8285212642376532658L;

	@CollectionOfElements
	@JoinTable(name = "Dish_Service_Request")
	private Map<String, String> requestDetail = new HashMap<String, String>();

	@CollectionOfElements
	@JoinTable(name = "Dish_Service_Response")
	private Map<String, String> responseDetail = new HashMap<String, String>();

	public Map<String, String> getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(Map<String, String> requestDetail) {
		this.requestDetail = requestDetail;
	}

	public Map<String, String> getResponseDetail() {
		return responseDetail;
	}

	public void setResponseDetail(Map<String, String> responseDetail) {
		this.responseDetail = responseDetail;
	}

	
}
*/