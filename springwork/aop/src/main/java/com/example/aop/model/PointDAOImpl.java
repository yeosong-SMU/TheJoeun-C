package com.example.aop.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PointDAOImpl implements PointDAO {
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void updatePoint(String userid, int point) {
		Map<String, Object> map = new HashMap<>();
		map.put("userid", userid);
		map.put("point", point);
		sqlSession.update("point.update", map);
	}
}