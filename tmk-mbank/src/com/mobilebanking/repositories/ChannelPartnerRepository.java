/**
 * 
 */
package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.ChannelPartner;


/**
 * @author bibek
 *
 */
@Repository
public interface ChannelPartnerRepository
		extends CrudRepository<ChannelPartner, Long>, JpaSpecificationExecutor<ChannelPartner> {
	
	@Query("select cp from ChannelPartner cp where cp.id=:id")
	ChannelPartner getChannelPartnerById(@Param("id") Long id);
	
	@Query("select cp from ChannelPartner cp order by cp.name asc")
	List<ChannelPartner> getAllChannelParter();

	ChannelPartner findByUniqueCode(String channelPartner);
    @Query("select cp from ChannelPartner cp where cp.name like concat(?1, '%') order by cp.name asc")
	List<ChannelPartner> getChannelPartnerByNameLike(String name);

}
