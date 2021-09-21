package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.HomeScreenImage;
import com.mobilebanking.model.HomeScreenImageDTO;

@Component
public class HomeScreenImageConverter implements IConverter<HomeScreenImage, HomeScreenImageDTO>,
		IListConverter<HomeScreenImage, HomeScreenImageDTO> {

	@Override
	public HomeScreenImage convertToEntity(HomeScreenImageDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HomeScreenImageDTO convertToDto(HomeScreenImage entity) {
		HomeScreenImageDTO dto = new HomeScreenImageDTO();
		dto.setBankId(entity.getBank().getId());
		dto.setId(entity.getId());
		dto.setPlacement(entity.getPlacement());
		dto.setStatus(entity.getStatus());
		dto.setImage(entity.getBank().getSwiftCode() + "/" + entity.getDocument().getIdentifier() + "."
				+ entity.getDocument().getExtention());
		return dto;
	}

	@Override
	public List<HomeScreenImageDTO> convertToDtoList(List<HomeScreenImage> entityList) {
		List<HomeScreenImageDTO> dtoList = new ArrayList<>();
		for (HomeScreenImage entity : entityList) {
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	public List<String> converForApi(List<HomeScreenImage> homeScreenImageList) {
		List<String> imagesList = new ArrayList<>();
		for(HomeScreenImage entity : homeScreenImageList){
			imagesList.add("/homescreen/images/"+entity.getBank().getSwiftCode() + "/" + entity.getDocument().getIdentifier() + "."
				+ entity.getDocument().getExtention());
		}
		return imagesList;
	}

}
