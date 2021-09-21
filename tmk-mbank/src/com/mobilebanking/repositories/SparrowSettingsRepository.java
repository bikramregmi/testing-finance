package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.SparrowSettings;

@Repository
public interface SparrowSettingsRepository extends JpaRepository<SparrowSettings, Long>{

	@Query("select S from SparrowSettings s")
	SparrowSettings findSettings();

}
