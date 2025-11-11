package com.example.backend.memo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MemoDAO {
	List<Map<String, Object>> list(String memo);
	void insert(Map<String, Object> map);
	Map<String, Object> detail(int idx);
	void update(Map<String, Object> map);
	void delete(int idx);
}
