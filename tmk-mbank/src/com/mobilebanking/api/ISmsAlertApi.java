package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.model.BulkSmsAlertDTO;
import com.mobilebanking.model.SmsAlertDTO;

public interface ISmsAlertApi {

	Long saveBulkBatch(BulkSmsAlertDTO bulkSmsAlertDTO);

	void saveSmsAlert(SmsAlertDTO smsAlertdto);

	List<BulkSmsAlertDTO> getBatchList(Long userId);

	List<SmsAlertDTO> getSmsAlertByBatch(long batchId);

}
