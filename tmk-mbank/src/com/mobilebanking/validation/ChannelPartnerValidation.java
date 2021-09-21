package com.mobilebanking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.ChannelPartnerDTO;
import com.mobilebanking.model.error.ChannelPartnerError;

@Component
public class ChannelPartnerValidation {

	@Autowired
	private IUserApi userApi;

	public ChannelPartnerError validateChannelPartner(ChannelPartnerDTO channelPartnerDTO) {
		ChannelPartnerError error = new ChannelPartnerError();
		boolean valid = true;
		String errorMessage = null;

		errorMessage = checkName(channelPartnerDTO.getName());
		if (errorMessage != null) {
			error.setName(errorMessage);
			valid = false;
		}
		errorMessage = checkOwner(channelPartnerDTO.getOwner());
		if (errorMessage != null) {
			error.setOwner(errorMessage);
			valid = false;
		}
		errorMessage = checkAddress(channelPartnerDTO.getAddress());
		if (errorMessage != null) {
			error.setAddress(errorMessage);
			valid = false;
		}
		errorMessage = checkState(channelPartnerDTO.getState());
		if (errorMessage != null) {
			error.setState(errorMessage);
			valid = false;
		}
		errorMessage = checkCity(channelPartnerDTO.getCity());
		if (errorMessage != null) {
			error.setCity(errorMessage);
			valid = false;
		}
		errorMessage = checkUsername(channelPartnerDTO.getUniqueCode());
		if (errorMessage != null) {
			error.setUniqueCode(errorMessage);
			valid = false;
		}
		errorMessage = checkPassword(channelPartnerDTO.getPassCode());
		if (errorMessage != null) {
			error.setPassCode(errorMessage);
			valid = false;
		} else {
			errorMessage = checkRePassword(channelPartnerDTO.getPassCode(), channelPartnerDTO.getRepassCode());
			if (errorMessage != null) {
				error.setRepassCode(errorMessage);
				valid = false;
			}
		}
		error.setValid(valid);
		return error;
	}

	private String checkName(String name) {
		if (name == null || name.trim().equals("")) {
			return "Invalid Channel Partner Name";
		}
		return null;
	}

	private String checkOwner(String owner) {
		if (owner == null || owner.trim().equals("")) {
			return "Invalid Channel Partner Owner";
		}
		return null;
	}

	private String checkAddress(String address) {
		if (address == null || address.trim().equals("")) {
			return "Invalid Channel Partner Address";
		}
		return null;
	}

	private String checkState(String state) {
		if (state == null || state.trim().equals("")) {
			return "Invalid State";
		}
		return null;
	}

	private String checkCity(String city) {
		if (city == null || city.trim().equals("")) {
			return "Invalid City";
		}
		return null;
	}

	private String checkUsername(String username) {
		if (username == null || username.trim().equals("")) {
			return "Invalid username";
		} else if (userApi.getUserByUserName(username) != null) {
			return "Username already used";
		}
		return null;
	}

	private String checkPassword(String password) {
		if (password == null || password.trim().equals("")) {
			return "Invalid Password";
		}
		return null;
	}

	private String checkRePassword(String password, String repassword) {
		if (!password.equals(repassword)) {
			return "Password does not match";
		}
		return null;
	}

}
