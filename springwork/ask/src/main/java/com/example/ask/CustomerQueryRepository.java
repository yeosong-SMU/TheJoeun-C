package com.example.ask;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerQueryRepository extends JpaRepository<CustomerQuery, Long> {
}