package com.mobilebanking.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.ICityApi;
import com.mobilebanking.api.ICountryApi;
import com.mobilebanking.api.IStateApi;
import com.mobilebanking.entity.State;
import com.mobilebanking.model.CityDTO;
import com.mobilebanking.model.CountryDTO;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.model.error.StateError;
import com.mobilebanking.parser.BulkUploadParser;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.validation.StateValidation;

@Controller
public class StateController implements MessageSourceAware {

	private Logger logger = LoggerFactory.getLogger(StateController.class);

	@Autowired
	private IStateApi stateApi;
	@Autowired
	private StateValidation stateValidation;
	@Autowired
	private ICountryApi countryApi;
	@Autowired
	private ICityApi cityApi;
	
	private MessageSource messageSource;
	
	@Autowired
	private BulkUploadParser bulkUploadParser;

	/*public StateController(IStateApi stateApi, StateValidation stateValidation, ICountryApi countryApi) {
		this.stateApi = stateApi;
		this.stateValidation = stateValidation;
		this.countryApi = countryApi;

	}
*/
	@RequestMapping(method = RequestMethod.GET, value = "/addState")
	public String addState(ModelMap modelMap, HttpServletRequest request) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				List<CountryDTO> countryList = new ArrayList<CountryDTO>();
				countryList = countryApi.findCountry();
				modelMap.put("countryList", countryList);
				return "State/" + "addState";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addState")
	public String addState(ModelMap modelMap, @ModelAttribute("state") StateDTO stateDTO, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				if (stateDTO == null) {
					logger.debug("state null");
					return "redirect:/Static/500";
				} else {
					try {
						StateError stateError = new StateError();
						stateError = stateValidation.stateValidation(stateDTO);
						logger.debug("valid" + stateError.isValid());
						if (stateError.isValid()) {
							stateApi.saveState(stateDTO);

							redirectAttributes.addFlashAttribute("message", "state.Added.Successfully");
							return "redirect:/listState";
						} else {
							logger.debug("error while adding");
							modelMap.put("error", stateError);
							modelMap.put("state", stateDTO);
							return "State/addState";
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

	@RequestMapping(method = RequestMethod.GET, value = "/listState")
	public String listState(ModelMap modelMap,
			@RequestParam(value = "pageNo", required = false) Integer page, 
	@RequestParam(value = "name", required = false) String searchWord, HttpServletRequest request, HttpServletResponse response, Model model) {

		PagablePage stateDTOList = stateApi.getAllState(page, searchWord);
		modelMap.put("stateList", stateDTOList);
		modelMap.put("searchWord", (searchWord==null) ? "" : searchWord);
		
		logger.debug("agentList" + stateDTOList);
		return "/State/listState";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/editstate")
	public String editState(ModelMap modelMap, @RequestParam(value = "stateid", required = true) String stateId,
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				State state = stateApi.getStateById(Long.parseLong(stateId));
				modelMap.put("state", state);
				return "State/editState";
			}
		}
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editState")
	public String editState(ModelMap modelMap, @ModelAttribute("state") StateDTO stateDTO, RedirectAttributes redirectAttributes) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
					try {
						StateError stateError = new StateError();
						stateError = stateValidation.stateEditValidation(stateDTO);
						if (stateError.isValid()) {
							logger.debug("error valid=" + stateError.isValid());
							stateApi.editState(stateDTO);

							redirectAttributes.addFlashAttribute("message", "State Edited Successfully");
							return "redirect:/listState";
						} else {
							logger.debug("error during edit");
							modelMap.put("error", stateError);
							modelMap.put("state", stateDTO);
							return "State/editState";
						}
					} catch (Exception e) {
						logger.debug("exception occured=>" + e);
						return "redirect:/Static/500";
					}
			}
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/ajax/state/getCitiesByState")
	@ResponseBody
	public Map<String, List<CityDTO>> getCitiesByState(@RequestParam("state") String state, 
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		Map<String, List<CityDTO>> citiesListMap = new HashMap<String, List<CityDTO>>();
		List<CityDTO> citiesList = cityApi.findCityByStateName(state);
		citiesListMap.put("citiesList", citiesList);
		
		return citiesListMap;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	@RequestMapping(method = RequestMethod.POST, value = "/bulkStateUpload")
	public ResponseEntity<RestResponseDTO> bulkBranchUpload(ModelMap modelMap, Model model, HttpServletRequest request,
			@RequestParam("file") MultipartFile file, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		RestResponseDTO restResponseDto = new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED)) {
				

				ServletContext context = request.getSession().getServletContext();
				String path = context.getRealPath("");

				File theDir = new File(path + "\\" + "SatateBulkUpload");

				if (!theDir.exists()) {
					try {
						theDir.mkdir();

					} catch (SecurityException se) {

					}

				}
				
				if (!file.isEmpty()) {
					if (!file.getOriginalFilename().equals("")) {
						
						List<StateDTO> stateList = new ArrayList<StateDTO>();
						
						//parsing file
						file.transferTo(
								new File(path + "\\" + "SatateBulkUpload" + "\\" + file.getOriginalFilename()));
						
						stateList = bulkUploadParser.parseStateFile(file);
						
						if (stateList.size() > 0) {
							for (StateDTO state : stateList) {

								if (!(state == null)) {
									stateApi.saveBulkState(state);
									
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
