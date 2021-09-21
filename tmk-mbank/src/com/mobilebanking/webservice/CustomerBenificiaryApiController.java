package com.mobilebanking.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.BeneficiaryDTO;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.CustomerStatus;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.ClientException;
import com.mobilebanking.util.StringConstants;

@Controller
@RequestMapping("/api")
public class CustomerBenificiaryApiController {

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@Autowired
	private IBankBranchApi branchApi;

	@RequestMapping(value = "/addcustomerbeneficiary", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> addRecipient(HttpServletRequest request,
			@RequestBody BeneficiaryDTO beneficiaryDTO) {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		String clientId = request.getHeader("client");

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {

				try {
					CustomerDTO customer = customerApi
							.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					if (customer.getState() != null && customer.getStatus().equals(CustomerStatus.Blocked)) {
						response.setCode(ResponseStatus.CUSTOMER_BLOCKED);
						response.setStatus("FAILURE");
						response.setMessage(StringConstants.CUSTOMER_BLOCKED.replace("{bank}", ""));
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
					}
					BankBranchDTO branch = branchApi.findBranchById(beneficiaryDTO.getBankBranchId());
					List<CustomerBankAccountDTO> customerBankAccountList = customerBankAccountApi.findCustomerBankAccountByCustomer(customer.getUniqueId());
					if (customerBankAccountList != null) {
						for (CustomerBankAccountDTO customerBankAccount : customerBankAccountList) {
							String accountNo = branch.getBranchCode() + beneficiaryDTO.getAccountNumber();
							if (customerBankAccount.getAccountNumber().equals(accountNo)) {
								response.setCode(ResponseStatus.FAILURE);
								response.setStatus("FAILURE");
								response.setMessage("You cannot add your own account as Beneficiary");
								return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
							}
						}
					}
					beneficiaryDTO = customerApi.saveBenificiary(beneficiaryDTO, customer);
					if (beneficiaryDTO.isBeneficiaryFlag()) {
						response.setCode(ResponseStatus.FAILURE);
						response.setStatus("FAILURE");
						response.setMessage("Beneficiary Already Exist");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
					}
					List<BeneficiaryDTO> revicevers = customerApi
							.listBenificiary(AuthenticationUtil.getCurrentUser().getAssociatedId(), clientId);
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage(ResponseStatus.SUCCESS.getValue());
					response.setDetails(revicevers);
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/editcustomerbeneficiary", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> editRecipient(HttpServletRequest request,
			@RequestBody BeneficiaryDTO beneficiaryDTO) {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		String clientId = request.getHeader("client");

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<CustomerBankAccountDTO> customerBankAccountList = customerBankAccountApi.findCustomerBankAccountByCustomer(customer.getUniqueId());
					BankBranchDTO branch = branchApi.findBranchById(beneficiaryDTO.getBankBranchId());
					if (customerBankAccountList != null) {
						for (CustomerBankAccountDTO customerBankAccount : customerBankAccountList) {
							if (customerBankAccount.getAccountNumber().equals(branch.getBranchCode() + beneficiaryDTO.getAccountNumber())) {
								response.setCode(ResponseStatus.FAILURE);
								response.setStatus("FAILURE");
								response.setMessage("You cannot add your own account as Beneficiary");
								return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
							}
						}
					}

					beneficiaryDTO = customerApi.editBenificiary(beneficiaryDTO, customer);
					if (beneficiaryDTO.isBeneficiaryFlag()) {
						response.setCode(ResponseStatus.FAILURE);
						response.setStatus("FAILURE");
						response.setMessage("Beneficiary Already Exist");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.CONFLICT);
					}
					List<BeneficiaryDTO> revicevers = customerApi
							.listBenificiary(AuthenticationUtil.getCurrentUser().getAssociatedId(), clientId);
					response.setCode(ResponseStatus.SUCCESS);
					response.setStatus("SUCCESS");
					response.setMessage("Beneficiary was sucessfully edited");
					response.setDetails(revicevers);
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/deletecustomerbeneficiary", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> deleteRecipient(HttpServletRequest request,
			@RequestParam("benificiaryId") Long benificiaryId) {
		ResponseDTO response = new ResponseDTO();

		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		String clientId = request.getHeader("client");

		// customerValidation.customerValidation(customerDTO);
		// String username = request.getParameter("username");

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {

				try {
					// validate the account number of sender
					// CustomerDTO customer =
					// customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					// UserDTO username =
					// userApi.getUserByUserName(customerDTO.getCreatedBy());
					// if(!customer.getMobileNumber().equals(customerDTO.getCreatedBy())){
					// response.setCode(ResponseStatus.USERNAME_NOT_VALID);
					// response.setStatus("FAILURE");
					// response.setMessage(ResponseStatus.USERNAME_NOT_VALID.getValue());
					// response.setDetails(new ArrayList<>());
					// return new
					// ResponseEntity<ResponseDTO>(response,HttpStatus.BAD_REQUEST);
					// }
					CustomerDTO customer = customerApi
							.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					boolean delete = customerApi.deleteBenificiary(benificiaryId);

					if (delete) {
						List<BeneficiaryDTO> revicevers = customerApi
								.listBenificiary(AuthenticationUtil.getCurrentUser().getAssociatedId(), clientId);
						response.setCode(ResponseStatus.SUCCESS);
						response.setStatus("SUCCESS");
						response.setMessage("Beneficiary was deleted Succesfully");
						response.setDetails(revicevers);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}
				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.EXPECTATION_FAILED);
				}
			}
		}
		response.setCode(ResponseStatus.UNAUTHORIZED_USER);
		response.setStatus("FAILURE");
		response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		response.setDetails(new ArrayList<>());
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/getbeneficiary", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> getCustomerDetails(HttpServletRequest request) throws ClientException {
		ResponseDTO response = new ResponseDTO();
		if (request.getHeader("Authorization") == null) {
			response.setCode(ResponseStatus.FAILURE);
			response.setStatus("FAILURE");
			response.setDetails(new ArrayList<>());
			response.setMessage("Authorization not valid or empty");
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
		}
		// String username = request.getParameter("username");

		if (request.getHeader("client") == null) {
			response.setCode(ResponseStatus.CLIENT_REQUIRED);
			response.setStatus("FAILURE");
			response.setMessage(ResponseStatus.CLIENT_REQUIRED.getValue());
			;
			response.setDetails(new ArrayList<>());
			return new ResponseEntity<ResponseDTO>(response, HttpStatus.FORBIDDEN);
		}
		String clientId = request.getHeader("client");

		CustomerDTO customer = customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					// customer =
					// customerApi.getCustomerById(AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<BeneficiaryDTO> revicevers = customerApi
							.listBenificiary(AuthenticationUtil.getCurrentUser().getAssociatedId(), clientId);
					if (revicevers == null || revicevers.isEmpty()) {
						response.setStatus("FAILED");
						response.setCode(ResponseStatus.FAILURE);
						response.setMessage("NO BENEFICIARY AVAILABLE");
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					} else {
						response.setStatus("SUCCCESS");
						response.setCode(ResponseStatus.SUCCESS);
						response.setMessage(ResponseStatus.SUCCESS.getValue());
						response.setDetails(revicevers);
						return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
					}

				} catch (Exception e) {
					response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
					response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
					response.setDetails(new ArrayList<>());
					e.printStackTrace();
					return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
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
