package com.mobilebanking.controller;

import com.mobilebanking.api.impl.BankApi;
import com.mobilebanking.api.impl.LicenseCountLogApi;
import com.mobilebanking.api.impl.UserApi;
import com.mobilebanking.entity.LicenseCountLog;
import com.mobilebanking.model.*;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LicenseCountLogController {

    @Autowired
    private LicenseCountLogApi licenseCountLogApi;
    @Autowired
    private UserApi userApi;

    public LicenseCountLogController() {
    }

    private BankApi bankApi;
    public LicenseCountLogController(BankApi bankApi){this.bankApi=bankApi;}

    @RequestMapping(value = "/listLicenseCount/list", method = RequestMethod.GET)
    public String getLicenseCountLog(ModelMap modelMap) {
        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();
            if (authority.contains(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
                List<LicenseCountLogDTO> licenseCountLogList = licenseCountLogApi.findLicenseCountLog();
                modelMap.put("licenseCountLogList", licenseCountLogList);
                modelMap.put("bank", true);

                return "LicenseCountLog/" + "licensecount";

            }
//        }
        }
        return "redirect:/";


    }
    private RestResponseDTO getResponseDto(ResponseStatus status, String message) {
        RestResponseDTO restResponseDto = new RestResponseDTO();

        restResponseDto.setResponseStatus(status);
        restResponseDto.setMessage(message);

        return restResponseDto;
    }
    @RequestMapping(value = "/addLicenseCount", method = RequestMethod.POST)
    public ResponseEntity<RestResponseDTO> addLicenseCount(HttpServletRequest request,
                                                           @ModelAttribute("bankId") Long bankId, @ModelAttribute("licenseCount") Integer licenseCount,@ModelAttribute("remarks") String remarks,
                                                           ModelMap modelMap, RedirectAttributes redirectAttributes) {

        RestResponseDTO restResponseDto = new RestResponseDTO();

        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();

            if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
                    || authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
                try {
                    boolean updateSmsCount = licenseCountLogApi.addLicenseCount(bankId, licenseCount);
                    if (updateSmsCount) {
						restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED);
					} else {
						restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
						return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
					}
                    UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
                    LicenseCountLogDTO licenseCountLogDTO=new LicenseCountLogDTO();
                    licenseCountLogDTO.setUserId(user.getId());
                    licenseCountLogDTO.setBankId(bankId);
                    licenseCountLogDTO.setLicenseCount(licenseCount);
                    licenseCountLogDTO.setRemarks(remarks);
                    System.out.println(licenseCountLogDTO.toString());
                    licenseCountLogApi.saveRequest(licenseCountLogDTO);


//

                } catch (Exception e) {
                    e.printStackTrace();
                    restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
                    return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.OK);
    }


}