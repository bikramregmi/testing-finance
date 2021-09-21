package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.IMenuApi;
import com.mobilebanking.entity.Menu;
import com.mobilebanking.model.MenuDTO;
import com.mobilebanking.model.MenuType;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.MenuRepository;
import com.mobilebanking.util.ConvertUtil;

@Service
@Transactional
public class MenuApi implements IMenuApi {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Menu saveMenu(Menu menu) {
		Menu mm = menuRepository.save(menu);
		return mm;
	}

	@Override
	public void saveMenu(MenuDTO menuDto) {
		Menu menu = new Menu();
		menu.setName(menuDto.getName());
		menu.setUrl(menuDto.getUrl());
		menu.setStatus(Status.Active);
		Menu superMenu = menuRepository.findByMenuByUrl(menuDto.getSuperMenu());
		if (superMenu == null) {
			menu.setMenuType(MenuType.SuperMenu);
		} else {
			menu.setMenuType(MenuType.SubMenu);
			menu.setSuperId(superMenu.getId());
		}
		menuRepository.save(menu);
	}

	@Override
	public void editMenu(MenuDTO menuDto) {
		Menu menu = menuRepository.findOne(menuDto.getId());
		menu = ConvertUtil.convertDtoToMenu(menuDto, menu);
		menuRepository.save(menu);
	}

	@Override
	public Menu getMenuWithId(long menuId) {
		return menuRepository.findOne(menuId);
	}

	public MenuDTO getMenuDtoById(long menuId) {
		return ConvertUtil.convertMenu(menuRepository.findOne(menuId));
	}

	@Override
	public List<Menu> getAllMenu() {
		List<Menu> copy = ConvertUtil.convertIterableToList(menuRepository.findAll());
		return copy;
	}

	@Override
	public List<MenuDTO> findMenu() {
		List<Menu> menuList = new ArrayList<Menu>();
		menuList = menuRepository.findMenu();
		return ConvertUtil.ConvertMenuDTOtoMenu(menuList);
	}

	public List<MenuDTO> findAllMenu() {
		Iterable<Menu> menuIterable = menuRepository.findAll();
		List<MenuDTO> menuDtoList = new ArrayList<MenuDTO>();
		for (Menu menu : menuIterable) {
			menuDtoList.add(ConvertUtil.convertMenu(menu));
		}
		return menuDtoList;
	}

	@Override
	public List<MenuDTO> findAllSuperMenu() {
		List<Menu> menu = ConvertUtil.convertIterableToList(menuRepository.findBySuperMenu(MenuType.SuperMenu));
		return ConvertUtil.ConvertMenuDTOtoMenu(menu);
	}

	@Override
	public List<MenuDTO> getAllSubMenuExcNot(long templateId) {

		List<Menu> copy = ConvertUtil
				.convertIterableToList(menuRepository.getAllSubMenuExcNot(templateId, MenuType.SubMenu));
		return ConvertUtil.ConvertMenuDTOtoMenu(copy);
	}

	@Override
	public List<MenuDTO> getAllSubMenuIncluded(long templateId) {
		List<Menu> copy = ConvertUtil
				.convertIterableToList(menuRepository.getAllSubMenuIncluded(templateId, MenuType.SubMenu));
		return ConvertUtil.ConvertMenuDTOtoMenu(copy);
	}

}
