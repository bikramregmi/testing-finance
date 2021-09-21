/**
 * 
 */
package com.mobilebanking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobilebanking.api.IBankApi;
import com.mobilebanking.api.ISmsModeApi;
import com.mobilebanking.model.BankDTO;
import com.mobilebanking.model.RestResponseDTO;
import com.mobilebanking.model.SmsModeDto;
import com.mobilebanking.model.SmsType;
import com.mobilebanking.model.Status;
import com.mobilebanking.util.AuthenticationUtil;
import com.mobilebanking.util.Authorities;

/**
 * @author bibek
 *
 */
@Controller
public class SmsModeController implements MessageSourceAware {
	
	@Autowired
	private ISmsModeApi smsModeApi;
	
	@Autowired
	private IBankApi bankApi;
	
	@RequestMapping(value="/smsmode/list", method=RequestMethod.GET)
	public String listSmsMode(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
						authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
					BankDTO bankDto = bankApi.getBankDtoById(
							AuthenticationUtil.getCurrentUser().getAssociatedId());
					List<SmsModeDto> smsModeList = smsModeApi.findSmsModeByBank(bankDto.getName());
					modelMap.put("smsModeList", smsModeList);
				} else {
					List<SmsModeDto> smsModeList = smsModeApi.findAllSmsMode();
					modelMap.put("smsModeList", smsModeList);
					modelMap.put("admin", "admin");
				}
				
