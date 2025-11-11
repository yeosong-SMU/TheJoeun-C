package com.example.diabetes;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DiabetesRepository extends JpaRepository<Diabetes, Long> {
}