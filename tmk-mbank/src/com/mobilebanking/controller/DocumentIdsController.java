/**
 * 
 */
package com.mobilebanking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IDocumentIdsApi;
import com.mobilebanking.model.DocumentIdsDTO;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

/**
 * @author bibek
 *
 */
@Controller
public class DocumentIdsController implements MessageSourceAware {
	
	@Autowired
	private IDocumentIdsApi documentIdApi;
	
	@RequestMapping(value="/documentids/add", method=RequestMethod.GET)
	public String addDocumentIds(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR)) {
				return "DocumentIds/add";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/documentids/add", method=RequestMethod.POST)
	public String addDocumentIds(ModelMap modelMap, @ModelAttribute("document") DocumentIdsDTO documentIdsDto, 
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (documentIdsDto == null) {
			return "redirect:/documentids/add";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR)) {
				try {
					documentIdApi.save(documentIdsDto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return "redirect:/documentids/list";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/documentids/list", method=RequestMethod.GET)
	public String listDocumentIds(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR)) {
				List<DocumentIdsDTO> documentsList = documentIdApi.getAllDocumentIds();
				modelMap.put("documentsList", documentsList);
				return "DocumentIds/list";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/docuemntids/edit", method=RequestMethod.GET)
	public String editDocumentIds(ModelMap modelMap, @RequestParam("document") long documentId, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR)) {
				DocumentIdsDTO documentIds = documentIdApi.getDocumentById(documentId);
				modelMap.put("document", documentIds);
				return "DocumentIds/edit";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="documentids/edit", method=RequestMethod.POST)
	public String editDocumentIds(ModelMap modelMap, @ModelAttribute("document") DocumentIdsDTO documentIdsDto, 
			HttpServletRequest request, HttpServletResponse response) {
		if (documentIdsDto == null) {
			return "redirect:/documentids/list";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.AUTHENTICATED+","+Authorities.ADMINISTRATOR)) {
				try {
					documentIdApi.edit(documentIdsDto);
					return "redirect:/documentids/list";
				} catch (Exception e) {
					return "redirect:/documentids/edit?document="+documentIdsDto.getId();
				}
				
			}
		}
		return "redirect:/";
	}
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

}
