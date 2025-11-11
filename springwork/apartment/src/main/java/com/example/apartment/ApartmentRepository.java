package com.example.apartment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
	List<Apartment> findByAddressContainingIgnoreCase(String keyword);
}