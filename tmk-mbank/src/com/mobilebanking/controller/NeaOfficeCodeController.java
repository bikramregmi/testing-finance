package com.mobilebanking.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.INeaOfficeCodeApi;
import com.mobilebanking.model.NeaOfficeCodeDTO;
import com.mobilebanking.model.error.NeaOfficeCodeError;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.NeaOfficeCodeValidation;

@Controller
public class NeaOfficeCodeController {

	@Autowired
	private INeaOfficeCodeApi neaOfficeCodeApi;

	@Autowired
	private NeaOfficeCodeValidation neaOfficeCodeValidation;

	@RequestMapping(method = RequestMethod.GET, value = "/officecode")
	public String listOfficeCodes(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<NeaOfficeCodeDTO> officeCodeList = neaOfficeCodeApi.getAllOfficeCodes();
				modelMap.put("officeCodeList", officeCodeList);
				return "/nea/neaofficecode";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/officecode/add")
	public String addOfficeCode() {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				return "/nea/addneaofficecode";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/officecode/add")
	public String addNeaOfficeCode(NeaOfficeCodeDTO officeCodeDto, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					NeaOfficeCodeError officeCodeError = new NeaOfficeCodeError();
					officeCodeDto.setId(0);
					officeCodeError = neaOfficeCodeValidation.officeCodeValidation(officeCodeDto);
					if (officeCodeError.isValid()) {
						officeCodeDto = neaOfficeCodeApi.saveNeaOfficeCode(officeCodeDto);
						redirectAttributes.addFlashAttribute("message", "Nea office added successfully");
						return "redirect:/officecode";
					} else {
						redirectAttributes.addFlashAttribute("error", officeCodeError);
						redirectAttributes.addFlashAttribute("officeCodeDto", officeCodeDto);
						return "/nea/addneaofficecode";
					}
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Something went wrong. please Try again later");
					return "/nea/addneaofficecode";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/officecode/add/bulk")
	public String bulkUploadNeaOfficeCode(ModelMap modelMap, @RequestParam("officeCodeFile") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					List<NeaOfficeCodeDTO> officeCodeList = parseNeaOfficeFile(file);
					for (NeaOfficeCodeDTO neaOffice : officeCodeList) {
						neaOfficeCodeApi.saveNeaOfficeCode(neaOffice);
					}
					redirectAttributes.addFlashAttribute("message", "Nea office added successfully");
					return "redirect:/officecode";
				} catch (Exception e) {
					e.printStackTrace();
					modelMap.put("bulkError", "UPLOAD FORMAT ERROR");
					return "/nea/addneaofficecode";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/officecode/edit", method = RequestMethod.GET)
	public String editNeaOfficeCode(@RequestParam("office") Long id, RedirectAttributes redirectAttributes,
			ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				modelMap.put("officeCodeDto", neaOfficeCodeApi.getNeaOfficeCode(id));
				return "/nea/editneaofficecode";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/officecode/edit", method = RequestMethod.POST)
	public String editNeaOfficeCode(NeaOfficeCodeDTO officeCodeDto, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					NeaOfficeCodeError officeCodeError = new NeaOfficeCodeError();
					officeCodeError = neaOfficeCodeValidation.officeCodeValidation(officeCodeDto);
					if (officeCodeError.isValid()) {
						officeCodeDto = neaOfficeCodeApi.editNeaOfficeCode(officeCodeDto);
						return "redirect:/officecode";
					} else {
						redirectAttributes.addFlashAttribute("error", officeCodeError);
						redirectAttributes.addFlashAttribute("officeCodeDto", officeCodeDto);
						return "/nea/editneaofficecode";
					}
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Something went wrong. please Try again later");
					return "redirect:/officecode";
				}
			}
		}
		return "redirect:/";

	}

	@RequestMapping(value = "/officecode/changestatus", method = RequestMethod.GET)
	public String changeStatus(@RequestParam("office") Long id, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					neaOfficeCodeApi.changeOfficeCodeStatus(id);
					redirectAttributes.addFlashAttribute("message", "Status Updated Successfully");
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Something went wrong. please Try again later");
				}
				return "redirect:/officecode";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "/officecode/delete", method = RequestMethod.GET)
	public String delete(@RequestParam("office") Long id, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					neaOfficeCodeApi.deleteOffice(id);
					redirectAttributes.addFlashAttribute("message", "Office Deleted Successfully");
				} catch (Exception e) {
					e.printStackTrace();
					redirectAttributes.addFlashAttribute("message", "Something went wrong. please Try again later");
				}
				return "redirect:/officecode";
			}
		}
		return "redirect:/";
	}

	public List<NeaOfficeCodeDTO> parseNeaOfficeFile(MultipartFile multipartFile)
			throws IOException, DataFormatException {
		List<NeaOfficeCodeDTO> neaOfficeList = new ArrayList<NeaOfficeCodeDTO>();

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());

		XSSFSheet workSheet = workbook.getSheetAt(0);
		System.out.println(workSheet.getLastRowNum());
		int i = 0;
		while (i <= workSheet.getLastRowNum()) {
			XSSFRow row = workSheet.getRow(i++);
			XSSFCell cell0 = null;
			XSSFCell cell1 = null;
			if (i == 1) {
				cell0 = row.getCell(0);
				cell1 = row.getCell(1);

				if (!cell0.toString().equalsIgnoreCase("BRANCH CODE")
						|| !cell1.toString().equalsIgnoreCase("BRANCH NAME"))
					throw new DataFormatException("UPLOAD FORMAT ERROR");
			} else if (i > 1) {
				NeaOfficeCodeDTO dto = new NeaOfficeCodeDTO();
				if (row.getCell(0).toString() != null) {
					dto.setOfficeCode(row.getCell(0).getRawValue().trim());
				}
				if (row.getCell(1).toString() != null) {
					dto.setOffice(row.getCell(1).toString());
				}
				neaOfficeList.add(dto);
			}
		}
		return neaOfficeList;
	}

}
