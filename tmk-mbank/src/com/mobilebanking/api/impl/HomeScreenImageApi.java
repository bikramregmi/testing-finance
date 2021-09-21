package com.mobilebanking.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IDocumentsApi;
import com.mobilebanking.api.IHomeScreenImageApi;
import com.mobilebanking.converter.HomeScreenImageConverter;
import com.mobilebanking.entity.Bank;
import com.mobilebanking.entity.BankOAuthClient;
import com.mobilebanking.entity.Document;
import com.mobilebanking.entity.HomeScreenImage;
import com.mobilebanking.model.HomeScreenImageDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.BankOAuthClientRepository;
import com.mobilebanking.repositories.BankRepository;
import com.mobilebanking.repositories.HomeScreenImageRepository;
import com.mobilebanking.util.FileHandler;

@Service
public class HomeScreenImageApi implements IHomeScreenImageApi {

	@Autowired
	private HomeScreenImageRepository homeScreenImageRepository;

	@Autowired
	private HomeScreenImageConverter homeScreenImageConverter;

	@Autowired
	private IDocumentsApi documentApi;

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private BankOAuthClientRepository bankAuthRepository;

	@Override
	public List<HomeScreenImageDTO> findByBank(long bankId) {
		List<HomeScreenImage> homeScreenImages = homeScreenImageRepository.findByBank(bankId);
		if (homeScreenImages != null) {
			return homeScreenImageConverter.convertToDtoList(homeScreenImages);
		}
		return null;
	}

	@Override
	public void savaNewHomeScreenImage(HomeScreenImageDTO homeScreenImageDTO) {
		Bank bank = bankRepository.findOne(homeScreenImageDTO.getBankId());
		Document document = documentApi.saveDocuments(homeScreenImageDTO.getFile().getOriginalFilename());
		fileHandler.homeScreenImageHandler(homeScreenImageDTO.getFile(), document, bank.getSwiftCode());
		HomeScreenImage homeScreenImage = new HomeScreenImage();
		homeScreenImage.setBank(bank);
		homeScreenImage.setStatus(Status.Active);
		homeScreenImage.setPlacement(homeScreenImageRepository.countByBank(bank).intValue());
		homeScreenImage.setDocument(document);
		homeScreenImageRepository.save(homeScreenImage);
	}

	@Override
	public void updatePlacement(List<HomeScreenImageDTO> homeScreenImageList) {
		int placement = 0;
		for(HomeScreenImageDTO homeScreenImage : homeScreenImageList){
			HomeScreenImage entity = homeScreenImageRepository.findOne(homeScreenImage.getId());
			entity.setPlacement(placement);
			homeScreenImageRepository.save(entity);
			placement++;
		}
		
	}

	@Override
	public void delete(Long id) {
		HomeScreenImage entity = homeScreenImageRepository.findOne(id);
		entity.setStatus(Status.Deleted);
		homeScreenImageRepository.save(entity);
		
		updatePlacement(findByBank(entity.getBank().getId()));
	}

	@Override
	public List<String> getImagesByClient(String clientId) {
		BankOAuthClient bankAouthClient = bankAuthRepository.findByOAuthClientId(clientId);
		Bank bank = bankAouthClient.getBank();
		List<HomeScreenImage> imagesList = homeScreenImageRepository.findByBank(bank.getId());
		if(imagesList != null){
			return homeScreenImageConverter.converForApi(imagesList);
		}
		return null;
	}

}
