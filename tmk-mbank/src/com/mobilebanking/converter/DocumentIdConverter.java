/**
 * 
 */
package com.mobilebanking.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mobilebanking.entity.DocumentIds;
import com.mobilebanking.model.DocumentIdsDTO;

/**
 * @author bibek
 *
 */
@Component
public class DocumentIdConverter implements IConverter<DocumentIds, DocumentIdsDTO>, IListConverter<DocumentIds, DocumentIdsDTO> {

	@Override
	public List<DocumentIdsDTO> convertToDtoList(List<DocumentIds> entityList) {
		List<DocumentIdsDTO> documentDtoList = new ArrayList<DocumentIdsDTO>();
		for (DocumentIds dI : entityList) {
			documentDtoList.add(convertToDto(dI));
		}
		return documentDtoList;
	}
	
	@Override
	public DocumentIds convertToEntity(DocumentIdsDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentIdsDTO convertToDto(DocumentIds entity) {
		DocumentIdsDTO documentDto = new DocumentIdsDTO();
		documentDto.setId(entity.getId());
		documentDto.setIssuedDateRequired(entity.isIssuedDateRequired());
		documentDto.setExpiryDateRequired(entity.isExpiryDateRequired());
		documentDto.setIssuedCityRequired(entity.isIssuedCityRequired());
		documentDto.setIssuedStateRequired(entity.isIssuedStateRequired());
		documentDto.setDocumentType(entity.getDocumentType());
		documentDto.setStatus(entity.getStatus());
		return documentDto;
	}

}
