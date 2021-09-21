package com.mobilebanking.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilebanking.api.IStateApi;
import com.mobilebanking.controller.StateController;
import com.mobilebanking.entity.State;
import com.mobilebanking.model.PagablePage;
import com.mobilebanking.model.StateDTO;
import com.mobilebanking.repositories.StateRepository;
import com.mobilebanking.util.ConvertUtil;
import com.mobilebanking.util.PageInfo;

@Service
@Transactional
public class StateApi implements IStateApi {

	private Logger logger = LoggerFactory.getLogger(StateController.class);

	@Autowired
	private StateRepository stateRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void saveState(StateDTO stateDTO) {
		State state = ConvertUtil.convertDtoToState(stateDTO);
		stateRepository.save(state);
		logger.debug("State Saved");

	}

	@Override
	public List<StateDTO> findState() {
		List<State> stateList = new ArrayList<State>();
		stateList = stateRepository.findState();
		return ConvertUtil.convertStateDtoToState(stateList);
	}

	@Override
	public PagablePage getAllState(Integer currentpage, String searchWord) {

		PagablePage pageble = new PagablePage();

		if (currentpage == null || currentpage == 0) {
			currentpage = 1;
		}

		int starting = ((currentpage * (int) PageInfo.pageList) - (int) PageInfo.pageList);
		entityManager = entityManager.getEntityManagerFactory().createEntityManager();
		Session session = entityManager.unwrap(Session.class);

		String startQuery = "select s from State s";
		String endQuery = "";
		boolean valid = false;
		if (searchWord != null && !searchWord.trim().equals("")) {
			endQuery += " s.name like CONCAT(:name, '%')";
			valid = true;
		}

		if (valid) {
			startQuery += " where " + endQuery;
		}

		Query selectQuery = session.createQuery(startQuery);
		if (searchWord != null && !searchWord.trim().equals("")) {
			selectQuery.setParameter("name", searchWord);
		}

		int totalpage = (int) Math.ceil(selectQuery.list().size() / PageInfo.pageList);
		List<Integer> pagesnumbers = PageInfo.PageLimitCalculator(currentpage, totalpage, PageInfo.numberOfPage);
		selectQuery.setFirstResult(starting);
		selectQuery.setMaxResults((int) PageInfo.pageList);
		List<State> states = selectQuery.list();

		pageble.setCurrentPage(currentpage);
		pageble.setObject(ConvertUtil.convertStateDtoToState(states));
		pageble.setPageList(pagesnumbers);
		pageble.setLastpage(totalpage);

		return pageble;
	}

	@Override
	public List<StateDTO> getAllState() {

		List<State> stateList = ConvertUtil.convertIterableToList(stateRepository.findAll());
		return ConvertUtil.convertStateDtoToState(stateList);
	}

	@Override
	public State getStateById(long stateId) {
		return stateRepository.findOne(stateId);
	}

	@Override
	public List<StateDTO> getStatesByCountryIso(String iso) {
		return null;
		// List<State>
		// stateList=ConvertUtil.convertIterableToList(stateRepository.findStatesByCountryIso(iso));
		// return ConvertUtil.convertStateDtoToState(stateList);
	}

	@Override
	public void editState(StateDTO stateDTO) {
		State state = stateRepository.findOne(stateDTO.getId());
		state.setName(stateDTO.getName());
		state.setStatus(stateDTO.getStatus());
		stateRepository.save(state);
	}

	@Override
	public void saveBulkState(StateDTO stateDTO) {
		State state = ConvertUtil.convertDtoToState(stateDTO);
		stateRepository.save(state);
		logger.debug("State Saved");

	}

}
