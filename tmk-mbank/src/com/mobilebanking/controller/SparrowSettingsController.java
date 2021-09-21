package com.mobilebanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobilebanking.api.ISparrowApi;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.SparrowSettingsDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class SparrowSettingsController {

	@Autowired
	private ISparrowApi sparrowApi;

	@RequestMapping(method = RequestMethod.GET, value = "/getSparrowSettings")
	public ResponseEntity<RestResponseDTO> getSparrowSettings() {
		RestResponseDTO responseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					SparrowSettingsDTO sparrowSettings = sparrowApi.getSettings();
					responseDTO.setMessage("Success");
					responseDTO.setDetail(sparrowSettings);
					responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
					return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.OK);
				} catch (Exception e) {
					responseDTO.setMessage("Failure");
					responseDTO.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
					return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		}
		responseDTO.setMessage("Failure");
		responseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
		return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/updateSparrowSettings")
	public ResponseEntity<RestResponseDTO> updateSparrowSettings(SparrowSettingsDTO sparrowSettingsDTO) {
		RestResponseDTO responseDTO = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					SparrowSettingsDTO sparrowSettings = sparrowApi.updateSetting(sparrowSettingsDTO);
					responseDTO.setMessage("Success");
					responseDTO.setDetail(sparrowSettings);
					responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
					return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.OK);
				} catch (Exception e) {
					responseDTO.setMessage("Failure");
					responseDTO.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
					return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		}
		responseDTO.setMessage("Failure");
		responseDTO.setResponseStatus(ResponseStatus.UNAUTHORIZED_USER);
		return new ResponseEntity<RestResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
	}

}
