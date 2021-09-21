package com.wallet.serviceprovider.paypoint.paypointResponse;

import java.util.HashMap;
import java.util.List;
import com.wallet.serviceprovider.paypoint.paypointResponse.Package;
public class WorldLinkPackage {

	HashMap<String,String> hashResponse;
	
	List<Package> packages;

	public HashMap<String, String> getHashResponse() {
		return hashResponse;
	}

	public void setHashResponse(HashMap<String, String> hashResponse) {
		this.hashResponse = hashResponse;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}
	
}
