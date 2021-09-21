package com.mobilebanking.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.Ledger;
import com.mobilebanking.model.LedgerDTO;
@Component
public class LedgerConverter implements IConverter<Ledger, LedgerDTO>, IListConverter<Ledger, LedgerDTO> {

	@Override
	public List<LedgerDTO> convertToDtoList(List<Ledger> entityList) {

		List<LedgerDTO> dtoList = new ArrayList<LedgerDTO>();
		for (Ledger ledger : entityList) {
			dtoList.add(convertToDto(ledger));
		}
		return dtoList;

	}

	@Override
	public Ledger convertToEntity(LedgerDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LedgerDTO convertToDto(Ledger entity) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LedgerDTO dto = new LedgerDTO();
		try {
			if (entity != null) {

				dto.setAmount(entity.getAmount());
				dto.setFromBalance(entity.getFromBalance());
				dto.setRemarks(entity.getRemarks());
				dto.setToBalance(entity.getToBalance());
				//dto.setTransactionId(Long.toString(entity.getTransactionId()));
				dto.setTransactionId((entity.getTransactionId()));
				dto.setTransactionStatus(entity.getStatus());
				dto.setTransactionType(entity.getType());
				if (entity.getExternalRefId() > 0) {
					dto.setExternalRefId(entity.getExternalRefId());
				}
				if (entity.getFromAccount() != null) {
					dto.setFromAccount(entity.getFromAccount().getAccountNumber());
				}
				if (entity.getToAccount() != null) {
					dto.setToAccount(entity.getToAccount().getAccountNumber());
				}
				dto.setDate(dateFormatter.format(entity.getCreated()));
				dto.setParentId(entity.getParentId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;

	}

}
