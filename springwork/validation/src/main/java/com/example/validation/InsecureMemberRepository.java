package com.example.validation;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class InsecureMemberRepository {
	@PersistenceContext
	private EntityManager em;
	
	// 이메일 주소에 입력 : x' OR '1' = '1
	public List<Member> findByEmailInsecure(String email) {
		System.out.println("email:"+email);
		String sql = "select * from member where email = '" + email + "'";
		
		return em.createNativeQuery(sql, Member.class).getResultList();
	}
}