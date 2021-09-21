package com.mobilebanking.util;

public class AuthorityUtil {
	
	public static boolean isAgentOrSuperAgentOrAdmin(String authority){
		if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
				|| (authority.contains(Authorities.SUPER_AGENT) && authority.contains(Authorities.AUTHENTICATED))
				|| (authority.contains(Authorities.AGENT) && authority.contains(Authorities.AUTHENTICATED))) {
			return true;
		}
		else{
			return false;
		}
	
	}
	
	public static boolean isCustomer(String authority){
		if (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED)
				|| (authority.contains(Authorities.BENEFICIARY_CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))
				|| (authority.contains(Authorities.SENDER_CUSTOMER) && authority.contains(Authorities.AUTHENTICATED))){
			return true;
		}
		else{
			return false;
		}
	
	}

}
