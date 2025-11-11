package com.example.trigger;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
	private final MemberRepository repo;
	
	public Member signup(String username, String rawPassword) {
		Member m = Member.builder().username(username)
				.password(rawPassword)
				.status("ACTIVE")
				.build();
		return repo.save(m);
	}
	
	// 탈퇴: status를 inactive로 변경
	// 테이블의 컬럼이 바뀌면서 자동 변경 감지
	// => update 수행
	// => 트리거가 작동하면서 deleted_at 값이 자동으로 채워짐
	public void withdraw(Long id) {
		Member m = repo.findById(id).orElseThrow();
		if (!"INACTIVE".equals(m.getStatus())) {
			m.setStatus("INACTIVE");
		}
	}
}