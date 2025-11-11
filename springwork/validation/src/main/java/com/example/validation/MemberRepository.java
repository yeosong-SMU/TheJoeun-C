package com.example.validation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
	Member findByEmail(String email);
}