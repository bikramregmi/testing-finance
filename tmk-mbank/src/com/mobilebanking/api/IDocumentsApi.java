package com.mobilebanking.api;

import com.mobilebanking.entity.Document;

public interface IDocumentsApi {

	Document saveDocuments(String filename);
	
}
