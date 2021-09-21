package com.mobilebanking.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.parser.BulkUploadParser;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.FileUtils;
import com.mobilebanking.validation.CustomerValidation;

@Controller
public class BulkUploadController {

	@Autowired
	private BulkUploadParser bulkUploadParser;

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IBankBranchApi bankBranchApi;

	@Autowired
	private ICustomerApi customerApi;

	@Autowired
	private CustomerValidation customerValidation;

	@RequestMapping(method = RequestMethod.GET, value = "/bankBulkUpload")
	public String bulkUpload(ModelMap modelMap, Model model, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				return "/BulkUpload/bankUpload";
			}
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bulkBranchUpload")
	public String erc(ModelMap modelMap, Model model, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {

				return "/BulkUpload/branchUpload";
			}
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/stateBulkUpload")
	public String bulkStateUpload(ModelMap modelMap, Model model, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				return "/BulkUpload/stateUpload";
			}
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cityBulkUpload")
	public String bulkCityUpload(ModelMap modelMap, Model model, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				return "/BulkUpload/cityUpload";
			}
		}

		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bankBulkUpload")
	public ResponseEntity<RestResponseDTO> uploadErc(ModelMap modelMap, Model model, HttpServletRequest request,
			@RequestParam("file") MultipartFile file, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)
					|| authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {

				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("");

				File theDir = new File(path + "\\" + "BankBulkUpload");

				if (!theDir.exists()) {
					try {
						theDir.mkdir();

					} catch (SecurityException se) {

					}

				}

				if (!file.isEmpty()) {
					if (!file.getOriginalFilename().equals("")) {

						List<BankDTO> bankList = new ArrayList<BankDTO>();

						// parsing file
						file.transferTo(new File(path + "\\" + "BankBulkUpload" + "\\" + file.getOriginalFilename()));

						bankList = bulkUploadParser.parseBankFile(file, "");

						if (bankList.size() > 0) {
							for (BankDTO bank : bankList) {

								if (!(bank == null)) {
									bank.setCreatedById(AuthenticationUtil.getCurrentUser().getId());
									bankApi.saveBulkBank(bank);

								}

							}
							restResponseDto.setMessage("Success");
						}
					}
				}

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bulkCustomerUpload")
	public ResponseEntity<RestResponseDTO> bulkBranchUpload(ModelMap modelMap, Model model, HttpServletRequest request,
			@RequestParam("file") MultipartFile file, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)
					|| (authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED))) {

				String responseMessage;
				String destinationPath = FileUtils.getBasePath() + "/upload/customer/";
				try {
					responseMessage = customerApi.uploadBulkCustomer(file, destinationPath);
					//
				} catch (IllegalArgumentException e) {
					responseMessage = e.getMessage();
					restResponseDto.setStatus("Failure");
					restResponseDto.setMessage(responseMessage);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
				}

				restResponseDto.setMessage("Customer Added succesfully");

			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/bulkCustomerUpload") public ResponseEntity<RestResponseDTO>
	 * customerBulkUpload(ModelMap modelMap, Model model, HttpServletRequest
	 * request,
	 * 
	 * @RequestParam("file") MultipartFile file, HttpServletResponse response,
	 * RedirectAttributes redirectAttributes) throws IllegalStateException,
	 * IOException { RestResponseDTO restResponseDto = new RestResponseDTO(); if
	 * (AuthenticationUtil.getCurrentUser() != null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.contains(Authorities.BANK) &&
	 * authority.contains(Authorities.AUTHENTICATED)||(authority.contains(
	 * Authorities.BANK_ADMIN) &&
	 * authority.contains(Authorities.AUTHENTICATED))||authority.contains(
	 * Authorities.BANK_BRANCH) &&
	 * authority.contains(Authorities.AUTHENTICATED)||(authority.contains(
	 * Authorities.BANK_BRANCH_ADMIN) &&
	 * authority.contains(Authorities.AUTHENTICATED))) {
	 * 
	 * 
	 * ServletContext context = request.getSession().getServletContext(); String
	 * path = context.getRealPath("");
	 * 
	 * File theDir = new File(path + "\\" + "CustomerBulkUpload");
	 * 
	 * if (!theDir.exists()) { try { theDir.mkdir();
	 * 
	 * } catch (SecurityException se) {
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if (!file.isEmpty()) { if (!file.getOriginalFilename().equals("")) {
	 * 
	 * List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
	 * 
	 * //parsing file file.transferTo(new File(path +
	 * "\\" + "CustomerBulkUpload" + "\\" + file.getOriginalFilename())); File
	 * newFile= new File(path + "\\" + "CustomerBulkUpload" + "\\" +
	 * file.getOriginalFilename());
	 * 
	 * customerList = bulkUploadParser.parseCustomerFile(newFile);
	 * 
	 * String error =
	 * customerValidation.checkLicenseCountForBulkUpload(customerList.size());
	 * if(error!=null){ restResponseDto.setMessage(error); return new
	 * ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
	 * }
	 * 
	 * if (customerList!=null && customerList.size() > 0) { for (CustomerDTO
	 * customer : customerList) {
	 * 
	 * if (!(customer == null)) { UserType userType = null;
	 * if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
	 * customer.setBank(String.valueOf(AuthenticationUtil.getCurrentUser().
	 * getAssociatedId()));
	 * customer.setCreatedBy(AuthenticationUtil.getCurrentUser().getId().
	 * toString()); userType = UserType.Bank; }else if
	 * (AuthenticationUtil.getCurrentUser().getUserType()==UserType.BankBranch){
	 * BankBranchDTO bankBranchDTO =
	 * bankBranchApi.findBranchById(AuthenticationUtil.getCurrentUser().
	 * getAssociatedId());
	 * customer.setBankBranch(bankBranchDTO.getId().toString());
	 * customer.setCreatedBy(AuthenticationUtil.getCurrentUser().getId().
	 * toString()); userType = UserType.BankBranch; }
	 * customerApi.saveBulkCustomer(customer,userType);
	 * 
	 * }
	 * 
	 * } restResponseDto.setMessage("Success"); }else{ restResponseDto.
	 * setMessage("Error while adding the bulk file. Please check the file or contact administrator"
	 * ); return new ResponseEntity<RestResponseDTO>(restResponseDto,
	 * HttpStatus.BAD_REQUEST); } } }
	 * 
	 * 
	 * } } return new ResponseEntity<RestResponseDTO>(restResponseDto,
	 * HttpStatus.OK); }
	 * 
	 */

	/*
	 * @RequestMapping(method = RequestMethod.POST, value =
	 * "/bulkCustomerUpload") public ResponseEntity<RestResponseDTO>
	 * customerBulkUpload(ModelMap modelMap, Model model, HttpServletRequest
	 * request,
	 * 
	 * @RequestParam("file") MultipartFile file, HttpServletResponse response,
	 * RedirectAttributes redirectAttributes) throws IllegalStateException,
	 * IOException { RestResponseDTO restResponseDto = new RestResponseDTO(); if
	 * (AuthenticationUtil.getCurrentUser() != null) { String authority =
	 * AuthenticationUtil.getCurrentUser().getAuthority(); if
	 * (authority.contains(Authorities.BANK) &&
	 * authority.contains(Authorities.AUTHENTICATED)||(authority.contains(
	 * Authorities.BANK_ADMIN) &&
	 * authority.contains(Authorities.AUTHENTICATED))||authority.contains(
	 * Authorities.BANK_BRANCH) &&
	 * authority.contains(Authorities.AUTHENTICATED)||(authority.contains(
	 * Authorities.BANK_BRANCH_ADMIN) &&
	 * authority.contains(Authorities.AUTHENTICATED))) {
	 * 
	 * 
	 * ServletContext context = request.getSession().getServletContext(); String
	 * path = context.getRealPath("");
	 * 
	 * File theDir = new File(path + "\\" + "CustomerBulkUpload");
	 * 
	 * if (!theDir.exists()) { try { theDir.mkdir();
	 * 
	 * } catch (SecurityException se) {
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if (!file.isEmpty()) { if (!file.getOriginalFilename().equals("")) {
	 * 
	 * List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();
	 * 
	 * //parsing file file.transferTo(new File(path +
	 * "\\" + "CustomerBulkUpload" + "\\" + file.getOriginalFilename())); File
	 * newFile= new File(path + "\\" + "CustomerBulkUpload" + "\\" +
	 * file.getOriginalFilename());
	 * 
	 * customerList = bulkUploadParser.parseCustomerFile(newFile);
	 * 
	 * String error =
	 * customerValidation.checkLicenseCountForBulkUpload(customerList.size());
	 * if(error!=null){ restResponseDto.setMessage(error); return new
	 * ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
	 * }
	 * 
	 * if (customerList!=null && customerList.size() > 0) { for (CustomerDTO
	 * customer : customerList) {
	 * 
	 * if (!(customer == null)) { UserType userType = null;
	 * if(AuthenticationUtil.getCurrentUser().getUserType()==UserType.Bank){
	 * customer.setBank(String.valueOf(AuthenticationUtil.getCurrentUser().
	 * getAssociatedId()));
	 * customer.setCreatedBy(AuthenticationUtil.getCurrentUser().getId().
	 * toString()); userType = UserType.Bank; }else if
	 * (AuthenticationUtil.getCurrentUser().getUserType()==UserType.BankBranch){
	 * BankBranchDTO bankBranchDTO =
	 * bankBranchApi.findBranchById(AuthenticationUtil.getCurrentUser().
	 * getAssociatedId());
	 * customer.setBankBranch(bankBranchDTO.getId().toString());
	 * customer.setCreatedBy(AuthenticationUtil.getCurrentUser().getId().
	 * toString()); userType = UserType.BankBranch; }
	 * customerApi.saveBulkCustomer(customer,userType);
	 * 
	 * }
	 * 
	 * } restResponseDto.setMessage("Success"); }else{ restResponseDto.
	 * setMessage("Error while adding the bulk file. Please check the file or contact administrator"
	 * ); return new ResponseEntity<RestResponseDTO>(restResponseDto,
	 * HttpStatus.BAD_REQUEST); } } }
	 * 
	 * 
	 * } } return new ResponseEntity<RestResponseDTO>(restResponseDto,
	 * HttpStatus.OK); }
	 */

}
