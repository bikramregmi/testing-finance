/**
 * 
 */
package com.mobilebanking.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.SmsLog;
import com.mobilebanking.model.SmsLogDTO;

/**
 * @author bibek
 *
 */
@Component
public class SmsLogConverter implements IConverter<SmsLog, SmsLogDTO>, IListConverter<SmsLog, SmsLogConverter> {

	@Override
	public List<SmsLogConverter> convertToDtoList(List<SmsLog> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsLog convertToEntity(SmsLogDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SmsLogDTO convertToDto(SmsLog entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
