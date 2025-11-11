package com.example.jwt;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository repo;
	
	public Member verifyLogin(String username, 
			String rawPassword) {
		return repo.loginNative(username, rawPassword).orElse(null);
	}
	
	@Transactional
	public Member join(MemberDTO dto) {
		try {
			repo.insertMember(dto.getUsername(), 
					dto.getPassword(), 
					(dto.getDisplayName() == null || dto.getDisplayName().isBlank()) 
					? dto.getUsername() : dto.getDisplayName());
			return repo.findByUsername(dto.getUsername()).orElseThrow();
		} catch (DataIntegrityViolationException e) {
			throw new IllegalStateException("이미 존재하는 아이디입니다.", e);
		}
	}
}