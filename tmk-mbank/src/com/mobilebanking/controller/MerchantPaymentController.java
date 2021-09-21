package com.mobilebanking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobilebanking.api.ICardlessOtpApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IMerchantPaymentRefactorApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.Hashes;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.TransactionResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.MerchantPaymentValidation;
import com.wallet.serviceprovider.paypoint.paypointResponse.WorldLinkPackage;

@Controller
public class MerchantPaymentController {

	@Autowired
	private IMerchantServiceApi merchantServiceApi;

	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentService;

	@Autowired
	private MerchantPaymentValidation merchantPaymentValidation;
	@Autowired
	private IMerchantPaymentRefactorApi merchantPaymentRefactor;
	
	@Autowired
	private ICardlessOtpApi cardlessApi;
	
	@Autowired
	private ICustomerBankAccountApi customerBankAccountApi;

	@RequestMapping(method = RequestMethod.GET, value = "/payment")
	public String merchantPayment(@RequestParam(value = "serviceId") String serviceId, ModelMap modelMap,
			HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AGENT) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_ADMIN) || authority.contains(Authorities.BANK_BRANCH_ADMIN)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				MerchantServiceDTO merchantService = merchantServiceApi.findServiceByUniqueIdentifier(serviceId);
				modelMap.put("serviceDTO", merchantService);
				if (serviceId.equals(StringConstants.SUBISU)) {
					return "MerchantPayment/Subisu";
				}
                if (serviceId.equals(StringConstants.WLINK_TOPUP)) {
					return "MerchantPayment/worldLink";
				} else if (serviceId.equals(StringConstants.NEA)) {
					return "nea/nea";
				}
				else if (serviceId.equals(StringConstants.SMART_CELL)||serviceId.equals(StringConstants.BROADTEL)||serviceId.equals(StringConstants.BROADLINK)||serviceId.equals(StringConstants.DISHHOME_PIN)||serviceId.equals(StringConstants.NET_TV)) {
					return "MerchantPayment/rechargepin";
				}else
					return "MerchantPayment/Topup";
			}
		}

		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/paymentservices")
	public String merchantPayment( ModelMap modelMap,
			HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.AGENT) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_ADMIN) || authority.contains(Authorities.BANK_BRANCH_ADMIN)
					|| authority.contains(Authorities.BANK_BRANCH)) {

				List<MerchantServiceDTO> merchantServiceList = merchantServiceApi.findAllMerchantService();
				modelMap.put("serviceDTO", merchantServiceList);
				
					return "Service/paymentServices";
			}
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/generalMerchantPayment")
	public String getService(ModelMap modelMap, HttpServletRequest request) {

		String serviceid = request.getParameter("serviceUnique");

		if (serviceid != null) {

			if (serviceid.trim().equals("ntc_prepaid_topup") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/NtcPrepaidTopup";
			} else if (serviceid.trim().equals("ntc_postpaid_topup") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/NtcPostpaidTopup";
			} else if (serviceid.trim().equals("dish_home_topup") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				// return "onlinepayment/dishhome/rechargebycasId";
				// MerchantServiceDto serviceDTO =
				// merchantServiceService.findServiceByUniqueIdentifier(serviceid);
				// modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/DishHomeTopup";
			}

			else if (serviceid.trim().equals("worldlink_online_topup")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				// return "onlinepayment/dishhome/rechargebycasId";
				// MerchantServiceDto serviceDTO =
				// merchantServiceService.findServiceByUniqueIdentifier(serviceid);
				// modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/worldLink";
			}

			else if (serviceid.trim().equals("ncell_prepaid_topup") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/NcellPrepaidTopup";
			} else if (serviceid.trim().equals("ncell_postpaid_topup") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/NcellPostpaidTopup";
			}

			else if (serviceid.trim().equals("ncell_prepaid_topuptest") || serviceid.trim().equals("service1")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/NcellPrepaidTopup";
			}

			if (serviceid.trim().equals("ntc_prepaid_topup") || serviceid.trim().equals("ntc_postpaid_topup")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/Topup";
			} else if (serviceid.trim().equals("dishHome")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "onlinepayment/dishhome/balanceEnquiry";
			} else if (serviceid.trim().equals("dishHomeRecharge")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "onlinepayment/dishhome/dishhomerecharge";
			} else if (serviceid.trim().equals("rechargebycasId")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "onlinepayment/dishhome/rechargebycasId";
			} else if (serviceid.trim().equals("unitedSolutionBalanceCheck")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "onlinepayment/unitedsolution/balancecheck";
			} else if (serviceid.trim().equals("epayOnlineService")) {
				MerchantServiceDTO serviceDTO = merchantServiceApi.findServiceByUniqueIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "onlinepayment/epay/onlinepayment";
			}
		}

		return "MerchantPayment/serviceList";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generalMerchantPayment")
	public ResponseEntity<Object> getService(HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			try {
				HashMap<String, String> hashMap = null;
				// HashMap<String, String> hash = new HashMap<>();
				String serviceId = request.getParameter("generalserviceUnique");
				String transactionId = request.getParameter("generaltransactionId");
				String amount;
				if (serviceId.equals(StringConstants.WLINK_TOPUP)) {
					amount = "0";
				} else
					amount = request.getParameter("generalamount");

				// Hashes hashes =
				// merchantPaymentValidation.generalMerchantValidation(serviceId,
				// transactionId, amount);
				Hashes hashes = new Hashes();
				HashMap<String, String> hashResponse = new HashMap<>();
				HashMap<String, String> myhash = new HashMap<>();
				hashes.setValid(true);
				if (hashes.isValid()) {
					if (serviceId.equals(StringConstants.WLINK_TOPUP) && amount.trim().equals("0")) {

						WorldLinkPackage worldLinkPackage = merchantPaymentRefactor.worldLinkCheck(serviceId,
								transactionId, Double.parseDouble(amount),

								AuthenticationUtil.getCurrentUser().getId());
						if (worldLinkPackage.getHashResponse().get("status").equals("success")) {
							responseDTO.setMessage("Package");
							responseDTO.setStatus(ResponseStatus.SUCCESS.getKey());
							worldLinkPackage.getHashResponse().put("userId", transactionId);
							responseDTO.setDetail(worldLinkPackage);
							return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
						} else {
							responseDTO.setStatus(ResponseStatus.FAILURE.getKey());
							responseDTO.setMessage(worldLinkPackage.getHashResponse().get("Result Message"));
							return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
						}

					} /*
						 * else if(hash!=null
						 * &serviceId.equals(StringConstants.WLINK_TOPUP )){
						 * hashResponse =
						 * merchantPaymentService.merchantPayment(serviceId,
						 * transactionId, Double.parseDouble(amount),
						 * AuthenticationUtil.getCurrentUser().getId(),hashMap);
						 * }
						 */

					else {
						HashMap<String, String> validationResponse = merchantPaymentValidation
								.merchantPaymentValidation(transactionId, serviceId, Double.parseDouble(amount),
										AuthenticationUtil.getCurrentUser().getId());

						if (validationResponse.get("message").equals("valid")) {

							hashResponse = merchantPaymentRefactor.merchantPayment(serviceId, transactionId,
									Double.parseDouble(amount), AuthenticationUtil.getCurrentUser().getId(), "",
									myhash);

						} else {
							transactionResponse.setMessage(validationResponse.get("message"));
							transactionResponse.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
							return new ResponseEntity<Object>(transactionResponse, HttpStatus.FORBIDDEN);
						}
					}

					if (hashResponse.get("status").equals("success")) {
						transactionResponse.setDate(hashResponse.get("transactionDate"));
						transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
						transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
						//added fields 26th-04-2018
						
						transactionResponse.setBankName((hashResponse.get("bankName")!=null)?hashResponse.get("bankName"):"");
						transactionResponse.setBranchName((hashResponse.get("bankBranch")!=null)?hashResponse.get("bankBranch"):"");
						transactionResponse.setIcon((hashResponse.get("serviceIcon")!=null)?hashResponse.get("serviceIcon"):"");
						
						//end added fields 26th-04-2018
						transactionResponse.setMessage("Service Accomplish");
						transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
						return new ResponseEntity<Object>(transactionResponse, HttpStatus.OK);
					} else if (hashResponse.get("status").equals("unknown")) {
						transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
						transactionResponse.setDate(hashResponse.get("transactionDate"));
						transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
						transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
						transactionResponse.setMessage("Technical Error Please Contact Administrator");
						return new ResponseEntity<Object>(transactionResponse, HttpStatus.CONFLICT);
					}

					else {
						responseDTO.setStatus(ResponseStatus.BAD_REQUEST.getKey());
						responseDTO.setMessage(hashResponse.get("Result Message"));
						return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
					}
				} else {
					responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
					responseDTO.setMessage("Request Not Valid");
					responseDTO.setDetail(hashes.getMyHash());
					return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				e.printStackTrace();
				responseDTO.setStatus(ResponseStatus.FAILURE.getKey());
				responseDTO.setMessage("Service Failed");
				return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		}
		responseDTO.setStatus(ResponseStatus.UNAUTHORIZED_USER.getKey());
		responseDTO.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
		return new ResponseEntity<Object>(responseDTO, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/wlinkPayment")
	public ResponseEntity<Object> wlinkPayment(HttpServletRequest request, HttpServletResponse response) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		try {
			// HashMap<String,String> hash = null;
			HashMap<String, String> hash = new HashMap<>();
			String serviceId = request.getParameter("generalserviceUnique");
			String transactionId = request.getParameter("generaltransactionId");
			
			hash.put("serviceCode", request.getParameter("serviceCode"));
			hash.put("Utility Code", request.getParameter("utilityCode"));
			hash.put("Bill Number", request.getParameter("billBumber"));
			hash.put("RefStan", request.getParameter("refStan"));
			hash.put("Commission Value", request.getParameter("commissionValue"));
			if(request.getParameter("nopackage").equals("false")){
				String packageId = request.getParameter("packageId");
			hash.put("packageId", packageId);
			}
			String test = request.getParameter("serviceCode");

			String amount = request.getParameter("generalamount");
			Hashes hashes = new Hashes();
			TransactionResponseDTO transactionResponse = new TransactionResponseDTO();
			// Hashes hashes =
			// merchantPaymentValidation.generalMerchantValidation(serviceId,
			// transactionId, amount);
			HashMap<String, String> validationResponse = merchantPaymentValidation.merchantPaymentValidation(
					transactionId, serviceId, Double.parseDouble(amount) / 100,
					AuthenticationUtil.getCurrentUser().getId());

			if (validationResponse.get("message").equals("valid")) {
				hashes.setValid(true);
			} else {
				transactionResponse.setMessage(validationResponse.get("message"));
				transactionResponse.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				return new ResponseEntity<Object>(transactionResponse, HttpStatus.FORBIDDEN);
			}

			HashMap<String, String> hashResponse = new HashMap<>();

			if (hashes.isValid()) {
				// hashResponse =
				// merchantPaymentRefactor.merchantPayment(serviceId,
				// transactionId, Double.parseDouble(amount),
				// AuthenticationUtil.getCurrentUser().getId(), hash);

				hashResponse = merchantPaymentService.merchantPayment(serviceId, transactionId,
						Double.parseDouble(amount) / 100, AuthenticationUtil.getCurrentUser().getId(), hash);

				if (hashResponse.get("status").equals("success")) {
					transactionResponse.setDate(hashResponse.get("transactionDate"));
					transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
					transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
              
					transactionResponse.setMessage("Service Accomplish");
					transactionResponse.setStatus(ResponseStatus.SUCCESS.getKey());
					return new ResponseEntity<Object>(transactionResponse, HttpStatus.OK);
				} else if (hashResponse.get("status").equals("unknown")) {
					transactionResponse.setStatus(ResponseStatus.AMBIGUOUS_TRANSACTION.getKey());
					transactionResponse.setDate(hashResponse.get("transactionDate"));
					transactionResponse.setServiceTo(hashResponse.get("ServiceTo"));
					transactionResponse.setTransactionIdentifier(hashResponse.get("transactionIdentifier"));
					transactionResponse.setMessage("Technical Error Please Contact Administrator");
					return new ResponseEntity<Object>(transactionResponse, HttpStatus.CONFLICT);
				}

				else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST.getKey());
					responseDTO.setMessage(hashResponse.get("Result Message"));
					return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED.getKey());
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetail(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE.getKey());
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.BAD_REQUEST);
		}
	}

	// for dish homes balanceenquery

	@RequestMapping(method = RequestMethod.GET, value = "/bussewa")
	public String dishHome(ModelMap modelMap, HttpServletRequest request) throws Exception {

//		merchantPaymentService.bussewa();
		;
//		customerApi.getMiniStatementOfUser("9805230034", "0010011546846546546");
		return "onlinepayment/bussewa/bussewa";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/testotp")
	public String tette(ModelMap modelMap, HttpServletRequest request) {

		// merchantPaymentService.dishHome();;
		try {
			//TODO Get Bank
			CustomerBankAccountDTO customerBankAcocunt = customerBankAccountApi.findCustomerBankAccountByAccountNumberAndBank("001RD6",1L);
//			customerApi.getCustomerBankBalance("1997162448", "001RD6");
			cardlessApi.saveAndShareOTP(customerBankAcocunt, "9803001069", 1025.0, 1L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "onlinepayment/bussewa/bussewa";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/epay")
	public String bussewa(ModelMap modelMap, HttpServletRequest request) {

		// merchatPaymentService.epay();;

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
		String formattedDate = dateFormat.format(new Date());
		System.out.println(formattedDate);

		return "onlinepayment/epay/epay";
	}

}
