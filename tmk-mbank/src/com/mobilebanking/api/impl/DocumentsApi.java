package com.mobilebanking.api.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilebanking.api.IDocumentsApi;
import com.mobilebanking.entity.Document;
import com.mobilebanking.repositories.DocumentRepository;

@Service
public class DocumentsApi implements IDocumentsApi{

	@Autowired
	private DocumentRepository documentRepository;
	
	@Override
	public Document saveDocuments(String filename) {
		Document document= new Document();
		document.setExtention(FilenameUtils.getExtension(filename));
		document.setIdentifier(""+System.currentTimeMillis());
		documentRepository.save(document);
		return document;
	}

	
	
}
