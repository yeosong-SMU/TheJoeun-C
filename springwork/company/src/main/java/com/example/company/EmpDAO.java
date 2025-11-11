package com.example.company;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpDAO {
	List<EmpDTO> list();
	
	void insert(EmpDTO dto);
	
	EmpDTO detail(int empno);
	
	void update(EmpDTO dto);
	
	void delete(int empno);
}
