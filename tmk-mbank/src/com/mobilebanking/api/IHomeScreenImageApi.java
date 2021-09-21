package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.HomeScreenImageDTO;

public interface IHomeScreenImageApi {

	List<HomeScreenImageDTO> findByBank(long bankId);

	void savaNewHomeScreenImage(HomeScreenImageDTO homeScreenImageDTO);

	void updatePlacement(List<HomeScreenImageDTO> homeScreenImageList);

	void delete(Long id);

	List<String> getImagesByClient(String clientId);

}
