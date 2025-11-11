package com.example.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
	List<Consultation> findByCustomerId(Long customerId);
}