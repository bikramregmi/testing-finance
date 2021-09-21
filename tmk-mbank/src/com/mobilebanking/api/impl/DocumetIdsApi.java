/**
 * 
 */
package com.mobilebanking.api.impl;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IDocumentIdsApi;
import com.mobilebanking.converter.DocumentIdConverter;
import com.mobilebanking.entity.DocumentIds;
import com.mobilebanking.model.DocumentIdsDTO;
import com.mobilebanking.model.Status;
import com.mobilebanking.repositories.DocumentIdsRepository;

/**
 * @author bibek
 *
 */
@Service
public class DocumetIdsApi implements IDocumentIdsApi {
	
	@Autowired
	private DocumentIdsRepository documentIdsRepository;
	
	@Autowired
	private DocumentIdConverter documentConverter;
	
	@Override
	public DocumentIdsDTO save(DocumentIdsDTO documentIdDto) throws JSONException, IOException {
		
		DocumentIds documentId = new DocumentIds();
		documentId.setDocumentType(documentIdDto.getDocumentType());
		documentId.setExpiryDateRequired(documentIdDto.isExpiryDateRequired());
		documentId.setIssuedCityRequired(documentIdDto.isIssuedDateRequired());
		documentId.setIssuedCityRequired(documentIdDto.isIssuedCityRequired());
		documentId.setIssuedStateRequired(documentIdDto.isIssuedStateRequired());
		documentId.setStatus(Status.Active);
		
		documentId = documentIdsRepository.save(documentId);
		
		return documentConverter.convertToDto(documentId);
	}

	@Override
	public List<DocumentIdsDTO> getAllDocumentIds() {
		List<DocumentIds> documentIdsList = documentIdsRepository.findAllDocumentsIds();
		if (! documentIdsList.isEmpty()) {
			return documentConverter.convertToDtoList(documentIdsList);
		}
		return null;
	}

	@Override
	public List<DocumentIdsDTO> getDocumentIdsByStatus(Status status) {
		List<DocumentIds> documentIdsList = documentIdsRepository.findDocumentsIdsByStatus(status);
		System.out.println(documentIdsList + " THIS IS LIST DOCUMENTS");
		if (! documentIdsList.isEmpty()) {
			return documentConverter.convertToDtoList(documentIdsList);
		}
		return null;
	}

	@Override
	public DocumentIdsDTO edit(DocumentIdsDTO documentIdsDto) throws JSONException, IOException {
		DocumentIds documentIds = documentIdsRepository.findOne(documentIdsDto.getId());
		documentIds.setDocumentType(documentIdsDto.getDocumentType());
		documentIds.setDocumentType(documentIdsDto.getDocumentType());
		documentIds.setExpiryDateRequired(documentIdsDto.isExpiryDateRequired());
		documentIds.setIssuedCityRequired(documentIdsDto.isIssuedDateRequired());
		documentIds.setIssuedCityRequired(documentIdsDto.isIssuedCityRequired());
		documentIds.setIssuedStateRequired(documentIdsDto.isIssuedStateRequired());
		documentIds.setStatus(documentIdsDto.getStatus());
		documentIdsRepository.save(documentIds);
		return documentConverter.convertToDto(documentIds);
	}

	@Override
	public DocumentIdsDTO getDocumentById(long id) {
		DocumentIds documentIds = documentIdsRepository.findOne(id);
		if (documentIds != null) {
			return documentConverter.convertToDto(documentIds);
		}
		return null;
	}

}
