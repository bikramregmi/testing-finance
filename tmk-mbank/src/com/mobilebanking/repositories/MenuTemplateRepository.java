package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.MenuTemplate;

public interface MenuTemplateRepository extends CrudRepository<MenuTemplate, Long>, JpaSpecificationExecutor<MenuTemplate> {
	
	@Query("select s from MenuTemplate s order by s.name")
	List<MenuTemplate> findMenuTemplate();
	
	@Query("select s from MenuTemplate s where s.name=?1")
	MenuTemplate findByMenuTemplate(String name);
	
	@Query("select s.menu from MenuTemplate s where s.id=?1")
	List<Long> findmenus(long id);
	

}
