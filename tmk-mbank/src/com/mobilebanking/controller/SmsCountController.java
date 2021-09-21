package com.mobilebanking.controller;

import com.mobilebanking.api.impl.BankApi;
import com.mobilebanking.api.impl.LicenseCountLogApi;
import com.mobilebanking.api.impl.SmsCountApi;
import com.mobilebanking.api.impl.UserApi;
import com.mobilebanking.model.LicenseCountLogDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.SmsCountDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import com.mobilebanking.util.StringConstants;
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
public class SmsCountController {
    @Autowired
    private SmsCountApi smsCountApi;
    @Autowired
    private UserApi userApi;

    public SmsCountController() {
    }

    private BankApi bankApi;
    public SmsCountController(BankApi bankApi){this.bankApi=bankApi;}

    @RequestMapping(value = "/listSmsCount/list", method = RequestMethod.GET)
    public String getSmsCountLog(ModelMap modelMap) {
        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();
            if (authority.contains(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)) {
                List<SmsCountDTO> smsCountLogList = smsCountApi.findSmsCountLog();
                modelMap.put("smsCountLogList", smsCountLogList);
                modelMap.put("bank", true);

                return "SmsCountLog/" + "smscountlog";

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
    @RequestMapping(value = "/addSmsCount", method = RequestMethod.POST)
    public ResponseEntity<RestResponseDTO> addLicenseCount(HttpServletRequest request,
                                                           @ModelAttribute("bankId") Long bankId, @ModelAttribute("smsCount") Integer smsCount, @ModelAttribute("remarks") String remarks,
                                                           ModelMap modelMap, RedirectAttributes redirectAttributes) {

        RestResponseDTO restResponseDto = new RestResponseDTO();

        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();

            if ((authority.equals(Authorities.ADMINISTRATOR + "," + Authorities.AUTHENTICATED)
                    || authority.equals(Authorities.AUTHENTICATED + "," + Authorities.ADMINISTRATOR))) {
                try {
                    boolean updateSmsCount = smsCountApi.addSmsCount(bankId, smsCount);
                    if (updateSmsCount) {
                        restResponseDto = getResponseDto(ResponseStatus.SUCCESS, StringConstants.DATA_SAVED);
                    } else {
                        restResponseDto = getResponseDto(ResponseStatus.FAILURE, StringConstants.DATA_SAVING_ERROR);
                        return new ResponseEntity<RestResponseDTO>(restResponseDto, HttpStatus.BAD_REQUEST);
                    }
                    UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
                    SmsCountDTO smsCountDTO=new SmsCountDTO();
                    smsCountDTO.setUserId(user.getId());
                    smsCountDTO.setBankId(bankId);
                    smsCountDTO.setSmsCount(smsCount);
                    smsCountDTO.setRemarks(remarks);
//                    System.out.println(smsCountDTO.toString());
                    smsCountApi.saveRequest(smsCountDTO);
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
