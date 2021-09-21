/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CustomerLog;
import com.mobilebanking.model.CustomerLogDto;
import com.mobilebanking.util.DateUtil;

/**
 * @author bibek
 *
 */
@Component
public class CustomerLogConverter
		implements IListConverter<CustomerLog, CustomerLogDto>, IConverter<CustomerLog, CustomerLogDto> {

	@Override
	public CustomerLog convertToEntity(CustomerLogDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerLogDto convertToDto(CustomerLog entity) {
		CustomerLogDto customerLogDto = new CustomerLogDto();
		customerLogDto.setId(entity.getId());
		if (entity.getCustomer() != null) {
			if(entity.getCustomer().getMiddleName() != null){
				customerLogDto.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getMiddleName()+ " " + entity.getCustomer().getLastName());
			}else{
				customerLogDto.setCustomerName(entity.getCustomer().getFirstName() + " " + entity.getCustomer().getLastName());
			}
		}
		if (entity.getLastLoggedIn() != null) {
			customerLogDto.setLastLoggedIn(entity.getLastLoggedIn().toString());
		}
		customerLogDto.setChangedBy(entity.getChangedBy());
		customerLogDto.setDate(DateUtil.convertDateToString(entity.getCreated()));
		customerLogDto.setRemarks(entity.getRemarks());
		return customerLogDto;
	}

	@Override
	public List<CustomerLogDto> convertToDtoList(List<CustomerLog> entityList) {
		List<CustomerLogDto> customerLogDtoList = new ArrayList<CustomerLogDto>();
		for (CustomerLog cLog : entityList) {
			customerLogDtoList.add(convertToDto(cLog));
		}
		return customerLogDtoList;
	}

}
