package com.mobilebanking.model;

public enum AmlList {

	
	ofac ("xml"),ofsi("xls"),
	eucl ("xml"),uncl("xml"),aucl("xls");
	
	
	
	
	
	
	
    private final String name;
    private AmlList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
	
	
}
