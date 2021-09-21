/*package com.wallet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wallet.api.IDishHomeApi;
import com.wallet.api.IMerchantPaymentApi;
import com.wallet.api.IPaymentCatagoryApi;
import com.wallet.api.IServicesApi;
import com.wallet.model.DishHomeDto;
import com.wallet.model.Hashes;
import com.wallet.model.PaymentCatagoryDTO;
import com.wallet.model.ServicesDTO;
import com.wallet.model.mobile.ResponseDTO;
import com.wallet.model.mobile.ResponseStatus;
import com.wallet.util.AuthenticationUtil;
import com.wallet.util.Authorities;
import com.wallet.validation.MerchantPaymentValidation;

@Controller
public class MerchantPaymentController {

	@Autowired
	private IPaymentCatagoryApi paymentCatagory;

	@Autowired
	private IServicesApi serviceApi;

	@Autowired
	private IMerchantPaymentApi merchantPaymentApi;
	
	@Autowired
	private IDishHomeApi dishHomeApi;

	@Autowired
	private MerchantPaymentValidation merchantPaymentValidation;
	
	@RequestMapping(method = RequestMethod.GET, value = "/paypoint")
	public String paypointPayment(Model model, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.CUSTOMER) && authority.contains(Authorities.AUTHENTICATED)) {
				try{
					String serviceid = request.getParameter("serviceUnique");
					System.out.println("Unique Service:"+serviceid);
					return "redirect:/";
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/eprabhu")
	public void eprabhuPayment(ModelMap modelMap, HttpServletRequest request) {

		merchantPaymentApi.eprabhu();

	}

	@RequestMapping(method = RequestMethod.GET, value = "/payment")
	public String merchantPayment(ModelMap modelMap, HttpServletRequest request) {

		modelMap.put("paymentCatagory", paymentCatagory.findAllPaymentCatagoryDTO());

		return "MerchantPayment/payment";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ServiceListByMethod")
	public String ServiceListByMethod(ModelMap modelMap, HttpServletRequest request, @RequestParam("id") long id) {

		PaymentCatagoryDTO pc = paymentCatagory.findById(id);

		modelMap.put("paymentCatagoryDTO", paymentCatagory.findById(id));

		return "MerchantPayment/ServiceList";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/generalMerchantPayment")
	public String getService(ModelMap modelMap, HttpServletRequest request) {

		String serviceid = request.getParameter("serviceUnique");

		if (serviceid != null) {
			if (serviceid.trim().equals("ntc_prepaid_topup") || serviceid.trim().equals("ntc_postpaid_topup")) {
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "MerchantPayment/Topup";
			}
			else if(serviceid.trim().equals("dishHome")){
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "dishhome/balanceEnquiry";
			}else if(serviceid.trim().equals("dishHomeRecharge")){
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "dishhome/dishhomerecharge";
			}else if(serviceid.trim().equals("rechargebycasId")){
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "dishhome/rechargebycasId";
			}else if(serviceid.trim().equals("unitedSolutionBalanceCheck")){
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "unitedsolution/balancecheck";
			}else if(serviceid.trim().equals("epayOnlineService")){
				ServicesDTO serviceDTO = serviceApi.getServicesByIdentifier(serviceid);
				modelMap.put("ServicesDTO", serviceDTO);
				return "epay/onlinepayment";
			}
		}
		

		return "MerchantPayment/ServiceList";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/generalMerchantPayment")
	public ResponseEntity<Object> getService(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			String serviceId = request.getParameter("generalserviceUnique");
			String transactionId = request.getParameter("generaltransactionId");
			String amount = request.getParameter("generalamount");

			Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			if (hashes.isValid()) {
				boolean valid = merchantPaymentApi.merchantPayment(serviceId, transactionId, Double.parseDouble(amount),
						AuthenticationUtil.getCurrentUser().getId());

				if (valid == true) {
					responseDTO.setStatus(ResponseStatus.SUCCESS);
					responseDTO.setMessage("Service Accomplish");
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST);
					responseDTO.setMessage("Service failed");
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED);
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetails(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE);
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		}
	}
	
	
	//for dish homes balanceenquiry
	
	@RequestMapping(method = RequestMethod.GET, value = "/bussewa")
	public String dishHome(ModelMap modelMap, HttpServletRequest request) {

	merchantPaymentApi.bussewa();;

		return "bussewa/bussewa";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/epay")
	public String bussewa (ModelMap modelMap, HttpServletRequest request) {

	merchantPaymentApi.epay();;

		return "epay/epay";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/paypointtest")
	public String paypoint (ModelMap modelMap, HttpServletRequest request) {

		merchantPaymentApi.paytime();

		return "epay/epay";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/unitedSolution")
	public String untdsoln (ModelMap modelMap, HttpServletRequest request) {

	merchantPaymentApi.unitedsolution();

		return "epay/epay";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/balanceenquiry")
	public ResponseEntity<Object> getBalance(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {

			String serviceId = request.getParameter("generalserviceUnique");

			Hashes hashes = new Hashes();
			hashes.setValid(true);
			//Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			if (hashes.isValid()) {
				DishHomeDto dishHomeDto = dishHomeApi.balanceEnquiry(serviceId);

				if (dishHomeDto.getResponseDetail().get("status") == "success") {
					responseDTO.setStatus(ResponseStatus.SUCCESS);
					responseDTO.setMessage("Service Accomplish");
					responseDTO.setDetails(dishHomeDto);
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST);
					responseDTO.setMessage("Service failed");
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED);
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetails(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE);
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		}
	}
	
	//dishHome recharge
	@RequestMapping(method = RequestMethod.POST, value = "/dishomerecharge")
	public ResponseEntity<Object> rechargeDishHome(HttpServletRequest request, HttpServletResponse response) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {


			String serviceId = request.getParameter("generalserviceUnique");
			String amount = request.getParameter("generalamount");

			Hashes hashes = new Hashes();
			hashes.setValid(true);
			//Hashes hashes = merchantPaymentValidation.generalMerchantValidation(serviceId, transactionId, amount);
			if (hashes.isValid()) {
				boolean valid = dishHomeApi.dishHomeRecharge(serviceId, Double.parseDouble(amount), AuthenticationUtil.getCurrentUser().getId());

				if (valid == true) {
					responseDTO.setStatus(ResponseStatus.SUCCESS);
					responseDTO.setMessage("Service Accomplish");
					//responseDTO.setDetails(dishHomeDto);
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				} else {
					responseDTO.setStatus(ResponseStatus.BAD_REQUEST);
					responseDTO.setMessage("Service failed");
					return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
				}
			} else {
				responseDTO.setStatus(ResponseStatus.VALIDATION_FAILED);
				responseDTO.setMessage("Request Not Valid");
				responseDTO.setDetails(hashes.getMyHash());
				return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatus(ResponseStatus.FAILURE);
			responseDTO.setMessage("Service Failed");
			return new ResponseEntity<Object>(responseDTO, HttpStatus.OK);
		}
	}
	
}
*/