package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mobilebanking.entity.State;

public interface StateRepository extends CrudRepository<State, Long>, JpaSpecificationExecutor<State> {
	
	@Query("select s from State s order by s.name")
	List<State> findState();
	
	@Query("select s from State s where s.name=?1")
	State findByState(String name);
	
	@Query("select s from State s where s.name=?1")
	State getStateByStateName(String name);

}
