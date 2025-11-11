package com.example.myjpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByUseridAndPasswd(String userid, String passwd);
}