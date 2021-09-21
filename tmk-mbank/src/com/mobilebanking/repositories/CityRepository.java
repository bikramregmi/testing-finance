package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	
	@Query("select c from City c order by c.name")
	List<City> findCity();
	
	@Query("select c from City c where c.name=?1")
	City findByCity(String name);
	
	@Query("select c from City c where c.state.id=?1")
	List<City> findCityByState(long state);
	
	@Query("select c from City c where c.state.name=:state")
	List<City> findCityByStateName(@Param("state") String stateName);
	
	@Query("select c from City c where c.state.name=:state and c.id=:city")
	City findCityByIdAndState(@Param("state") String stateName, @Param("city") Long cityId);

	@Query("select c from City c where c.state.name=?1 and c.name=?2")
	City findCitByCityNamesAndState(String state, String city);

	City findByName(String city);
	
}
