package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.impl.INoticeApi;
import com.mobilebanking.model.NoticeDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

@Controller
public class NoticeController {

	@Autowired
	private INoticeApi noticeApi;
	
	@RequestMapping(value="/notice/add", method=RequestMethod.GET)
	public String addNotice(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)||authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				return "notice/notice";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/notice/add", method=RequestMethod.POST)
	public String addNotices(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response,@RequestParam("notice")String notice, @RequestParam("title")String title ) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)||authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
		
				if(noticeApi.saveNotice(title,notice,AuthenticationUtil.getCurrentUser().getAssociatedId())){
					return "redirect:/listNotice";
				}
				
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/listNotice", method=RequestMethod.GET)
	public String listNotice(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)||authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
			
				List<NoticeDTO> noticeList = noticeApi.listNotice(AuthenticationUtil.getCurrentUser().getAssociatedId());
				modelMap.put("noticeList", noticeList);
				return "notice/noticelist";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/notice/edit", method=RequestMethod.POST)
	public String editNotices(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response,@RequestParam("notice")String notice,@RequestParam("id")Long id,@RequestParam("title")String title) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)||authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
		
				if(noticeApi.editNotice(id,title, notice)){
					return "redirect:/listNotice";
				}
				
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/notice/edit", method=RequestMethod.GET)
	public String editNotice(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response,@RequestParam("notice")Long id) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)||authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
		
				NoticeDTO notice = noticeApi.getNoticeById(id);
				modelMap.put("notice", notice);
				return "notice/editnotice";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
}
