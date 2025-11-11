package com.example.schedule;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class CleanupScheduler {
	private final UsersRepo usersRepo;
	private final DeletionLogRepo logRepo;
	
	// 매분 0초에 실행
	@Scheduled(cron = "0 * * * * *")
	@Transactional
	public void cleanUpTempUsers() {
		LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
		
		List<Users> targets = usersRepo.findTempUsersBefore(cutoff);
		if(targets.isEmpty())
			return;
		
		List<DeletionLog> logs = targets.stream().map(u -> {
			DeletionLog l = new DeletionLog();
			l.setUserId(u.getId());
			l.setUsername(u.getUsername());
			l.setDeletedAt(LocalDateTime.now());
			l.setReason("temp>7d");
			return l;
		}).toList();
		logRepo.saveAll(logs);
		usersRepo.deleteByIdIn(targets.stream().map(Users::getId).toList());
	}
}