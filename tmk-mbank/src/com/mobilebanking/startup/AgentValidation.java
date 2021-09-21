package com.mobilebanking.startup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Menu;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.repositories.MenuTemplateRepository;
import com.mobilebanking.repositories.UserTemplateRepository;

@Component
public class AgentValidation {
	
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private MenuTemplateRepository menuTemplateRepository;
	
	@Autowired
	private UserTemplateRepository userTemplateRepository;
	
	
	public void startupCreator(){
		
		List<String> menus =new ArrayList<String>();
		menus.add("tmksupermenu, Dashboard-dashboard");
		menus.add("Users,Add User-addUser");
		menus.add("Users,Edit User-editUser");
		menus.add("tmksupermenu, Commission-commission");
		menus.add("Commisssion, Add Commission-addCommission");
		menus.add("Commisssion, Edit Commission-editCommission");
		
		
		for(String m: menus){
			
			String[] first= m.split(",");
			
			if(first[0].equals("tmksupermenu")){
				Menu menu = new Menu();
				String[] second= first[1].split("-");
				menu.setName(second[0]);
				menu.setUrl(second[1]);
				menu.setStatus(Status.Active);
				menu.setMenuType(MenuType.SuperMenu);
				menuRepository.save(menu);
			}else{
				Menu menu = new Menu();
				String[] second= first[1].split("-");
				menu.setName(second[0]);
				menu.setUrl(second[1]);
				menu.setStatus(Status.Active);
				menu.setMenuType(MenuType.SubMenu);
				Menu superMenu =  menuRepository.findByMenu(first[0]);
				menu.setSuperId(superMenu.getId());
				
				menuRepository.save(menu);
			}
			
			
			
		}
		
		
		
	}
	
	
		

}
