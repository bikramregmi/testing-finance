package com.mobilebanking.controller;

import com.mobilebanking.api.IBankBranchApi;
import com.mobilebanking.api.IMiniStatementRequestApi;
import com.mobilebanking.api.IUserApi;
import com.mobilebanking.model.BankBranchDTO;
import com.mobilebanking.model.UserDTO;
import com.mobilebanking.model.mobile.MiniStatementRequestDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MiniStatementRequestController {


    @Autowired
    private IMiniStatementRequestApi miniStatementRequestApi;
    @Autowired
    IUserApi userApi;
    @Autowired
    IBankBranchApi bankBranchApi;

    @RequestMapping(method = RequestMethod.GET, value = "/miniStatement/list")
    public String statementRequest(ModelMap modelMap, HttpServletRequest request) {
        if (AuthenticationUtil.getCurrentUser() != null) {
            String authority = AuthenticationUtil.getCurrentUser().getAuthority();
            if (authority.contains(Authorities.BANK_BRANCH) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_BRANCH_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
                UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
                List<MiniStatementRequestDTO> statementRequestList = miniStatementRequestApi.findMiniStatementRequestByBranch(user.getAssociatedId());
                modelMap.put("statementRequestList", statementRequestList);
                return "StatementRequest/" + "statementrequest";
            }else if (authority.contains(Authorities.BANK) && authority.contains(Authorities.AUTHENTICATED)||authority.contains(Authorities.BANK_ADMIN) && authority.contains(Authorities.AUTHENTICATED)) {
                UserDTO user = userApi.getUserWithId(AuthenticationUtil.getCurrentUser().getId());
                List<BankBranchDTO> bankBranchList = bankBranchApi.listBankBranchByBank(user.getAssociatedId());
                List<MiniStatementRequestDTO> statementRequestList = miniStatementRequestApi.findMiniStatementRequestByBank(user.getAssociatedId());
                modelMap.put("statementRequestList", statementRequestList);
                modelMap.put("bankBranchList", bankBranchList);
                modelMap.put("bank", true);
                return "StatementRequest/" + "statementrequest";
            }
        }
        return "redirect:/";
    }

}
