package com.mobilebanking.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mobilebanking.entity.UserNotification;
import com.mobilebanking.model.NotificationChannel;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {


	@Query("select count(n) from UserNotification n where n.user.id=?1 and n.seen=?2")
	Long countnNotificationByUserIdAndStatus(Long id, boolean seen);

	@Query("select n from UserNotification n where n.user.id=?1 and n.notificationChannel!=?2 group by n.id order by n.id DESC")
	Page<UserNotification> findLastTenByUserIdAndChannel(Long id,NotificationChannel channel, Pageable pageable);

	@Query("select n from UserNotification n where n.user.id=?1 and n.id<=?2 and n.seen=?3")
	List<UserNotification> findPreviousNotificationByUserIdAndLastNotificationIdAndStatus(Long id, Long lastNotificationid, Boolean status);

	
}
