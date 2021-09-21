/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.DocumentIds;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
@Repository
public interface DocumentIdsRepository extends JpaRepository<DocumentIds, Long>{

	@Query("select d from DocumentIds d order by d.documentType asc")
	List<DocumentIds> findAllDocumentsIds();
	
	@Query("select d from DocumentIds d where d.status=:status")
	List<DocumentIds> findDocumentsIdsByStatus(@Param("status") Status status);
	
	@Query("select d from DocumentIds d where d.documentType=:dType")
	DocumentIds findDocumentByDocumentType(@Param("dType") String documentType);
}
