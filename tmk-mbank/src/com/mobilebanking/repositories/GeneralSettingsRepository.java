package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.GeneralSettings;

@Repository
public interface GeneralSettingsRepository extends JpaRepository<GeneralSettings, Long>{
	@Query("select s from GeneralSettings s where s.settingsKey=:key")
	GeneralSettings findSettingsByKey(@Param("key") String key);
	
	@Query("select s from GeneralSettings s order by s.settingsKey asc")
	List<GeneralSettings> findAllSettings();
	
}
