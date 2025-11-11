package com.example.churn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurnRepository extends JpaRepository<Churn, Long> {
}