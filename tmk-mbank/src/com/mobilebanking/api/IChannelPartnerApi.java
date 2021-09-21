package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.ChannelPartnerDTO;

public interface IChannelPartnerApi {
	
	void saveChannelPartner(ChannelPartnerDTO channelPartnerDto);
	
	ChannelPartnerDTO findChannelPartnerById(Long id);
	
	List<ChannelPartnerDTO> findAllChannelPartner();

	ChannelPartnerDTO editChannelPartner(ChannelPartnerDTO channelPartnerDto);

	List<ChannelPartnerDTO> getAllChannelPartners();

	List<ChannelPartnerDTO> getChannelPartnerByNameLike(String name);

}
