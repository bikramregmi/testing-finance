/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.CommissionAmount;
import com.mobilebanking.model.CommissionAmountDTO;

/**
 * @author bibek
 *
 */
@Component
public class CommissionAmountConverter implements IConverter<CommissionAmount, CommissionAmountDTO>,
		IListConverter<CommissionAmount, CommissionAmountDTO> {

	@Override
	public List<CommissionAmountDTO> convertToDtoList(List<CommissionAmount> entityList) {
		List<CommissionAmountDTO> commissionAmounts = new ArrayList<CommissionAmountDTO>();
		for (CommissionAmount ca : entityList) {
			commissionAmounts.add(convertToDto(ca));
		}
		return commissionAmounts;
	}

	@Override
	public CommissionAmount convertToEntity(CommissionAmountDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommissionAmountDTO convertToDto(CommissionAmount entity) {
		CommissionAmountDTO commissionAmountDto = new CommissionAmountDTO();
		commissionAmountDto.setId(entity.getId());
		commissionAmountDto.setCommissionFlat(entity.getCommissionFlat());
		commissionAmountDto.setCommissionPercentage(entity.getCommissionPercentage());
		commissionAmountDto.setFromAmount(entity.getFromAmount());
		commissionAmountDto.setToAmount(entity.getToAmount());
		commissionAmountDto.setFeeFlat(entity.getFeeFlat());
		commissionAmountDto.setFeePercentage(entity.getFeePercentage());
		commissionAmountDto.setStatus(entity.getStatus());
		commissionAmountDto.setCommissionId(entity.getCommission().getId());
		commissionAmountDto.setMerchantId(entity.getCommission().getMerchant().getId());
		commissionAmountDto.setMerchantServiceId(entity.getCommission().getService().getId());
		return commissionAmountDto;
	}

}
