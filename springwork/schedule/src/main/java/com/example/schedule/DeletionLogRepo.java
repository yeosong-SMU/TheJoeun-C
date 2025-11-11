package com.example.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletionLogRepo extends JpaRepository<DeletionLog, Long> {
}