package com.example.product.member.model;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public String login(MemberDTO dto) {
		return sqlSession.selectOne("admin.login", dto);
	}
}
