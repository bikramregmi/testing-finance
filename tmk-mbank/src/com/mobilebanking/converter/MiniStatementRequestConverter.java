package com.mobilebanking.converter;
import com.mobilebanking.entity.MiniStatementRequest;
import com.mobilebanking.model.mobile.MiniStatementRequestDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
@Component
public class MiniStatementRequestConverter implements IConverter<MiniStatementRequest, MiniStatementRequestDTO>, IListConverter<MiniStatementRequest, MiniStatementRequestDTO> {

    @Override
    public MiniStatementRequest convertToEntity(MiniStatementRequestDTO dto) {
        return null;
    }

    @Override
    public MiniStatementRequestDTO convertToDto(MiniStatementRequest entity) {
        MiniStatementRequestDTO miniStatementRequestDTO = new  MiniStatementRequestDTO();
        miniStatementRequestDTO.setAccountNumber(entity.getCustomerBankAccount().getAccountNumber());
        miniStatementRequestDTO.setStartDate(entity.getStartDate());
        miniStatementRequestDTO.setEndDate(entity.getEndDate());
        miniStatementRequestDTO.setEmail(entity.getEmail());
        miniStatementRequestDTO.setCustomerName(entity.getCustomerBankAccount().getCustomer().getFirstName()+" "+entity.getCustomerBankAccount().getCustomer().getMiddleName()+" "+entity.getCustomerBankAccount().getCustomer().getLastName());
        miniStatementRequestDTO.setMobileNumber(entity.getCustomerBankAccount().getCustomer().getMobileNumber());
        return miniStatementRequestDTO;
    }
    @Override
    public List<MiniStatementRequestDTO> convertToDtoList(List<MiniStatementRequest> entityList) {
        List<MiniStatementRequestDTO> RequestList = new ArrayList<>();
        for(MiniStatementRequest miniStatementRequest :entityList ){
            MiniStatementRequestDTO miniStatementRequestDTO = convertToDto(miniStatementRequest);
            RequestList.add(miniStatementRequestDTO);
        }
        return RequestList;    }
}
