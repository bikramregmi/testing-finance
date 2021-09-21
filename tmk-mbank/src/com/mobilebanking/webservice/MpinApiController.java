package com.mobilebanking.webservice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.error.UserError;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.UserValidation;

@Controller
@RequestMapping("/api")
public class MpinApiController {

	@Autowired
	private UserValidation userValidation;
	
	@Autowired
	private IUserApi userApi;
	
	@RequestMapping(value="/changepin", method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> getServices(HttpServletRequest request) throws Exception {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER+","+Authorities.AUTHENTICATED)) {
				UserDTO userDto = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
				userDto.setPassword(request.getParameter("newmPin"));
				userDto.setRepassword(request.getParameter("remPin"));
				userDto.setOldPassword(request.getParameter("oldmPin"));
				UserError userError = new UserError();
				String newPin = request.getParameter("newmPin");
				userError = userValidation.passwordValidation(userDto);
			
				if(newPin.length()<4||newPin.length()>5){
					response.setCode(ResponseStatus.VALIDATION_FAILED);
					response.setStatus("VALIDATION_FAILED");
					response.setMessage("Please Enter Valid Pin Length. Pin number Should Be Of 4 or 5 digit");
					response.setDetails(userError);
					return new ResponseEntity<ResponseDTO> (response, HttpStatus.BAD_REQUEST);
				}
				
				if(userError.isValid()){
					userDto  = userApi.changePassword(userDto);
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					return new ResponseEntity<ResponseDTO> (response, HttpStatus.OK);
				}else{
					response.setCode(ResponseStatus.VALIDATION_FAILED);
					response.setStatus("VALIDATION_FAILED");
					response.setMessage(ResponseStatus.VALIDATION_FAILED.getValue());
					response.setDetails(userError);
					return new ResponseEntity<ResponseDTO> (response, HttpStatus.FORBIDDEN);
				}
			}
		}
		
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
	
}
