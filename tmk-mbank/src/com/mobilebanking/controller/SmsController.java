package com.mobilebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.IGeneralSettingsApi;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;

@Controller
public class SmsController {

	@Autowired
	private IGeneralSettingsApi generalSettingApi;

	@RequestMapping(value = "/addsparrowsmscredit", method = RequestMethod.POST)
	public ResponseEntity<RestResponseDTO> addLicenseCount(@ModelAttribute("smsCount") String smsCount) {

		RestResponseDTO restResponseDto = new RestResponseDTO();

		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();

			if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {

				try {
					try{
						Long.parseLong(smsCount);
					}catch(Exception e){
						restResponseDto = getResponseDto(ResponseStatus.FAILURE, "Invalid Sms Count");
						return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
					}
					generalSettingApi.addCredit(smsCount);
					restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED);
				} catch (Exception e) {
					e.printStackTrace();
					restResponseDto = getResponseDto(ResponseStatus.INTERNAL_SERVER_ERROR, StringConstants.DATA_SAVING_ERROR);
					return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
	}

	private RestResponseDTO getResponseDto(ResponseStatus status, String message) {
		RestResponseDTO restResponseDto = new RestResponseDTO();

		restResponseDto.setResponseStatus(status);
		restResponseDto.setMessage(message);

		return restResponseDto;
	}

}
