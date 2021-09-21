/**
 * 
 */
package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.ActionLog;

/**
 * @author bibek
 *
 */
public interface ActionLogRepository extends CrudRepository<ActionLog, Long>, JpaSpecificationExecutor<ActionLog> {
	
}
