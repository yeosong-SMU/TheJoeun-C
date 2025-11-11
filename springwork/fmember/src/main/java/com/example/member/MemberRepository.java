package com.example.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {
	@Query(value = "select * from member where id=:id "
			+ "and passowrd=standard_hash(:pwd, 'sha256')", nativeQuery = true)
	Member findByLogin(@Param("id") String id, 
			@Param("pwd") String pwd);
	
	@Modifying
	@Transactional
	@Query(value="insert into member "
			+ "(id, password, name, email, role, regdate) "
			+ "values (:id, standard_hash(:pwd, 'sha256'), :name, :email, 'user', sysdate)", 
			nativeQuery = true)
	void register (@Param("id") String id, 
		@Param("pwd") String password,
		@Param("name") String name, 
		@Param("email") String email);
}