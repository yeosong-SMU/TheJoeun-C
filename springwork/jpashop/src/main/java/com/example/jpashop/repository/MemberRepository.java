package com.example.jpashop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByUserid(String userid);
	
	Optional<Member> findByUseridAndPasswd(String userid, String passwd);
}