package com.mobilebanking.api;

import java.util.List;

import com.mobilebanking.entity.State;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.StateDTO;

public interface IStateApi {

	void saveState(StateDTO dto);
	
	List<StateDTO> findState();

	List<StateDTO> getAllState();

	State getStateById(long stateId);

	void editState(StateDTO stateDTO);
	
	List<StateDTO> getStatesByCountryIso(String iso);

	void saveBulkState(StateDTO state);

	

	PagablePage getAllState(Integer currentpage, String searchWord);

}
