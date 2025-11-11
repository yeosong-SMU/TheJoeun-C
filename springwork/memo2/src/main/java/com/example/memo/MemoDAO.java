package com.example.memo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemoDAO {
	List<Map<String, Object>> list();
	void insert(Map<String, Object> memo);
	Map<String, Object> detail(int id);
	void update(Map<String, Object> memo);
	void delete(int id);
	List<Map<String, Object>> search(String keyword);
}