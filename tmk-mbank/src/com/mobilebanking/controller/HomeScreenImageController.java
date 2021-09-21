package com.mobilebanking.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mobilebanking.api.IHomeScreenImageApi;
import com.mobilebanking.model.HomeScreenImageDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.HomeScreenImageValidation;

@Controller
public class HomeScreenImageController {

	@Autowired
	private IHomeScreenImageApi homeScreenImageApi;

	@Autowired
	private HomeScreenImageValidation homeScreenImageValidation;

	@RequestMapping(value = "/homescreenimage", method = RequestMethod.GET)
	public String homeScreenImage(ModelMap modelMap) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				return "HomescreenImage/homescreenImage";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/homescreenimage/add", method = RequestMethod.POST)
	public String addHomeScreenImage(RedirectAttributes redirectAttributes, HomeScreenImageDTO homeScreenImageDTO) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				String error = homeScreenImageValidation.validateHomeScreenImage(homeScreenImageDTO.getFile());
				if (error == null) {
					homeScreenImageDTO.setBankId(AuthenticationUtil.getCurrentUser().getAssociatedId());
					homeScreenImageApi.savaNewHomeScreenImage(homeScreenImageDTO);
					redirectAttributes.addFlashAttribute("message", "Image added successfully.");
					return "redirect:/homescreenimage";
				}
				redirectAttributes.addFlashAttribute("errorMessage", error);
				return "redirect:/homescreenimage";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/homescreenimage/getimages", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getHomeScreenImage(RedirectAttributes redirectAttributes) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				response.setDetail(
						homeScreenImageApi.findByBank(AuthenticationUtil.getCurrentUser().getAssociatedId()));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/homescreenimage/update", method = RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> updateHomeScreenImage(@RequestParam("homeScreenImageList") String json) {
		RestResponseDTO response = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				Gson gson = new GsonBuilder().create();
				Type listType = new TypeToken<ArrayList<HomeScreenImageDTO>>() {
				}.getType();
				List<HomeScreenImageDTO> homeScreenImageList = gson.fromJson(json, listType);
				homeScreenImageApi.updatePlacement(homeScreenImageList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/homescreenimage/remove", method = RequestMethod.GET)
	public String removeHomeScreenImage(@RequestParam("image") Long id, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK + "," + Authorities.AUTHENTICATED)
					|| authority.equals(Authorities.BANK_ADMIN + "," + Authorities.AUTHENTICATED)) {
				homeScreenImageApi.delete(id);
				redirectAttributes.addFlashAttribute("message", "Homescreen Image Removed Successfully");
				return "redirect:/homescreenimage";
			}
		}
		return "redirect:/";
	}

}
