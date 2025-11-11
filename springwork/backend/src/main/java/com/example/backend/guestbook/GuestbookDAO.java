package com.example.backend.guestbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GuestbookDAO {
	@Autowired
	SqlSession sqlSession;
	
	public List<Map<String, Object>> list(String searchkey, String search) {
		if(searchkey.equals("name_contents")) {
			return sqlSession.selectList("guestbook.list_all", "%" + search + "%");
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("searchkey", searchkey);
			map.put("search", "%" + search + "%");
			return sqlSession.selectList("guestbook.list", map);
		}
	}
	
	public void insert(Map<String, Object> map) {
		sqlSession.insert("guestbook.insert", map);
	}
	
	public Map<String, Object> detail(int idx) {
		return sqlSession.selectOne("guestbook.detail", idx);
	}
	
	public boolean check_pwd(int idx, String passwd) {
		boolean result = false;
		Map<String, Object> map = new HashMap<>();
		map.put("idx", idx);
		map.put("passwd", passwd);
		int count = sqlSession.selectOne("guestbook.check_pwd", map);
		result = count == 1 ? true : false;
		return result;
	}
	
	public void update(Map<String, Object> map) {
		sqlSession.update("guestbook.update", map);
	}
	
	public void delete(int idx) {
		sqlSession.delete("guestbook.delete", idx);
	}
}