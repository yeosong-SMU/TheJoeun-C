package com.example.product.member.model;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public String login(MemberDTO dto) {
		return sqlSession.selectOne("member.login", dto);
	}
}
