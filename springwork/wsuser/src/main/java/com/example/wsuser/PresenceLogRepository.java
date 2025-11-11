package com.example.wsuser;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PresenceLogRepository extends JpaRepository<PresenceLog, Long> {
	@Query("select p from PresenceLog p order by p.atTs desc")
	List<PresenceLog> findRecent(Pageable pageable);
	
	@Query("select p from PresenceLog p where p.nickname "
			+ "in :nicknames order by p.nickname asc, p.atTs desc")
	List<PresenceLog> findByNicknamesOrderByTimeDesc(List<String> nicknames);
}