package com.example.jwt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	
	@Query(value = "select * from member "
			+ "where username = :username and "
			+ "password = STANDARD_HASH(:rawPassword, 'SHA256') "
			+ "fetch first 1 rows only", nativeQuery = true)
	Optional<Member> loginNative(@Param("username") String username, 
			@Param("rawPassword") String rawPassword);
	
	@Modifying
	@Query(value = "insert into member "
			+ "(username, password, role, display_name) values "
			+ "(:username, STANDARD_HASH(:rawPassword, 'SHA256'), 'USER', :displayName) "
			, nativeQuery = true)
	int insertMember(@Param("username") String username, 
			@Param("rawPassword") String rawPassword, 
			@Param("displayName") String displayName);
}