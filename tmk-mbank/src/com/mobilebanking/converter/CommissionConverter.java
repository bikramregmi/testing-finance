/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Commission;
import com.mobilebanking.model.CommissionDTO;

/**
 * @author bibek
 *
 */
@Component
public class CommissionConverter
		implements IListConverter<Commission, CommissionDTO>, IConverter<Commission, CommissionDTO> {

	@Override
	public Commission convertToEntity(CommissionDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommissionDTO convertToDto(Commission entity) {
		CommissionDTO commissionDto = new CommissionDTO();
		commissionDto.setId(entity.getId());
		commissionDto.setMerchant(entity.getMerchant().getName());
		commissionDto.setService(entity.getService().getService());
		commissionDto.setSameForAll(entity.isSameForAll());
		commissionDto.setStatus(entity.getStatus());
		commissionDto.setCommissionType(entity.getCommissionType());
		commissionDto.setSameForAll(entity.isSameForAll());
		return commissionDto;
	}

	@Override
	public List<CommissionDTO> convertToDtoList(List<Commission> entityList) {
		List<CommissionDTO> commissionList = new ArrayList<CommissionDTO>();
		for (Commission commission : entityList) {
			commissionList.add(convertToDto(commission));
		}
		return commissionList;
	}
}
