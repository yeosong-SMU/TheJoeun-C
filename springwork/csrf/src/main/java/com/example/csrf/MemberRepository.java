package com.example.csrf;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByUserid(String userid);
	
	@Modifying   //select가 아닌 dml
	@Query(value = "insert into member (userid, pwd, name) values "
			+ "(:userid, STANDARD_HASH(:pwd, 'SHA256'), :name)", 
			nativeQuery = true)
	int insertWithHash(@Param("userid") String userid, 
			@Param("pwd") String pwd, 
			@Param("name") String name);
	
	@Query(value = "select userid, pwd, name from member "
			+ "where userid = :userid and "
			+ "pwd = STANDARD_HASH('1234', 'SHA256')", 
			nativeQuery = true)
	Optional<Member> loginRaw(@Param("userid") String userid,
			@Param("pwd") String pwd);
}