package com.mobilebanking.api;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONException;

import com.mobilebanking.entity.User;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.util.ClientException;

public interface IUserApi {

	User saveUser(UserDTO userDTO);

	List<UserDTO> getAllUser();

	UserDTO getUserWithId(long userId);

	List<UserDTO> findUser();

	List<UserDTO> findAllUserExceptAdmin();

	List<UserDTO> findAllUserExceptDefaultAdmin();

	void editUser(UserDTO userDTO) throws IOException, JSONException;

	Boolean deleteUser(Long parseLong);

	String generateSecretKey(String clientId, String accessKey) throws ClientException;

	User findByAccessAndSecretKey(String accessKey, String secretKey);

	void changePassword(long userId,String newPassword) throws IOException , JSONException;
	
	UserDTO getUserByUserName(String userName);
	
	Object userDetector(UserType userType, String name, long associatedId);
	
	void createUser(String userName,UserType userType,String authorities,String password,Status status, TimeZone timeZone,long associatedId);

	List<UserDTO> findUserByRole(String role);
	
	UserDTO changePassword(UserDTO userDto) throws Exception;

	UserDTO updateUser(UserDTO userDto);

	boolean checkPassword(UserDTO userDto);

	UserDTO findUserByAssociatedIdAndUserType(Long id, UserType customer);
	
	String verifyCode(String verificationCode, String userName);

	String getUserToken(String username, String clientId);
	
	boolean verifyUserAsPerbank(String userName, String clientId);

	List<UserDTO> findBranchUserByBank(long bankId);

	List<UserDTO> findUserListByAssociatedIdAndUserType(long associatedId, UserType userType);

	UserDTO changePasswordByAdmin(UserDTO userDto);

	Boolean setDeviceToken(String parameter, Long id, String serverKey);

	List<UserDTO> getUserByUserNameConCat(String data);

	boolean checkDeviceToken(Long userId);

	boolean changeBranch(Long userId, Long branchId);

}
