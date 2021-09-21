/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.OAuthClient;

/**
 * @author bibek
 *
 */
@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClient, String> {
	
	@Query("select c from OAuthClient c order by c.clientId asc")
	List<OAuthClient> getAllOAuthClient();

}
