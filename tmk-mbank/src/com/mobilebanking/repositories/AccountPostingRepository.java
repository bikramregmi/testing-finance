/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.AccountPosting;

/**
 * @author bibek
 *
 */
@Repository
public interface AccountPostingRepository extends JpaRepository<AccountPosting, Long> {

}
