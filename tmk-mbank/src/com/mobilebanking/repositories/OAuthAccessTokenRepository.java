package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.OAuthAccessToken;

@Repository
public interface OAuthAccessTokenRepository extends JpaRepository<OAuthAccessToken, String>{
	
	@Query("Select o from OAuthAccessToken o where o.user_name=?1")
	List<OAuthAccessToken> findByUsername(String userName);

	@Query("Select count(o) from OAuthAccessToken o where o.user_name=?1")
	Long countByUsername(String userName);

	@Query("Delete from OAuthAccessToken o where o.user_name=?1")
	void deleteByUsername(String userName);

}
