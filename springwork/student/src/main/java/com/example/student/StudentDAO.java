package com.example.student;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentDAO {
	public List<StudentDTO> list_all();
	public List<StudentDTO> list(String majorno);
	public void insert(StudentDTO dto);
	public StudentDTO detail(int studno);
	public void update(StudentDTO dto);
	public void delete(int studno);
	public List<MajorDTO> list_major();
	public List<ProfessorDTO> list_prof();
}
