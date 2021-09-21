package com.mobilebanking.model.error;

public class AmlFileTypeError {

	String Source; // ofci,.....
	String type; // extinction mismatch
	String message; // message to user
	boolean valid; //
	
	

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(String type) {
		this.type = type;
	}

}
