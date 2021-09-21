package com.wallet.serviceprovider.bussewa;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRouteList {
	private String status;
	private List<String> routes;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getRoutes() {
		return routes;
	}

	public void setRoutes(List<String> routes) {
		this.routes = routes;
	}

	@Override
	public String toString() {
		return "Demo [status=" + status + ", routes=" + routes + "]";
	}

}