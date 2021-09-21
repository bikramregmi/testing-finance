package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobilebanking.entity.BulkSmsAlertBatch;
import com.mobilebanking.entity.SmsAlert;
import com.mobilebanking.model.BulkSmsAlertDTO;
import com.mobilebanking.model.SmsAlertDTO;
import com.mobilebanking.repositories.SmsAlertRepository;

@Component
public class SmsAlertConverter implements IConverter<SmsAlert, SmsAlertDTO>, IListConverter<SmsAlert, SmsAlertDTO> {

	@Autowired
	private SmsAlertRepository smsAlertRepository;

	@Override
	public List<SmsAlertDTO> convertToDtoList(List<SmsAlert> entityList) {
		List<SmsAlertDTO> dtoList = new ArrayList<>();
		for (SmsAlert entity : entityList)
			dtoList.add(convertToDto(entity));
		return dtoList;
	}

	@Override
	public SmsAlert convertToEntity(SmsAlertDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsAlertDTO convertToDto(SmsAlert entity) {
		SmsAlertDTO dto = new SmsAlertDTO();
		dto.setBulkBatch(entity.getBulkSmsAlertBatch().getBatchId());
		dto.setBulkBatchId(entity.getBulkSmsAlertBatch().getId());
		dto.setMobileNumber(entity.getForMobileNumber());
		dto.setMessage(entity.getMessage());
		if (entity.getDeliveredDate() != null)
			dto.setDeliveredDate(entity.getDeliveredDate().toString().substring(0, 16));
		else
			dto.setDeliveredDate("N/A");
		dto.setCreatedDate(entity.getCreated().toString().substring(0, 16));
		dto.setSmsStatus(entity.getSmsStatus());
		return dto;
	}

	public List<BulkSmsAlertDTO> convertBulkBatch(List<BulkSmsAlertBatch> entityList) {
		List<BulkSmsAlertDTO> dtoList = new ArrayList<>();
		for (BulkSmsAlertBatch entity : entityList) {
			dtoList.add(convertBulkBatch(entity));
		}
		return dtoList;
	}

	public BulkSmsAlertDTO convertBulkBatch(BulkSmsAlertBatch entity) {
		BulkSmsAlertDTO bulkSmsAlertDTO = new BulkSmsAlertDTO();
		bulkSmsAlertDTO.setId(entity.getId());
		bulkSmsAlertDTO.setBatchId(entity.getBatchId());
		bulkSmsAlertDTO.setCreatedBy(entity.getCreatedBy().getId());
		bulkSmsAlertDTO.setCreatedDate(entity.getCreated().toString().substring(0, 16));
		bulkSmsAlertDTO.setSmsCount(smsAlertRepository.countByBulkSmsAlertBatch(entity));
		return bulkSmsAlertDTO;
	}

}
