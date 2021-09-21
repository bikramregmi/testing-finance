package com.cas.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cas.entity.Session;

public interface SessionRepository extends CrudRepository<Session, String> {

}
