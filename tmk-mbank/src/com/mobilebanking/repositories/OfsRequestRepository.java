package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.OfsRequest;
@Repository
public interface OfsRequestRepository extends JpaRepository<OfsRequest, Long> {

}
