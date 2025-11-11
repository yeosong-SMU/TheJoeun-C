package com.example.login;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class LoginService {
	//계정별 시도 횟수
	private final ConcurrentHashMap<String, AtomicInteger> accountAttempts = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Long> accountLockUntil = new ConcurrentHashMap<>();
	
	//IP별 시도 횟수
	private final ConcurrentHashMap<String, AtomicInteger> ipAttempts = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, Long> ipLockUntil = new ConcurrentHashMap<>();
	
	//실습용 임계치
	private final int MAX_ACCOUNT_ATTEMPTS = 3; //계정 실패 허용 횟수
	private final int MAX_IP_ATTEMPTS = 5; //IP 실패 허용 횟수
	private final long LOCK_TIME_MS = 10 * 1000L; //10초 잠금
	
	//로그인 성공 시 카운트 초기화
	public void loginSucceededAccount(String acctKey) {
		accountAttempts.remove(acctKey);
		accountLockUntil.remove(acctKey);
	}
	
	public void loginSucceededIp(String ipKey) {
		ipAttempts.remove(ipKey);
		ipLockUntil.remove(ipKey);
	}
	
	//로그인 실패 시 카운트 증가
	public void loginFailedAccount(String acctKey) {
		accountAttempts.compute(acctKey, (k, v) -> {
			if (v == null)
				return new AtomicInteger(1);
			v.incrementAndGet();
			return v;
		});
		AtomicInteger ai = accountAttempts.get(acctKey);
		if (ai != null && ai.get() >= MAX_ACCOUNT_ATTEMPTS) {
			accountLockUntil.put(acctKey, System.currentTimeMillis() + LOCK_TIME_MS);
		}
	}
	
	public void loginFailedIp(String ipKey) {
		ipAttempts.compute(ipKey, (k, v) -> {
			if (v == null)
				return new AtomicInteger(1);
			v.incrementAndGet();
			return v;
		});
		AtomicInteger ai = ipAttempts.get(ipKey);
		if (ai != null && ai.get() >= MAX_IP_ATTEMPTS) {
			ipLockUntil.put(ipKey, System.currentTimeMillis() + LOCK_TIME_MS);
		}
	}
	
	//차단 여부 확인
	public boolean isAccountBlocked(String acctKey) {
		Long until = accountLockUntil.get(acctKey);
		if (until == null)
			return false;
		if (System.currentTimeMillis() > until) {
			accountLockUntil.remove(acctKey);
			accountAttempts.remove(acctKey);
			return false;
		}
		return true;
	}
	
	public boolean isIpBlocked(String ipKey) {
		Long until = ipLockUntil.get(ipKey);
		if (until == null)
			return false;
		if (System.currentTimeMillis() > until) {
			ipLockUntil.remove(ipKey);
			ipAttempts.remove(ipKey);
			return false;
		}
		return true;
	}
	
	//현재 시도 횟수 조회
	public int getAccountAttempts(String acctKey) {
		AtomicInteger ai = accountAttempts.get(acctKey);
		return ai == null ? 0 : ai.get();
	}
	
	public int getIpAttempts(String ipKey) {
		AtomicInteger ai = ipAttempts.get(ipKey);
		return ai == null ? 0 : ai.get();
	}
}