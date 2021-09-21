package com.mobilebanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
