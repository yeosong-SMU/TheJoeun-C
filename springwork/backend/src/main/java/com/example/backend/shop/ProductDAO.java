package com.example.backend.shop;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDAO {
	List<Map<String, Object>> list(@Param("product_name") String product_name);
	void insert(Map<String, Object> map);
	Map<String, Object> detail(@Param("product_code") int product_code);
	void update(Map<String, Object> map);
	void delete(@Param("product_code") int product_code);
	String filename(@Param("product_code") int product_code);
}