				return "SmsMode/list";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/smsmode/add", method=RequestMethod.GET)
	public String addSmsMode(ModelMap modelMap, RedirectAttributes redirectAttributes, 
			HttpServletRequest request, HttpServletResponse response) {
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED)) {
				SmsType[] smsType = SmsType.values();
				List<BankDTO> bankList = bankApi.geteBankByStatus(Status.Active);
				modelMap.put("smsType", smsType);
				modelMap.put("bankList", bankList);
				return "SmsMode/add";
			}
			if (authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				SmsType[] smsType = SmsType.values();
				boolean forBank = true;
				BankDTO bank = bankApi.getBankDtoById(AuthenticationUtil.getCurrentUser().getAssociatedId());
				modelMap.put("smsType", smsType);
				modelMap.put("bank", bank);
				modelMap.put("forBank", forBank);
				return "SmsMode/add";
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/smsmode/add", method=RequestMethod.POST)
	public String addSmsMode(ModelMap modelMap, @ModelAttribute("smsMode") SmsModeDto smsModeDto, HttpServletRequest request, 
			HttpServletResponse response) {
		if (smsModeDto == null) {
			return "redirect:/smsmode/add";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				try {
					
					smsModeDto.setCreatedBy(""+AuthenticationUtil.getCurrentUser().getId());
					SmsModeDto smsMode = smsModeApi.save(smsModeDto);
					modelMap.put("smsMode", smsMode);
					return "redirect:/smsmode/list";
				} catch (Exception e) {
					SmsType[] smsType = SmsType.values();
					List<BankDTO> bankList = bankApi.geteBankByStatus(Status.Active);
					
					modelMap.put("smsType", smsType);
					modelMap.put("bankList", bankList);
					modelMap.put("smsMode", smsModeDto);
					return "SmsMode/add";
				}
			}
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="smsmode/edit", method=RequestMethod.GET)
	public String editSmsMode(@RequestParam("smsmode") Long smsModeId, ModelMap modelMap, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (smsModeId == null) {
			return "redirect:/smsmode/list";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				SmsModeDto smsMode = smsModeApi.findSmsModeById(smsModeId);
				modelMap.put("smsMode", smsMode);
				modelMap.put("smsType",SmsType.values());
				return "SmsMode/edit";
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="smsmode/edit", method=RequestMethod.POST)
	public String editSmsMode(ModelMap modelMap, @ModelAttribute("smsmode") SmsModeDto smsModeDto, 
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (smsModeDto == null) {
			return "redirect:/smsmode/list";
		}
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) || 
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				try { 
					System.out.println(smsModeDto.getMessage() + " SMS MESSAGE");
					smsModeApi.edit(smsModeDto);
					return "redirect:/smsmode/list";
				} catch (Exception e) {
					e.printStackTrace();
					modelMap.put("smsMode", smsModeDto);
					return "redirect:/smsmode/edit?smsmode="+smsModeDto.getId();
				}
				
			}
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/ajax/smsmode/getSmsModeByBank", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, List<SmsModeDto>> getSmsModeByBank(@RequestParam("bank") String bankName, 
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		Map<String, List<SmsModeDto>> smsResponseMap = new HashMap<String, List<SmsModeDto>>();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED) ||
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				List<SmsModeDto> smsModeList = smsModeApi.findSmsModeByBank(bankName);
				smsResponseMap.put("smsModeList",smsModeList);
			}
		}
		return smsResponseMap;
	}
	
	//added by amrit
	@ResponseBody
	@RequestMapping(value="/sms-type-list-for-bank", method=RequestMethod.GET)
	public ResponseEntity<RestResponseDTO> getSmsTypeNotInABank(@RequestParam(value="bank",required=true) String bankName){
		RestResponseDTO restResponseDto=new RestResponseDTO();
		if (AuthenticationUtil.getCurrentUser() != null) {
			String authority = AuthenticationUtil.getCurrentUser().getAuthority();
			if (authority.contains(Authorities.ADMINISTRATOR) && authority.contains(Authorities.AUTHENTICATED) ||
					authority.equals(Authorities.BANK+","+Authorities.AUTHENTICATED) ||
					authority.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
		          restResponseDto.setDetail(smsModeApi.getSmsTypesForBankToAdd(bankName));
			    restResponseDto.setMessage("sucsess");
			    return(new ResponseEntity<>(restResponseDto,HttpStatus.OK));
			    
			}

			restResponseDto.setMessage("unuthorized authuntication");
			return(new ResponseEntity<>(restResponseDto,HttpStatus.UNAUTHORIZED));
		}
		restResponseDto.setMessage("unuthorized authuntication");
		return(new ResponseEntity<>(restResponseDto,HttpStatus.UNAUTHORIZED));
				
		
	}
	
	@RequestMapping(value="/smsmode/deactive_active", method=RequestMethod.GET)
	public String deactivateSmsMode(@RequestParam("smsmode") Long smsModeId, HttpServletRequest request, ModelMap modelmap, RedirectAttributes redirectAttributes) {
		if(smsModeId!=null) {
		  if(AuthenticationUtil.getCurrentUser()!=null) {
			  String authentication= AuthenticationUtil.getCurrentUser().getAuthority();
			  if(authentication.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED)
				  || authentication.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)
				  ||authentication.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				  try {
					
				  if(smsModeApi.changeStatus(smsModeId)) {
					
					  return "redirect:/smsmode/list";
				  }
				  }
				  catch(Exception e) {
					  return "redirect:/";
				  }
				  
			  }
		  }
		}
		 return "redirect:/";
	}
	
	@RequestMapping(value="/smsmode/remove", method=RequestMethod.GET)
	public String remove(@RequestParam("smsmode") Long smsModeId, HttpServletRequest request, ModelMap modelmap, RedirectAttributes redirectAttributes) {
		if(smsModeId!=null) {
		  if(AuthenticationUtil.getCurrentUser()!=null) {
			  String authentication= AuthenticationUtil.getCurrentUser().getAuthority();
			  if(authentication.equals(Authorities.ADMINISTRATOR+","+Authorities.AUTHENTICATED)
				  || authentication.equals(Authorities.BANK+","+Authorities.AUTHENTICATED)
				  ||authentication.equals(Authorities.BANK_ADMIN+","+Authorities.AUTHENTICATED)) {
				  try {
					
				  if(smsModeApi.removeCustomSms(smsModeId)) {
					
					  return "redirect:/smsmode/list";
				  }
				  }
				  catch(Exception e) {
					  return "redirect:/";
				  }
				  
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
