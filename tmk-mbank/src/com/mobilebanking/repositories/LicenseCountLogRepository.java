package com.mobilebanking.repositories;

import com.mobilebanking.entity.LicenseCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseCountLogRepository extends JpaRepository<LicenseCountLog, Long> {

    @Query("select l from LicenseCountLog l ")
    List<LicenseCountLog> findLicenseCountLog();
}
