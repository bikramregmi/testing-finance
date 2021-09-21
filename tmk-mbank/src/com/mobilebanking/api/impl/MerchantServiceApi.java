/**
 * 
 */
package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IDocumentsApi;
import com.mobilebanking.api.IMerchantServiceApi;
import com.mobilebanking.entity.Account;
import com.mobilebanking.entity.Commission;
import com.mobilebanking.entity.Document;
import com.mobilebanking.entity.MerchantManager;
import com.mobilebanking.entity.MerchantService;
import com.mobilebanking.model.AccountType;
import com.mobilebanking.model.MerchantServiceDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.AccountRepository;
import com.mobilebanking.repositories.CommissionRepository;
import com.mobilebanking.repositories.MerchantManagerRepository;
import com.mobilebanking.repositories.MerchantRepository;
import com.mobilebanking.repositories.MerchantServiceRepository;
import com.mobilebanking.repositories.ServiceCategoryRepository;
import com.mobilebanking.util.AccountUtil;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.FileHandler;

/**
 * @author bibek
 *
 */
@Service
public class MerchantServiceApi implements IMerchantServiceApi {
	
	@Autowired
	MerchantServiceRepository merchantServiceRepository;
	
	@Autowired
	MerchantRepository merchantRepository;
	
	@Autowired
	private MerchantManagerRepository merchantManagerRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private IDocumentsApi documentApi;
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired 
	private ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	private CommissionRepository commissionRepository;
	
	@Autowired
	private AccountUtil accountUtil;
	
	@Override
	public MerchantServiceDTO saveMerchantService(MerchantServiceDTO merchantServiceDto) throws JSONException, ClassCastException {
		MerchantService merchantService = new MerchantService();
		merchantService.setServices(merchantServiceDto.getService());
		merchantService.setUniqueIdentifier(merchantServiceDto.getUniqueIdentifier());
		merchantService.setUrl("generalMerchantPayment");
		merchantService.setStatus(Status.Active);
		merchantService.setLabelName(merchantServiceDto.getLabelName());
		merchantService.setLabelPrefix(merchantServiceDto.getLabelPrefix());
		merchantService.setLabelSample(merchantServiceDto.getLabelSample());
		merchantService.setInstructions(merchantServiceDto.getInstructions());
		merchantService.setPriceInput(merchantServiceDto.isPriceInput());
		merchantService.setNotificationUrl(merchantServiceDto.getNotificationUrl());
		merchantService.setMinValue(merchantServiceDto.getMinValue());
		merchantService.setMaximumValue(merchantServiceDto.getMaxValue());
		merchantService.setServiceCatagory(serviceCategoryRepository.findOne(merchantServiceDto.getCategoryId()));
		if(merchantServiceDto.isPriceInput()){
			merchantService.setPriceRange(merchantServiceDto.getPriceRange());
		}
		merchantService.setFixedlabelSize(merchantServiceDto.isFixedlabelSize());
		if(merchantServiceDto.isFixedlabelSize()){
			merchantService.setLabelFizedSize(merchantServiceDto.getLabelSize());
		}else{
			merchantService.setLabelMaxLength(merchantServiceDto.getLabelMaxLength());
			merchantService.setLabelMinLength(merchantServiceDto.getLabelMinLength());
		}
		merchantService.setWebView(merchantServiceDto.getWebView());
		merchantService = merchantServiceRepository.save(merchantService);
		
		Account account = createAccount(merchantService);
		merchantService.setAccountNumber(account.getAccountNumber());
		
		Document document = documentApi.saveDocuments(fileHandler.multipartSingleNameResolver(merchantServiceDto.getFile()));
		fileHandler.documentHandler(merchantServiceDto.getFile(), document);
		merchantService.setDocuments(document);
		merchantService = merchantServiceRepository.save(merchantService);
		return ConvertUtil.convertMerchantService(merchantService);
	}

	private Account createAccount(MerchantService merchantService) {

		Account account = new Account();
		account.setAccountHead(merchantService.getService());
		account.setAccountNumber(accountUtil.generateAccountNumber());
		account.setAccountType(AccountType.MERCHANT_SERVICE);
		account.setBalance(0.0);
		//account.setUser(user);
		account.setMerchantService(merchantService);
		return accountRepository.save(account);
		
	}

