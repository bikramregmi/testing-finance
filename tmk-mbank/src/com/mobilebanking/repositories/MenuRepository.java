package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.Menu;
import com.mobilebanking.model.MenuType;

public interface MenuRepository extends CrudRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

	@Query("select s from Menu s order by s.name")
	List<Menu> findMenu();
	
	@Query("select s from Menu s where s not in (select m from Menu m inner join m.menuTemplate t where t in(select mt from MenuTemplate mt where mt.id=?1)) and  s.menuType=?2")
	List<Menu> getAllSubMenuExcNot(long templateId, MenuType menuType);
	
	@Query("select s from Menu s where s.name=?1")
	Menu findByMenu(String name);
	
	@Query("select s from Menu s where s.url=?1")
	Menu findByMenuByUrl(String name);
	
	@Query("select s from Menu s where s in (select m from Menu m inner join m.menuTemplate t where t in(select mt from MenuTemplate mt where mt.id=?1)) and s.menuType=?2")
	List<Menu> getAllSubMenuIncluded(long id, MenuType menuType);
	
	@Query("select s from Menu s where s.menuType=?1")
	List<Menu> findBySuperMenu(MenuType menuType);
	
	@Query("select s from Menu s where s not in (select m from Menu m inner join m.menuTemplate t where t in(select mt from MenuTemplate mt where mt.id=?1)) and  s.menuType=?2 and s.id=(select sm.superId from Menu sm where sm.url =?3)")
	Menu getParent(long id, MenuType menuType, String url);
	
	@Query("select s from Menu s where s not in (select m from Menu m inner join m.menuTemplate t where t in(select mt from MenuTemplate mt where mt.id=?1)) and  s.menuType=?2 and s.url=?3")
	Menu menuValidationforTemplate(long id, MenuType menuType, String url);

	@Query("select m from Menu m where m.superId=?1")
	List<Menu> getSubMenus(long superId);
	
}
