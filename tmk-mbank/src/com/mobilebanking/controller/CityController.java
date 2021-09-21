package com.mobilebanking.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.entity.City;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.CityError;
import com.mobilebanking.parser.BulkUploadParser;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.CityValidation;

@Controller
public class CityController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private ICityApi cityApi;
	@Autowired
	private CityValidation cityValidation;
	@Autowired
	private IStateApi stateApi;

	private MessageSource messageSource;

	@Autowired
	private BulkUploadParser bulkUploadParser;

	/*
	 * public CityController(ICityApi cityApi, CityValidation cityValidation,
	 * IStateApi stateApi) { this.cityApi = cityApi; this.cityValidation =
	 * cityValidation; this.stateApi = stateApi; }
	 */

	@RequestMapping(method = RequestMethod.GET, value = "/addCity")
	public String addCity(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<StateDTO> stateList = new ArrayList<StateDTO>();
				stateList = stateApi.findState();
				modelMap.put("stateList", stateList);
				return "City/" + "addCity";

			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addCity")
	public String addCity(ModelMap modelMap, @ModelAttribute("city") CityDTO cityDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (cityDTO == null) {
					logger.debug("city null");
					return "redirect:/Static/500";
				} else {
					try {
						CityError cityError = new CityError();
						cityError = cityValidation.cityValidation(cityDTO);
						logger.debug("valid" + cityError.isValid());
						if (cityError.isValid()) {
							cityApi.saveCity(cityDTO);

							redirectAttributes.addFlashAttribute("message", "city.Added.Successfully");
							return "redirect:/listCity";
						} else {
							logger.debug("error while adding");
							modelMap.put("error", cityError);
							modelMap.put("city", cityDTO);
							return "City/addCity";
						}

					} catch (Exception e) {
						logger.debug("exception" + e);
						return "redirect:/Static/500";
					}
				}

			}
		}
		return "redirect:/";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/listCity")
	public String listCity(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response, Model model) {

		List<CityDTO> cityDTOList = new ArrayList<CityDTO>();
		cityDTOList = cityApi.getAllCity();
		modelMap.put("cityList", cityDTOList);
		logger.debug("citylist" + cityDTOList);
		return "/City/listCity";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editcity")
	public String editCity(ModelMap modelMap, @RequestParam(value = "cityid", required = true) String cityId) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				City city = cityApi.getCityById(Long.parseLong(cityId));
				modelMap.put("city", city);
				return "City/editCity";

			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editCity")
	public String editCity(ModelMap modelMap, @ModelAttribute("city") CityDTO cityDTO,
			RedirectAttributes redirectAttributes) {
		logger.debug("at edit city");
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				try {
					CityError cityError = new CityError();
					cityError = cityValidation.cityEditValidation(cityDTO);
					if (cityError.isValid()) {
						logger.debug("error valid=" + cityError.isValid());
						cityApi.editCity(cityDTO);
						redirectAttributes.addFlashAttribute("message", "City Edited Successfully");
						return "redirect:/listCity";
					} else {
						logger.debug("error during edit");
						modelMap.put("error", cityError);
						modelMap.put("city", cityDTO);
						return "City/editCity";
					}
				} catch (Exception e) {
					logger.debug("exception occured=>" + e);
					return "redirect:/Static/500";
				}
			}

		}
		return "redirect:/";
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/bulkCityUpload")
	public ResponseEntity<RestResponseDTO> bulkCityUpload(ModelMap modelMap, Model model, HttpServletRequest request,
			@RequestParam("file") MultipartFile file, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {

				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("");

				File theDir = new File(path + "\\" + "CityBulkUpload");

				if (!theDir.exists()) {
					try {
						theDir.mkdir();

					} catch (SecurityException se) {

					}

				}

				if (!file.isEmpty()) {
					if (!file.getOriginalFilename().equals("")) {

						List<CityDTO> cityList = new ArrayList<CityDTO>();

						// parsing file
						file.transferTo(new File(path + "\\" + "CityBulkUpload" + "\\" + file.getOriginalFilename()));

						cityList = bulkUploadParser.parseCityFile(file);

						if (cityList.size() > 0) {
							for (CityDTO city : cityList) {

								if (!(city == null)) {
									cityApi.saveBulkCity(city);

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

}
