/**
 * 
 */
package com.mobilebanking.api;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.mobilebanking.model.DocumentIdsDTO;
import com.mobilebanking.model.Status;

/**
 * @author bibek
 *
 */
public interface IDocumentIdsApi {
	
	DocumentIdsDTO save(DocumentIdsDTO documentIdDto) throws JSONException, IOException;
	
	List<DocumentIdsDTO> getAllDocumentIds();
	
	List<DocumentIdsDTO> getDocumentIdsByStatus(Status status);
	
	DocumentIdsDTO edit(DocumentIdsDTO documentIdsDto) throws JSONException, IOException;
	
	DocumentIdsDTO getDocumentById(long id);
}
