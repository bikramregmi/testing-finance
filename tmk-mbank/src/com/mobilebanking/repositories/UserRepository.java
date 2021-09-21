package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mobilebanking.entity.User;
import com.mobilebanking.model.UserType;

public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	@Query("select u from User u where u.userName=?1 and u.status!=2")
	User findByUsername(String username);
	
	@Query("select u from User u where u.userType <>'Admin' and u.status!=2")
	List<User> findAllUserExceptAdmin();
	
	@Query("select u from User u where NOT u.userName IN (?1) and u.status!=2")
	List<User> findAllUserExceptDefaultAdmin(List<String> str);
	
	@Query("select u from User u order by u.userName")
	List<User> findUser();

	@Query("select u from User u where u.webService.access_key=?1 and u.secretKey=?2 and u.status!=2")
	User findByAccessAndSecretKey(String accessKey, String secretKey);
	
	@Query("select u from User u where u.associatedId=?1 and u.status!=2")
	User findByAssociatedId(long associatedId);
	
	@Query("select u.userName from User u where u.associatedId=?1 and u.status!=2")
	String findUserNameByAssociatedId(long associatedId);
	
	@Query("select u.userName from User u where u.associatedId=?1 and u.authority=?2 and u.status!=2")
	String findUserNameByAssociatedIdAndAuthority(long associatedId,String authority);
	
	@Query("select u from User u where u.authority=:role and u.status!=2 order by u.userName asc")
	List<User> findUserByRole(@Param("role") String role);
	
	@Query("select u from User u where u.userType=?1 and u.associatedId=?2 and u.status!=2")
	User findByUserTypeAndAssociatedId(UserType userType, Long id);

	@Query("select u from User u where  u.userName=?1 and u.status!=2")
	User findUserByUniqueId(String uniqueId);
	
	@Query("select u.token from User u where u.token=:token and u.status!=2")
	String findUserNameByToken(@Param("token") String token);
	
	@Query("select u.verificationCode from User u where u.verificationCode=:vCode and u.status!=2")
	String findUserNameByVerificationCode(@Param("vCode") String verificationCode);
	
	@Query("select u.token from User u where u.userName=:userName and u.status!=2")
	String getUserToken(@Param("userName") String userName);

	@Query("select u from User u where u.userType=?2 and u.associatedId in (select b.id from BankBranch b where b.bank.id=?1) and u.status!=2")
	List<User> findBranchUserByBank(long bankId, UserType userType);

	@Query("select u from User u where u.userType=?1 and u.associatedId=?2 and u.status!=2")
	List<User> findUserListByUserTypeAndAssociatedId(UserType userType, long associatedId);

	@Query("select u from User u where u.userName like CONCAT(?1,'%') and u.status!=2")
	List<User> findUserByUserNameCONCAT(String data);

	@Query("select u from User u where u.id=?1")
	User findUserById(Long userId);
}
