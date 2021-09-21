/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.BankBranch;
import com.mobilebanking.model.BankBranchDTO;

/**
 * @author bibek
 *
 */
@Component
public class BankBranchConverter
		implements IListConverter<BankBranch, BankBranchDTO>, IConverter<BankBranch, BankBranchDTO> {

	@Override
	public BankBranch convertToEntity(BankBranchDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankBranchDTO convertToDto(BankBranch entity) {
		BankBranchDTO bankBranchDto = new BankBranchDTO();
		bankBranchDto.setId(entity.getId());
		bankBranchDto.setName(entity.getName());
		bankBranchDto.setAddress(entity.getAddress());
		bankBranchDto.setBank(entity.getBank().getName());
		bankBranchDto.setChecker(entity.isChecker());
		bankBranchDto.setMaker(entity.isMaker());
		bankBranchDto.setState(entity.getCity().getState().getName());
		bankBranchDto.setEmail(entity.getEmail());
		bankBranchDto.setMobileNumber(entity.getMobileNumber());
		bankBranchDto.setBranchCode(entity.getBranchCode());
		bankBranchDto.setCity(entity.getCity().getName());
		return bankBranchDto;
	}

	@Override
	public List<BankBranchDTO> convertToDtoList(List<BankBranch> entityList) {
		List<BankBranchDTO> bankBranchList = new ArrayList<BankBranchDTO>();
		for (BankBranch bb : entityList) {
			bankBranchList.add(convertToDto(bb));
		}
		return bankBranchList;
	}

}
