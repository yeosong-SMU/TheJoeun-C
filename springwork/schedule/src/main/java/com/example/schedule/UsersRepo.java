package com.example.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepo extends JpaRepository<Users, Long> {
	@Query("select u from Users u where u.temporary = true and u.createdAt < :cutoff")
	List<Users> findTempUsersBefore(@Param("cutoff") LocalDateTime cutoff);
	
	void deleteByIdIn(List<Long> ids);
}