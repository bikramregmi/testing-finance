package com.mobilebanking.validation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mobilebanking.api.IUserApi;
import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.UserError;
import com.mobilebanking.repositories.BankBranchRepository;
import com.mobilebanking.repositories.UserRepository;

public class UserValidation {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IUserApi userApi;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BankBranchRepository bankBranchRepository;

	public UserError userValidation(UserDTO userDTO) {
		UserError error = new UserError();
		boolean valid = true;
		if (userDTO.getOperation() != null) {
			if (userDTO.getOperation().equals("edit")) {

				if (userDTO.getStatus() == null) {
					logger.debug("Status Required==>");
					error.setStatus("Status Required");
					valid = false;
				}
				if (userDTO.getUserType() == null) {
					logger.debug("Address Required==>");
					error.setUserType("UserType Required");
					valid = false;
				}
				System.out.println("1 ERROR");
				error.setValid(valid);
				return error;
			}

			if (userDTO.getOperation().equals("password")) {
				if (userDTO.getPassword() == null || userDTO.getPassword().trim().equals("")) {
					logger.debug("Password Required==>");
					error.setPassword("Password Required");
					valid = false;
				}
				if (userDTO.getRepassword() == null || userDTO.getRepassword().trim().equals("")) {
					logger.debug("get Re password Required==>");
					error.setRepassword("Re Password Required");
					valid = false;
				}
				logger.debug("userDTO.getRepassword()==>" + userDTO.getRepassword());
				logger.debug("userDTO.getPassword()==>" + userDTO.getPassword());
				if (!userDTO.getRepassword().trim().equals(userDTO.getPassword().trim())) {
					logger.debug("Password Mismatch==>");
					error.setRepassword("Password Mismatch");
					valid = false;
				}
				System.out.println("2 ERROR");
				error.setValid(valid);
				return error;
			}
		}
		if (userDTO.getUserName() == null) {
			logger.debug("Username Required==>");
			error.setUserName("UserName Required");
			valid = false;
		} else {

			if (StringUtils.isNumeric(userDTO.getUserName())) {
				error.setUserName("UserName Should not be  numeric");
				valid = false;
			}

		}
		try {
			User user = userRepository.findByUsername(userDTO.getUserName());
			if (user != null) {
				error.setUserName("UserName Already Exists");
				valid = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.debug("password Required==>" + userDTO.getPassword());
		if (userDTO.getPassword() == null || userDTO.getPassword().trim().equals("")) {

			error.setPassword("Password Required");
			valid = false;
		}

		if (userDTO.getUserType() == null) {
			logger.debug("User Type Required==>");
			error.setUserType("UserType Required");
			valid = false;
		}

		System.out.println("3 ERROR");
		error.setValid(valid);
		return error;

	}

	public UserError validateUser(UserDTO userDto) {
		UserError error = new UserError();
		boolean valid = true;
		String errorMessage;

		errorMessage = checkUsername(userDto.getUserName());
		if (errorMessage != null) {
			error.setUserName(errorMessage);
			valid = false;
		}

		errorMessage = checkPassword(userDto.getPassword());
		if (errorMessage != null) {
			error.setPassword(errorMessage);
			valid = false;
		}

		errorMessage = checkRePassword(userDto.getPassword(), userDto.getRepassword());
		if (errorMessage != null) {
			error.setRepassword(errorMessage);
			valid = false;
		}

		errorMessage = checkAddress(userDto.getAddress());
		if (errorMessage != null) {
			error.setAddress(errorMessage);
			valid = false;
		}

		errorMessage = checkState(userDto.getState());
		if (errorMessage != null) {
			error.setState(errorMessage);
			valid = false;
		}

		errorMessage = checkCity(userDto.getCity());
		if (errorMessage != null) {
			error.setCity(errorMessage);
			valid = false;
		}

		errorMessage = checkUserType(userDto.getUserType());
		if (errorMessage != null) {
			error.setUserType(errorMessage);
			valid = false;
		}
        if(userDto.getUserType()!=UserType.ChannelPartner) {
		errorMessage = checkRole(userDto.getUserTemplate());
		if (errorMessage != null) {
			error.setRole(errorMessage);
			valid = false;
		}
        }
        logger.debug("validate error at add user : "+errorMessage);
		error.setValid(valid);
		return error;
	}

	private String checkUsername(String username) {
		if (username == null) {
			return "Invalid Username";
		} else if (username.trim().equals("")) {
			return "Invalid Username";
		} else {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				return "Username Already Exists";
			}
		}
		return null;
	}

	private String checkAddress(String address) {
		if (address == null) {
			return "Invalid Address";
		} else if (address.trim().equals("")) {
			return "Invalid Address";
		}
		return null;
	}

	private String checkState(String state) {
		if (state == null) {
			return "Select a State";
		} else if (state.trim().equals("")) {
			return "Select a State";
		}
		return null;
	}

	private String checkCity(String city) {
		if (city == null) {
			return "Select a City";
		} else if (city.trim().equals("")) {
			return "Select a City";
		}
		return null;
	}

	private String checkUserType(UserType userType) {
		if (userType == null) {
			return "Select a User Type";
		}
		return null;
	}

	private String checkRole(String role) {
		if (role == null) {
			return "Select a Role";
		} else if (role.trim().equals("")) {
			return "Select a Role";
		}
		return null;
	}

	private String checkPassword(String password) {
		if (password == null) {
			return "Invalid Password";
		} else if (password.trim().equals("")) {
			return "Invalid Password";
		}
		return null;
	}

	private String checkRePassword(String password, String rePassword) {
		if (!(password.equals(rePassword))) {
			return "Password Doesn't match";
		}
		return null;
	}

	public UserError passwordValidation(UserDTO userDTO) {
		UserError error = new UserError();
		boolean valid = true;
		String errorMessage;

		errorMessage = checkpasswordValidation(userDTO);
		if (errorMessage != null) {
			error.setPassword(errorMessage);
			valid = false;
		}
		errorMessage = checkoldValidation(userDTO);
		if (errorMessage != null) {
			error.setOldPassword(errorMessage);
			valid = false;
		}

		error.setValid(valid);
		return error;

	}

	public UserError passwordValidationByAdmin(UserDTO userDTO) {
		UserError error = new UserError();
		boolean valid = true;
		String errorMessage;

		errorMessage = checkpasswordValidation(userDTO);
		if (errorMessage != null) {
			error.setPassword(errorMessage);
			valid = false;
		}

		error.setValid(valid);
		return error;
	}

	private String checkoldValidation(UserDTO userDTO) {
		if (userDTO.getOldPassword() == null || userDTO.getOldPassword().trim().equals("")) {
			return "Incorrect Old Password";
		} else if (userDTO.getOldPassword().equals(userDTO.getPassword())) {
			return "New Password cannot be same as previous password";
		} else {
			boolean check = userApi.checkPassword(userDTO);
			if (check == false) {
				return "Incorrect Password";
			}
		}
		return null;
	}

	private String checkpasswordValidation(UserDTO userDTO) {
		if (userDTO.getPassword() == null) {
			return "Password Is Required";
		}
		if (userDTO.getPassword().trim().equals("")) {
			return "Password Is Required";
		}

		if (!userDTO.getPassword().equals(userDTO.getRepassword())) {
			return "Password And Repassword does not match";
		}
		return null;
	}
	
	public boolean checkDeleteValidation(Long id, User currentUser){			
		User userToDelete = userRepository.findOne(id);
		if(currentUser.getUserType().equals(UserType.Admin)){
			return true;
		}
		if(currentUser.getUserType().equals(UserType.Bank)){
			
			if(userToDelete.getUserType().equals(UserType.Bank)){
				if(userToDelete.getAssociatedId() == currentUser.getAssociatedId()){
					return true;
				}				
			}
			if(userToDelete.getUserType().equals(UserType.BankBranch)){
				
				BankBranch bankBranch = bankBranchRepository.findOne(userToDelete.getAssociatedId());
				
				if(bankBranch.getBank().getId() == currentUser.getAssociatedId()){
					return true;
				}				
			}
			return false;					
			
		}else{
			return false;
		}
	}

	public boolean changeBranchValidation(Long userId, User currentUser, Long branchId) {
		User user = userRepository.findOne(userId);
		if(user.getUserType() == UserType.BankBranch){
			BankBranch branch = bankBranchRepository.findOne(user.getAssociatedId());
			if(branch.getBank().getId() == currentUser.getAssociatedId()){
				branch = bankBranchRepository.findOne(branchId);
				if(branch.getBank().getId() == currentUser.getAssociatedId()){
					return true;
				}
			}
		}
		return false;
	}
}
