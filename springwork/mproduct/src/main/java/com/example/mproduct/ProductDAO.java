package com.example.mproduct;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDAO {
	
	List<Map<String, Object>> list(String product_name);
	
	void insert(Map<String, Object> map);
	
	Map<String, Object> detail(String product_code);
	
	void update(Map<String, Object> map);
	
	void delete(int product_code);
	
	String filename(int product_code);
}
