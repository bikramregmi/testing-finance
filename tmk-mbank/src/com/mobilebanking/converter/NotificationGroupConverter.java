package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Customer;
import com.mobilebanking.entity.NotificationGroup;
import com.mobilebanking.model.NotificationGroupDTO;
import com.mobilebanking.repositories.BankRepository;

@Component
public class NotificationGroupConverter implements IConverter<NotificationGroup, NotificationGroupDTO>, IListConverter<NotificationGroup, NotificationGroupDTO>{

	@Autowired
	private BankRepository bankRepository;
	
	@Override
	public List<NotificationGroupDTO> convertToDtoList(List<NotificationGroup> entityList) {
		List<NotificationGroupDTO> dtoList = new ArrayList<>();
		for(NotificationGroup entity : entityList){
			dtoList.add(convertToDto(entity));
		}
		return dtoList;
	}

	@Override
	public NotificationGroup convertToEntity(NotificationGroupDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NotificationGroupDTO convertToDto(NotificationGroup entity) {
		NotificationGroupDTO dto = new NotificationGroupDTO();
		dto.setName(entity.getName());
		List<Long> customerList = new ArrayList<>();
		int customerCount = 0;
		for(Customer customer : entity.getCustomerList()){
			customerList.add(customer.getId());
			customerCount++;
		}
		dto.setCustomerCount(customerCount);
		dto.setCustomerList(customerList);
		dto.setBankName(bankRepository.findBankByCode(entity.getBankCode()).getName());
		dto.setBankCode(entity.getBankCode());
		return dto;
	}

}
