package com.mobilebanking.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.api.IUserTemplateApi;
import com.mobilebanking.entity.User;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.ObjectResponseDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.UserTemplateDTO;
import com.mobilebanking.model.UserType;
import com.mobilebanking.model.error.UserError;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import com.mobilebanking.validation.UserValidation;

@Controller
public class UserController implements MessageSourceAware {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IBankApi bankApi;

	@Autowired
	private IBankBranchApi branchApi;

	@Autowired
	private IUserApi userApi;

	@Autowired
	private IStateApi stateApi;

	@Autowired
	private UserValidation userValidation;

	@Autowired
	private IUserTemplateApi userTemplateApi;

	@Autowired
	private ICityApi cityApi;

	@RequestMapping(method = RequestMethod.GET, value = "/adduser")
	public String addUser(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authorities = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}

			if (authorities.contains(Authorities.ADMINISTRATOR) && authorities.contains(Authorities.AUTHENTICATED)) {
				List<BankDTO> banksList = bankApi.getAllBank();
				List<StateDTO> statesList = stateApi.getAllState();
				modelMap.put("statesList", statesList);
				modelMap.put("banksList", banksList);
				List<UserType> userTypeList = new ArrayList<UserType>();
				userTypeList.add(UserType.Admin);
				userTypeList.add(UserType.Bank);
				userTypeList.add(UserType.ChannelPartner);
				modelMap.put("userTypeList", userTypeList);
				modelMap.put("userObject", "admin");
				List<UserTemplateDTO> userTemplate = userTemplateApi.getAllUserTemplate();
				modelMap.put("userTemplate", userTemplate);
			} else if (authorities.contains(Authorities.BANK) && authorities.contains(Authorities.AUTHENTICATED)) {
				List<BankDTO> banksList = bankApi.getAllBank();
				List<StateDTO> statesList = stateApi.getAllState();
				modelMap.put("statesList", statesList);
				modelMap.put("banksList", banksList);
				List<UserType> userTypeList = new ArrayList<UserType>();
				userTypeList.add(UserType.Bank);
				userTypeList.add(UserType.BankBranch);
				modelMap.put("userTypeList", userTypeList);
				modelMap.put("userObject", "bank");
			}
			return "User/addUser";
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/adduser")
	public String addUser(ModelMap modelMap, RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserDTO userDto) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authorities = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if ((authorities.contains(Authorities.ADMINISTRATOR) || authorities.contains(Authorities.BANK))
					&& authorities.contains(Authorities.AUTHENTICATED)) {
				UserError userError = new UserError();
				userError = userValidation.validateUser(userDto);
				if (userError.isValid()) {
					userApi.saveUser(userDto);
					redirectAttributes.addFlashAttribute("message", "User added Succesfully.");
					return "redirect:/listuser";
				} else {
					redirectAttributes.addFlashAttribute("error", userError);
					redirectAttributes.addFlashAttribute("user", userDto);
					return "redirect:/adduser";
				}
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listuser")
	public String listUser(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			List<StateDTO> statesList = stateApi.getAllState();
			modelMap.put("statesList", statesList);
			String authorities = AuthenticationUtil.getCurrentUser().getAuthority();
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			if (authorities.contains(Authorities.ADMINISTRATOR) && authorities.contains(Authorities.AUTHENTICATED)) {
				List<UserType> userTypeList = new ArrayList<UserType>();
				userTypeList.add(UserType.Admin);
				userTypeList.add(UserType.ChannelPartner);
				userTypeList.add(UserType.Bank);
				modelMap.put("userAdmin", true);
				List<UserDTO> userList = userApi.findUserByRole(Authorities.BANK + "," + Authorities.AUTHENTICATED);
				if (userList == null) {
					userList = new ArrayList<>();
				}
				List<UserDTO> adminList = userApi
						.findUserByRole(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
				if (adminList != null) {
					userList.addAll(adminList);
				}
				List<UserDTO> channelPartnerList = userApi
						.findUserByRole(Authorities.CHANNELPARTNER + "," + Authorities.AUTHENTICATED);
				if (channelPartnerList != null) {
					userList.addAll(channelPartnerList);
				}
				modelMap.put("userList", userList);
				return "User/listUser";
			} else if (authorities.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authorities.equals(Authorities.AUTHENTICATED + "," + Authorities.BANK)
					|| (authorities.contains(Authorities.BANK_ADMIN)
							&& authorities.contains(Authorities.AUTHENTICATED))) {
				List<UserDTO> userList = userApi
						.findBranchUserByBank(AuthenticationUtil.getCurrentUser().getAssociatedId());
				if (userList == null) {
					userList = new ArrayList<>();
				}
				List<UserDTO> bankUserList = userApi.findUserListByAssociatedIdAndUserType(
						AuthenticationUtil.getCurrentUser().getAssociatedId(), UserType.Bank);
				if (bankUserList != null) {
					userList.addAll(bankUserList);
				}
				modelMap.put("branchList",
						branchApi.listBankBranchByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				modelMap.put("userList", userList);
				return "User/listUser";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getusertypesobjects")
	public @ResponseBody Object output(@RequestParam(value = "firecounter", required = true) int firecounter,
			@RequestParam(value = "usertype", required = true) UserType userType,
			@RequestParam(value = "objectName", required = true) String objectName, HttpServletRequest request,
			HttpServletResponse response) {
		ObjectResponseDTO result = new ObjectResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			Object object = userApi.userDetector(userType, objectName,
					AuthenticationUtil.getCurrentUser().getAssociatedId());
			result.setStatus("ok");
			result.setDetails(object);
			result.setResponse(firecounter);
		}

		return result;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getUser")
	public ResponseEntity<RestResponseDTO> getUser(@RequestParam("userid") long userid, HttpServletRequest request,
			HttpServletResponse response) {

		RestResponseDTO restResponseDto = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				UserDTO userDto = userApi.getUserWithId(userid);
				restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_READ, userDto);
			}

		} catch (Exception e) {
			logger.debug("exception" + e);
			e.printStackTrace();
			restResponseDto.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			restResponseDto.setMessage("Request Operator");
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCityList")
	public ResponseEntity<RestResponseDTO> getCity(@RequestParam("state") String state, HttpServletRequest request,
			HttpServletResponse response) {

		RestResponseDTO restResponseDto = new RestResponseDTO();
		try {
			if (AuthenticationUtil.getCurrentUser() != null) {

				List<CityDTO> citiesList = cityApi.findCityByStateName(state);
				restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_READ, citiesList);
			}

		} catch (Exception e) {
			logger.debug("exception" + e);
			e.printStackTrace();
			restResponseDto.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
			restResponseDto.setMessage("Request Operator");
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/changeuserpassword", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> changeUserPassword(HttpServletRequest request,
			@ModelAttribute("user") UserDTO user, ModelMap modelMap, RedirectAttributes redirectAttributes) {

		RestResponseDTO restResponseDto = new RestResponseDTO();

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			UserDTO userDto = userApi.getUserByUserName(user.getUserName());

			userDto.setPassword(user.getPassword());
			userDto.setRepassword(user.getRepassword());
			userDto.setOldPassword(user.getOldPassword());

			UserError userError = new UserError();

			userError = userValidation.passwordValidation(user);
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.CHANNELPARTNER + "," + Authorities.AUTHENTICATED)) {
				try {
					if (userError.isValid()) {
						userDto = userApi.changePassword(userDto);
						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED, null);
					} else {
						restResponseDto = getResponseDto(ResponseStatus.VALIDATION_FAILED,
								StringConstants.DATA_SAVING_ERROR, userError);
					}
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR, null);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/changepasswordbyadmin", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> changeUserPasswordByAdmin(HttpServletRequest request,
			@ModelAttribute("user") UserDTO user, ModelMap modelMap, RedirectAttributes redirectAttributes) {

		RestResponseDTO restResponseDto = new RestResponseDTO();

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			UserDTO userDto = userApi.getUserByUserName(user.getUserName());

			userDto.setPassword(user.getPassword());
			userDto.setRepassword(user.getRepassword());
			// userDto.setOldPassword(user.getOldPassword());

			UserError userError = new UserError();

			userError = userValidation.passwordValidationByAdmin(user);
			if (authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_BRANCH_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
				try {
					if (userError.isValid()) {
						userDto = userApi.changePasswordByAdmin(userDto);
						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED, null);
					} else {
						restResponseDto = getResponseDto(ResponseStatus.VALIDATION_FAILED,
								StringConstants.DATA_SAVING_ERROR, userError);
					}
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR, null);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> updateUser(UserDTO userDto) {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
				try {
					userDto = userApi.updateUser(userDto);
					restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED, userDto);
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED, userDto);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteUser(HttpServletRequest request, @RequestParam("userId") Long id, ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		User currentUser = AuthenticationUtil.getCurrentUser();
		if (currentUser != null) {
			if (userValidation.checkDeleteValidation(id, currentUser)) {
				try {
					userApi.deleteUser(id);
					redirectAttributes.addFlashAttribute("message", "User deleted Succesfully.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Unable to delete the user,Please try again.");
			}
		}

		return "redirect:/listuser";
	}

	@RequestMapping(value = "/changebranch", method = RequestMethod.POST)
	public String changeBranch(@RequestParam("user") Long userId, @RequestParam("branch") Long branchId,
			RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			User currentUser = AuthenticationUtil.getCurrentUser();
			if (currentUser.getUserType() == UserType.Bank) {
				try {
					if (userValidation.changeBranchValidation(userId, currentUser, branchId)) {
						if (userApi.changeBranch(userId, branchId)) {
							redirectAttributes.addFlashAttribute("message", "Branch changed Succesfully.");
							return "redirect:/listuser";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				redirectAttributes.addFlashAttribute("errorMessage",
						"Unable to change branch. Please try again later.");
				return "redirect:/listuser";
			}
		}
		return "redirect:/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
	 * springframework.context.MessageSource)
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(method = RequestMethod.GET, value = "/ajax/usertemplate/getbyusertype")
	@ResponseBody
	public Map<String, List<UserTemplateDTO>> getCitiesByState(@RequestParam("usertype") UserType userType,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Map<String, List<UserTemplateDTO>> userTemplateListMap = new HashMap<String, List<UserTemplateDTO>>();
		List<UserTemplateDTO> userTemplateList = userTemplateApi.findUserTemplateByUserType(userType);
		userTemplateListMap.put("userTemplateList", userTemplateList);
		return userTemplateListMap;
	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message, Object detail) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);
		restResponseDto.setDetail(detail);

		return restResponseDto;
	}

	@RequestMapping(value = "/user_list_by_filter")
	public String getUserByBankId(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "bank_id") String bankId, @RequestParam(value = "user_type") String userType) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			List<StateDTO> statesList = stateApi.getAllState();
			modelMap.put("statesList", statesList);
			if (AuthenticationUtil.getCurrentUser().isFirstLogin()) {
				return "redirect:/";
			}
			List<UserType> userTypeList = new ArrayList<UserType>();
			userTypeList.add(UserType.Admin);
			userTypeList.add(UserType.ChannelPartner);
			userTypeList.add(UserType.Bank);
			modelMap.put("userTypeList", userTypeList);
			modelMap.put("bankList", bankApi.getAllBank());
			modelMap.put("userAdmin", true);
			if (!bankId.equalsIgnoreCase("") && bankId != null) {
				List<UserDTO> userList = userApi.findBranchUserByBank(Long.parseLong(bankId));
				if (userList == null) {
					userList = new ArrayList<>();
				}
				List<UserDTO> bankUserList = userApi.findUserListByAssociatedIdAndUserType(Long.parseLong(bankId),
						UserType.Bank);
				if (bankUserList != null) {
					userList.addAll(bankUserList);
				}
				modelMap.put("userList", userList);
				return "User/listUser";
			} else if (userType != null && !userType.equalsIgnoreCase("")) {

				List<UserDTO> userList = new ArrayList<UserDTO>();
				if (userType.equalsIgnoreCase(UserType.Bank.name())) {
					userList = userApi.findUserByRole(Authorities.BANK + "," + Authorities.AUTHENTICATED);
				}
				if (userType.equalsIgnoreCase(UserType.Admin.name())) {
					List<UserDTO> adminList = userApi
							.findUserByRole(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED);
					if (adminList != null) {
						userList.addAll(adminList);
					}
				}
				if (userType.equalsIgnoreCase(UserType.ChannelPartner.name())) {
					List<UserDTO> channelPartnerList = userApi
							.findUserByRole(Authorities.CHANNELPARTNER + "," + Authorities.AUTHENTICATED);
					if (channelPartnerList != null) {
						userList.addAll(channelPartnerList);
					}
				}
				modelMap.put("userList", userList);
				return "User/listUser";
			} else {
				return "redirect:/listuser";
			}
		} else {
			return "redirect:/";
		}

	}
}
