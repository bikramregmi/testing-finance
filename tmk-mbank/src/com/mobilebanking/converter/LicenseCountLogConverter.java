package com.mobilebanking.converter;

import com.mobilebanking.controller.LicenseCountLogController;
import com.mobilebanking.entity.LicenseCountLog;
import com.mobilebanking.model.LicenseCountLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LicenseCountLogConverter implements IConverter<LicenseCountLog, LicenseCountLogDTO>, IListConverter<LicenseCountLog, LicenseCountLogDTO> {

    @Override
    public List<LicenseCountLogDTO> convertToDtoList(List<LicenseCountLog> requestList) {
        System.out.println("1"+requestList.toString());
        List<LicenseCountLogDTO> RequestList = new ArrayList<>();
        for(LicenseCountLog licenseCountLog :requestList ){
            LicenseCountLogDTO licenseCountLogDTO = convertToDto(licenseCountLog);
            RequestList.add(licenseCountLogDTO);
        }
        return RequestList;
    }

    @Override
    public LicenseCountLog convertToEntity(LicenseCountLogDTO dto) {
        return null;
    }

    @Override
    public LicenseCountLogDTO convertToDto(LicenseCountLog entity) {
        LicenseCountLogDTO licenseCountLogDTO = new  LicenseCountLogDTO();
        licenseCountLogDTO.setBankId(entity.getBank().getId());
        licenseCountLogDTO.setUserId(entity.getUser().getId());
        licenseCountLogDTO.setLicenseCount(entity.getLicenseCount());
        licenseCountLogDTO.setRemarks(entity.getRemarks());
        licenseCountLogDTO.setName(entity.getBank().getName());
        licenseCountLogDTO.setUserName(entity.getUser().getUserName());
        return licenseCountLogDTO;
    }
}