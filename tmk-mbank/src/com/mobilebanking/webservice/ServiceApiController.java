/**
 * 
 */
package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;

/**
 * @author bibek
 *
 */
@Controller
@RequestMapping("/api")
public class ServiceApiController {
	
	@Autowired
	private IMerchantServiceApi merchantServiceApi;
	@Autowired
	private ICustomerApi customerApi;
	
	@RequestMapping(value="/services", method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getServices(HttpServletRequest request) throws ClientException {
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
				CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
				if (customer.isAppVerification()) {
					List<MerchantServiceDTO> services = merchantServiceApi.findMerchantServiceByStatus(Status.Active);
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(services);
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
				}
				
				response.setCode(ResponseStatus.ACCOUNT_NOT_VERIFIED);
				response.setStatus("FAILURE");
				response.setMessage(ResponseStatus.ACCOUNT_NOT_VERIFIED.getValue());
				response.setDetails(new ArrayList<>());
				return new ResponseEntity<ResponseDTO> (response, HttpStatus.FORBIDDEN);
			}
		}
		
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}
}
