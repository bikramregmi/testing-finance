package com.mobilebanking.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ISmsAlertApi;
import com.mobilebanking.model.BulkSmsAlertDTO;
import com.mobilebanking.model.FileDTO;
import com.mobilebanking.model.SmsAlertDTO;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.FileUtils;

@Controller
public class SmsAlertController {
	
	@Autowired
	private ISmsAlertApi smsAlertApi;
	
	@RequestMapping(method = RequestMethod.GET, value = "/smsalert/bulk")
	public String sendBulkSmsAlert(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				return "SmsAlert/bulkalert";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/smsalert/bulk/sample")
	public void downloadBulkAlertSample(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				try {
					String filePath = FileUtils.getSamplePath() + "/bulksmsAlert.csv";

					response.setContentType("text/csv");
					response.setHeader("content-disposition", "attachment; filename=\"bulksmsAlert.csv\"");
					File sampleFile = new File(filePath);
					FileInputStream fis = new FileInputStream(sampleFile);
					OutputStream os = response.getOutputStream();
					try {
						response.setContentLength((int) sampleFile.length());
						int byteRead = 0;
						while ((byteRead = fis.read()) != -1) {
							os.write(byteRead);
						}
						os.flush();
					} catch (Exception excp) {
						excp.printStackTrace();
					} finally {
						os.close();
						fis.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/smsalert/bulk/upload")
	public ResponseEntity<ResponseDTO> uploadBulkSmsAlert(ModelMap modelMap, FileDTO file) {
		ResponseDTO response = new ResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				try {
					List<SmsAlertDTO> smsAlertList = processCsvFile(file.getFile());
					response.setMessage("Success");
					response.setDetails(smsAlertList);
					return new ResponseEntity<>(response, HttpStatus.OK);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					response.setMessage(e.getMessage());
					response.setStatus(e.getLocalizedMessage());
					return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/smsalert/bulk/send")
	public String sendBulkSmsAlert(RedirectAttributes redirectAttributes, BulkSmsAlertDTO bulkSmsAlertDTO) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				try {
					bulkSmsAlertDTO.setCreatedBy(AuthenticationUtil.getCurrentUser().getId());
					Long batchId = smsAlertApi.saveBulkBatch(bulkSmsAlertDTO);
					redirectAttributes.addFlashAttribute("message", "Message has been queued for delivery.");
					return "redirect:/smsalert/bulk/detail?batch="+batchId;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong. Please try again later.");
					return "redirect:/smsalert/bulk";
				}
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/smsalert/bulk/list")
	public String listBulkSmsAlert(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				List<BulkSmsAlertDTO> batchList = smsAlertApi.getBatchList(AuthenticationUtil.getCurrentUser().getId());
				modelMap.put("batchList", batchList);
				return "SmsAlert/bulklist";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/smsalert/bulk/detail")
	public String bulkSmsAlertDetail(ModelMap modelMap,@RequestParam("batch") long batchId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) || authority.contains(Authorities.BANK)
					|| authority.contains(Authorities.BANK_BRANCH)) {
				List<SmsAlertDTO> smsAlertList = smsAlertApi.getSmsAlertByBatch(batchId);
				modelMap.put("smsAlertList", smsAlertList);
				return "SmsAlert/bulkdetail";
			}
		}
		return "redirect:/";
	}

	private List<SmsAlertDTO> processCsvFile(MultipartFile file) {
		List<SmsAlertDTO> smsAlertList = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String[] header = br.readLine().split(",");
			if (!header[0].equalsIgnoreCase("Mobile Number") || !header[1].equalsIgnoreCase("Message")) {
				throw new IllegalArgumentException("Invalid number of columns.");
			}
			smsAlertList = br.lines().map(mapToSmsAlertFromSMSCsv).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		return smsAlertList;
	}

	private Function<String, SmsAlertDTO> mapToSmsAlertFromSMSCsv = (line) -> {
		String[] column = line.split(",");
		if (column.length != 2) {
			throw new IllegalArgumentException("Invalid number of columns.");
		}
		SmsAlertDTO smsAlertDTO = new SmsAlertDTO();
		if ((column[0] == null && column[0].equals("")) || (column[1] == null && column[1].equals(""))) {
			throw new IllegalArgumentException("Invalid columns In the given file.");
		}
		smsAlertDTO.setMobileNumber(column[0]);
		smsAlertDTO.setMessage(column[1]);
		return smsAlertDTO;
	};
}
