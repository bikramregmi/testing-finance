package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.OfsResponse;

@Repository
public interface OfsMessageRepository extends JpaRepository<OfsResponse, Long> {

}
