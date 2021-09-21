package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.error.BankBranchError;
import com.mobilebanking.util.AuthenticationUtil;

@Component
public class BankBranchValidation {
	
	@Autowired
	private IBankBranchApi branchApi;
	
	private boolean valid;
	
	public BankBranchError validateBranch(BankBranchDTO bankBranchDto){
		BankBranchError error = new BankBranchError();
		valid = true;
		
		error.setName(checkName(bankBranchDto.getName()));
		error.setBranchId(checkBranchId(bankBranchDto.getBranchId()));
		error.setBranchCode(checkBranchCode(bankBranchDto.getBranchCode()));
		error.setAddress(checkBranchAddress(bankBranchDto.getAddress()));
		error.setState(checkState(bankBranchDto.getState()));
		error.setCity(checkCity(bankBranchDto.getCity()));
		if((bankBranchDto.getMobileNumber()!=null) && !(bankBranchDto.getMobileNumber().trim().equals(""))){
			error.setMobileNo(checkMobileNo(bankBranchDto.getMobileNumber()));
		}
		error.setEmail(checkEmail(bankBranchDto.getEmail()));
		error.setValid(valid);
		
		return error;
		
	}
	
	private String checkName(String name){
		if(name==null){
			valid=false;
			return "Invalid Name";
		}else if(name.trim().equals("")){
			valid=false;
			return "Invalid Name";
		}
		return null;
	}
	
	private String checkBranchId(String branchId){
		if(branchId==null){
			valid=false;
			return "Invalid Branch ID";
		}else if(branchId.trim().equals("")){
			valid=false;
			return "Invalid Branch ID";
		}else{
			BankBranchDTO branch = branchApi.findBranchByBranchId(branchId);
			if(branch!=null){
				valid = false;
				return "Branch ID Already Used";
			}
		}
		return null;
	}
	
	private String checkBranchCode(String branchCode){
		if(branchCode==null){
			valid=false;
			return "Invalid Branch Code";
		}else if(branchCode.trim().equals("")){
			valid=false;
			return "Invalid Branch Code";
		}else{
			BankBranchDTO branch = branchApi.findBranchByBankAndBranchCode(AuthenticationUtil.getCurrentUser().getAssociatedId(), branchCode);
			if(branch!=null){
				valid = false;
				return "Branch Code Already Used";
			}
		}
		return null;
	}
	
	private String checkBranchAddress(String address){
		if(address==null){
			valid=false;
			return "Invalid Address";
		}else if(address.trim().equals("")){
			valid=false;
			return "Invalid Address";
		}
		return null;
	}
	
	private String checkState(String state){
		if(state==null){
			valid=false;
			return "Invalid State";
		}else if(state.trim().equals("")){
			valid=false;
			return "Invalid State";
		}
		return null;
	}
	
	private String checkCity(String city){
		if(city==null){
			valid=false;
			return "Invalid City";
		}else if(city.trim().equals("")){
			valid=false;
			return "Invalid City";
		}
		return null;
	}
	
	private String checkMobileNo(String mobileNo){
		if(isNotANumber(mobileNo)){
			valid=false;
			return "Invalid Mobile Number";
		}else if(mobileNo.length()!=10){
			valid=false;
			return "Mobile Number Must Be 10 Digits Long";
		}
		return null;
	}
	
	private String checkEmail(String email){
		if(email==null){
			valid=false;
			return "Invalid Email";
		}else if(email.trim().equals("")){
			valid=false;
			return "Invalid Email";
		}
		return null;
	}
	
	private boolean isNotANumber(String string){
		try{
			Long.parseLong(string);
			return false;
		}catch(Exception e){
			return true;
		}
	}
}