	@Override
	public MerchantServiceDTO editMerchantService(MerchantServiceDTO merchantServiceDto) {
		MerchantService merchantService = merchantServiceRepository.findOne(merchantServiceDto.getId());
		merchantService.setServices(merchantServiceDto.getService());
		merchantService.setStatus(merchantServiceDto.getStatus());
		merchantService.setLabelName(merchantServiceDto.getLabelName());
		merchantService.setLabelPrefix(merchantServiceDto.getLabelPrefix());
		merchantService.setLabelSample(merchantServiceDto.getLabelSample());
		merchantService.setInstructions(merchantServiceDto.getInstructions());
		merchantService.setPriceInput(merchantServiceDto.isPriceInput());
		merchantService.setMinValue(merchantServiceDto.getMinValue());
		merchantService.setMaximumValue(merchantServiceDto.getMaxValue());
		if(merchantServiceDto.isPriceInput()){
			merchantService.setPriceRange(merchantServiceDto.getPriceRange());
		}else{
			merchantService.setPriceRange(null);
		}
		merchantService.setFixedlabelSize(merchantServiceDto.isFixedlabelSize());
		if(merchantServiceDto.isFixedlabelSize()){
			merchantService.setLabelFizedSize(merchantServiceDto.getLabelSize());
			merchantService.setLabelMaxLength(null);
			merchantService.setLabelMinLength(null);
		}else{
			merchantService.setLabelMaxLength(merchantServiceDto.getLabelMaxLength());
			merchantService.setLabelMinLength(merchantServiceDto.getLabelMinLength());
			merchantService.setLabelFizedSize(null);
		}
		merchantService.setWebView(merchantServiceDto.getWebView());
		merchantService = merchantServiceRepository.save(merchantService);
		
		return ConvertUtil.convertMerchantService(merchantService);
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.IMerchantServiceApi#findMerchantServiceById(long)
	 */
	@Override
	public MerchantServiceDTO findMerchantServiceById(long merchantServiceId) {
		
		MerchantService merchantService = merchantServiceRepository.findOne(merchantServiceId);
		if(merchantService!=null){
		return ConvertUtil.convertMerchantService(merchantService);
		}else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.mobilebanking.api.IMerchantServiceApi#findMerchantServicesByMerchant(long)
	 */

	@Override
	public List<MerchantServiceDTO> findAllMerchantService() {
		List<MerchantService> merchantServiceList = merchantServiceRepository.findAllMerchantServices();
		if (! merchantServiceList.isEmpty()) {
			return ConvertUtil.convertMerchantServiceToDto(merchantServiceList);
		}
		return null;
	}

	@Override
	public List<MerchantServiceDTO> findMerchantServiceByStatus(Status status) {
		List<MerchantService> merchantServiceList = merchantServiceRepository.findMerchantServiceByStatus(status);
		if (! merchantServiceList.isEmpty()) {
			return ConvertUtil.convertMerchantServiceToDto(merchantServiceList);
		}
		return null;
	}

	@Override
	public MerchantServiceDTO findServiceByUniqueIdentifier(String uniqueId) {
		MerchantService merchantService = merchantServiceRepository.findMerchantServiceByIdentifier(uniqueId);
		if(merchantService != null){
			return ConvertUtil.convertMerchantService(merchantService);
		}
		return null;
	}

	@Override
	public List<MerchantServiceDTO> findNotIncludedMerchantServiceByMerchantIdAndStatus(long merchantId, Status status) {
		return null;
		//		List<MerchantService> merchantServiceList = merchantServiceRepository.findNotIncludedMerchantServiceByMerchantIdAndStatus(merchantId, status);
//		return ConvertUtil.convertMerchantServiceToDto(merchantServiceList);
	}

	@Override
	public List<MerchantServiceDTO> findMerchantServiceByMerchantAndStatus(long merchantId, Status active) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MerchantServiceDTO> getAllMerchantServiceExceptIncluded(long merchantId) {
		List<MerchantService> merchantService = merchantServiceRepository.getAllMerchantServicesExceptIncluded(merchantId);
		return ConvertUtil.convertMerchantServiceToDto(merchantService);

	}

	@Override
	public List<MerchantServiceDTO> getAllMerchantServicesByMerchant(long merchantId) {
		List<MerchantManager> merchantManagers = merchantManagerRepository.findMerchantManagerByMerchant(merchantId);
		List<MerchantService> serviceList = new ArrayList<>();
		for(MerchantManager merchantManager:merchantManagers){
			MerchantService MerchantService = new MerchantService();
			MerchantService=merchantManager.getMerchantsAndServices().getMerchantService();
			serviceList.add(MerchantService);
		}
		return ConvertUtil.convertMerchantServiceToDto(serviceList);

	}

	@Override
	public List<MerchantServiceDTO> getMerchantServicesWithoutCommissionByMerchant(long merchantId) {
		List<MerchantManager> merchantManagers = merchantManagerRepository.findMerchantManagerByMerchant(merchantId);
		List<MerchantService> serviceList = new ArrayList<>();
		List<Commission> commissionList = commissionRepository.findbyMerchant(merchantId);
		if(merchantManagers!=null){
			for(MerchantManager merchantManager:merchantManagers){
				boolean valid = true;
				for(Commission commission : commissionList){
					if((commission.getService().equals(merchantManager.getMerchantsAndServices().getMerchantService()))){
						valid = false;
					}
				}
				if(valid){
					MerchantService MerchantService = new MerchantService();
					MerchantService=merchantManager.getMerchantsAndServices().getMerchantService();
					serviceList.add(MerchantService);
				}
			}
			return ConvertUtil.convertMerchantServiceToDto(serviceList);
		}
		return null;
	}
	

}
