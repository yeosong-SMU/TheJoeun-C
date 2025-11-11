package com.example.aop.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDAOImpl implements MessageDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public List<UserDTO> list_user() {
		return sqlSession.selectList("user.list");
	}

	@Override
	public List<MessageDTO> list_message() {
		return sqlSession.selectList("message.list");
	}

	@Override
	public void create(MessageDTO dto) {
		sqlSession.insert("message.create", dto);
	}

	@Override
	public void read(int idx) {
		sqlSession.update("message.update", idx);
	}
}