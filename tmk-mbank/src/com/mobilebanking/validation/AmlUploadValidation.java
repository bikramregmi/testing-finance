package com.mobilebanking.validation;

import com.mobilebanking.model.error.AmlFileTypeError;

public class AmlUploadValidation {
	
	public AmlFileTypeError agentValidation(String filetype, String filename, String originalfilename){
		
		AmlFileTypeError  amlfterror = new AmlFileTypeError();
		boolean valid=true;
		
		if(!filetype.equalsIgnoreCase(originalfilename.substring(originalfilename.lastIndexOf(".") + 1))){
			amlfterror.setMessage("file not supported");
			amlfterror.setSource(filename);
			valid=false;
		}
		
	
		amlfterror.setValid(valid);
		
		return amlfterror;
		
	}

}
