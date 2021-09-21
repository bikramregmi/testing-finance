package com.serviceprovider.AirlinesResponse;

import java.util.HashMap;

public class AirliesGetDetails {

private InflAirRes airlinesRes;

private HashMap<String , String> myHash;

public InflAirRes getAirlinesRes() {
	return airlinesRes;
}

public void setAirlinesRes(InflAirRes airlinesRes) {
	this.airlinesRes = airlinesRes;
}

public HashMap<String, String> getMyHash() {
	return myHash;
}

public void setMyHash(HashMap<String, String> myHash) {
	this.myHash = myHash;
}

}
