package com.example.sse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findTop20ByStatusOrderByCreatedAtDesc(String status);
}