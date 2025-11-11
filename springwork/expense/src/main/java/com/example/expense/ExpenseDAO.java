package com.example.expense;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExpenseDAO {
	List<ExpenseDTO> list();
	
	void insert(ExpenseDTO dto);
	
	ExpenseDTO detail(@Param("idx") int idx);
	
	void update(ExpenseDTO dto);
	
	void delete(@Param("idx") int idx);
}
