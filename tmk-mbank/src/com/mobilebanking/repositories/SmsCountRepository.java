package com.mobilebanking.repositories;

import com.mobilebanking.entity.SmsCountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SmsCountRepository extends JpaRepository<SmsCountLog, Long> {
    @Query("select l from SmsCountLog l ")
    List<SmsCountLog> findSmsCountLog();
}
