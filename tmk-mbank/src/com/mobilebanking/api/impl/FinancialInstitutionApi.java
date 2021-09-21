package com.mobilebanking.api.impl;
/*package com.wallet.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.api.IAccountApi;
import com.wallet.api.ICityApi;
import com.wallet.api.IFinancialInstitutionApi;
import com.wallet.entity.City;
import com.wallet.entity.FinancialInstitution;
import com.wallet.model.AccountDTO;
import com.wallet.model.FinancialInstitutionDTO;
import com.wallet.model.FinancialInstitutionType;
import com.wallet.repositories.FinancialInstitutionRepository;
import com.wallet.util.AuthenticationUtil;
import com.wallet.util.ClientException;
import com.wallet.util.ConvertUtil;

@Service
public class FinancialInstitutionApi implements IFinancialInstitutionApi {
	
	@Autowired
	private FinancialInstitutionRepository financialInstitutionRepository;
	
	@Autowired
	private ICityApi cityApi;
	
	@Autowired
	private IAccountApi accountApi;

	@Override
	public void saveFinancialInstitute(FinancialInstitutionDTO financialInstitutionDTO) {
		
		
		
		AccountDTO accountDTO = new AccountDTO();
		
		try {
			accountDTO=	accountApi.createAccount(10000.00, AuthenticationUtil.getCurrentUser().getUsername());
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		FinancialInstitution financialInstitution = new FinancialInstitution();
		
		financialInstitution.setName(financialInstitutionDTO.getName());
		//financialInstitution.setAccount(financialInstitutionDTO.getAccount());
		financialInstitution.setAddress(financialInstitutionDTO.getAddress());
		financialInstitution.setAccountNo(accountDTO.getAccountNo());
		financialInstitution.setCity(cityApi.findByCityName(financialInstitutionDTO.getCity()));
		financialInstitution.setSwiftCode(financialInstitutionDTO.getSwiftCode());
		financialInstitution.setFinancialInstitutionType(FinancialInstitutionType.valueOf(financialInstitutionDTO.getInstituteType()));
		FinancialInstitution institute = financialInstitutionRepository.save(financialInstitution);
		
	}

	@Override
	public List<FinancialInstitutionDTO> findAllInstitute() {
		
		List<FinancialInstitution> financialInstitutes = (List<FinancialInstitution>) financialInstitutionRepository.findAll();
		List<FinancialInstitutionDTO> institutionDTOs = new ArrayList<FinancialInstitutionDTO>();
		for(FinancialInstitution institution : financialInstitutes){
			FinancialInstitutionDTO dto = ConvertUtil.convertFinanceInstituteToFinanceIntitute(institution);
			institutionDTOs.add(dto);
		}
		return institutionDTOs;
	}

	@Override
	public void deleteFinancialInstituteById(Long id){
		financialInstitutionRepository.delete(id);
	}

	@Override
	public FinancialInstitutionDTO findFinancialInstituteById(Long id) {
		FinancialInstitution institution = financialInstitutionRepository.findOne(id);
		FinancialInstitutionDTO institutionDTO = ConvertUtil.convertFinanceInstituteToFinanceIntitute(institution);
		return institutionDTO;
	}

	@Override
	public FinancialInstitution editFinancialInstitute(FinancialInstitutionDTO instituteDTO) {
		
		FinancialInstitution fInstitute = financialInstitutionRepository.findOne(instituteDTO.getId());
		City city = cityApi.findByCityName(instituteDTO.getCity());
		fInstitute.setCity(city);
		fInstitute = ConvertUtil.convertInstituteDtoToInstitute(instituteDTO,fInstitute);
		fInstitute = financialInstitutionRepository.save(fInstitute);
		return fInstitute;
	}

}
*/