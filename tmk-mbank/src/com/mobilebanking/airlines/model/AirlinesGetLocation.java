package com.mobilebanking.airlines.model;

import java.util.HashMap;

import com.serviceprovider.AirlinesResponse.Root;

public class AirlinesGetLocation {

	private Root root;
	
	private HashMap<String , String> myHash;

	public Root getRoot() {
		return root;
	}

	public void setRoot(Root root) {
		this.root = root;
	}

	public HashMap<String, String> getMyHash() {
		return myHash;
	}

	public void setMyHash(HashMap<String, String> myHash) {
		this.myHash = myHash;
	}
	
}
