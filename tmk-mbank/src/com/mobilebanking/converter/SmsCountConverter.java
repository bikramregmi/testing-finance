package com.mobilebanking.converter;

import com.mobilebanking.entity.SmsCountLog;
import com.mobilebanking.model.SmsCountDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SmsCountConverter implements IConverter<SmsCountLog, SmsCountDTO>, IListConverter<SmsCountLog, SmsCountDTO> {
    @Override
    public SmsCountLog convertToEntity(SmsCountDTO dto) {
        return null;
    }

    @Override
    public SmsCountDTO convertToDto(SmsCountLog entity) {

        SmsCountDTO smsCountDTO = new  SmsCountDTO();
        smsCountDTO.setBankId(entity.getBank().getId());
        smsCountDTO.setUserId(entity.getUser().getId());
        smsCountDTO.setSmsCount(entity.getSmsCount());
        smsCountDTO.setRemarks(entity.getRemarks());
        smsCountDTO.setName(entity.getBank().getName());
        smsCountDTO.setUserName(entity.getUser().getUserName());

        return smsCountDTO;    }

    @Override
    public List<SmsCountDTO> convertToDtoList(List<SmsCountLog> requestList) {
        List<SmsCountDTO> RequestList = new ArrayList<>();
        for(SmsCountLog smsCountLog :requestList ){
            SmsCountDTO smsCountDTO = convertToDto(smsCountLog);
            RequestList.add(smsCountDTO);
        }
        return RequestList;
    }
}
