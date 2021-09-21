package com.mobilebanking.webservice;

import com.mobilebanking.api.ICustomerApi;
import com.mobilebanking.api.ICustomerBankAccountApi;
import com.mobilebanking.api.IMiniStatementRequestApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.api.impl.CustomerApi;
import com.mobilebanking.entity.MiniStatementRequest;
import com.mobilebanking.model.CustomerBankAccountDTO;
import com.mobilebanking.model.CustomerDTO;
import com.mobilebanking.model.mobile.MiniStatementRequestDTO;
import com.mobilebanking.model.mobile.ResponseDTO;
import com.mobilebanking.model.mobile.ResponseStatus;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class MultiStatementRequestController {

    @Autowired
    private IMiniStatementRequestApi miniStatementRequestApi;

    @Autowired
    private IUserApi userApi;

    @Autowired
    private ICustomerApi customerApi;

    @Autowired
    private ICustomerBankAccountApi customerBankAccountApi;


    @RequestMapping(value = "/addRequest", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> addRequest(HttpServletRequest request,@ModelAttribute("request") MiniStatementRequestDTO miniStatementRequestDTO){
        ResponseDTO response = new ResponseDTO();
        if (request.getHeader("Authorization") == null) {
            response.setCode(ResponseStatus.FAILURE);
            response.setStatus("FAILURE");
            response.setDetails(new ArrayList<>());
            response.setMessage("Authorization not valid or empty");
            return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }
        if (miniStatementRequestDTO.getEmail() == null) {
            response.setCode(ResponseStatus.EMAIL_REQUIRED);
            response.setStatus("FAILURE");
            response.setDetails(new ArrayList<>());
            response.setMessage(ResponseStatus.EMAIL_REQUIRED.getValue());
            return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }
        if (miniStatementRequestDTO.getStartDate() == null) {
            response.setCode(ResponseStatus.START_DATE_REQUIRED);
            response.setStatus("FAILURE");
            response.setDetails(new ArrayList<>());
            response.setMessage(ResponseStatus.START_DATE_REQUIRED.getValue());
            return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }
        if (miniStatementRequestDTO.getEndDate() == null) {
            response.setCode(ResponseStatus.END_DATE_REQUIRED);
            response.setStatus("FAILURE");
            response.setDetails(new ArrayList<>());
            response.setMessage(ResponseStatus.END_DATE_REQUIRED.getValue());
            return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }
        if (miniStatementRequestDTO.getAccountNumber() == null) {
            response.setCode(ResponseStatus.ACCOUNT_NUMBER_REQUIRED);
            response.setStatus("FAILURE");
            response.setDetails(new ArrayList<>());
            response.setMessage(ResponseStatus.ACCOUNT_NUMBER_REQUIRED.getValue());
            return new ResponseEntity<ResponseDTO>(response, HttpStatus.BAD_REQUEST);
        }
        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();
            if (authority.equals(Authorities.CUSTOMER + "," + Authorities.AUTHENTICATED)) {
                try {
                    miniStatementRequestDTO.setCustomerId(AuthenticationUtil.getCurrentUser().getAssociatedId());
                    miniStatementRequestApi.saveRequest(miniStatementRequestDTO);
                    response.setStatus("SUCCCESS");
                    response.setCode(ResponseStatus.SUCCESS);
                    response.setMessage(ResponseStatus.SUCCESS.getValue());
                    response.setDetails(request.getParameter("device_token"));
                    return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

                } catch (Exception e) {
                    response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR);
                    response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getValue());
                    response.setDetails(new ArrayList<>());
                    e.printStackTrace();
                    return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        response.setCode(ResponseStatus.UNAUTHORIZED_USER);
        response.setStatus("FAILURE");
        response.setMessage(ResponseStatus.UNAUTHORIZED_USER.getValue());
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.UNAUTHORIZED);

    }
}
