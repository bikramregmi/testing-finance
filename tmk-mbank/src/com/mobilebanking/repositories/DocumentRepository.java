package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobilebanking.entity.Document;

public interface DocumentRepository extends JpaRepository<Document,Long> {

}
