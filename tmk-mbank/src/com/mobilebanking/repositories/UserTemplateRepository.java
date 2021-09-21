package com.mobilebanking.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.UserTemplate;
import com.mobilebanking.model.UserType;

public interface UserTemplateRepository extends CrudRepository<UserTemplate, Long>, JpaSpecificationExecutor<UserTemplate> {
	
//	@Query("select s from UserTemplate s order by s.name")
//	List<UserTemplate> findUserTemplate();
	
//	@Query("select s from UserTemplate s where s.name=?1")
//	UserTemplate findByUserTemplate(String name);
	
	@Query("select u from UserTemplate u where u.userTemplateName=?1")
	UserTemplate findByUserTemplate(String name);
	
	@Query("select u from UserTemplate u where u.id=?1")
	UserTemplate findByid(long id);

	@Query("select u from UserTemplate u where u.usertype=?1")
	List<UserTemplate> findByUsertype(UserType userType);

	@Query("select u from UserTemplate u where u.menuTemplate.id=?1")
	UserTemplate findByMenuTemplate(Long id);


}
