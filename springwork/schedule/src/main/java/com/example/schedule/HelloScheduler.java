package com.example.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloScheduler {

    private final ScheduleApplication scheduleApplication;

    HelloScheduler(ScheduleApplication scheduleApplication) {
        this.scheduleApplication = scheduleApplication;
    }
	// fixedRate: 메서드 시작 시각 기준으로 주기적 실행
	//@Scheduled(fixedRate = 5000)
	public void printMessage() {
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		System.out.println("Hello Spring Scheduler - " + now);
	}
	
	private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	// 매일 오전 9시 정각에 실행
	@Scheduled(cron = "0 0 9 * * *")
	public void morningMessage() {
		String now = LocalDateTime.now().format(fmt);
		System.out.println("아침 9시 알림 - " + now);
	}
	
	// 매주 월요일 오전 8시 30분에 실행
	@Scheduled(cron = "0 30 8 * * MON")
	public void mondayMessage() {
		String now = LocalDateTime.now().format(fmt);
		System.out.println("월요일 8시30분 알림 - " + now);
	}
}