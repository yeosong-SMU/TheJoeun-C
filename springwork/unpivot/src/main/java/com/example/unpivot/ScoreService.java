package com.example.unpivot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
	@Autowired
	ScoreUnpivotDAO dao;
	
	public List<ScoreUnpivotRow> listAll() {
		return dao.findAll();
	}
	
	public List<ScoreUnpivotRow> listByStudent(Long studentId) {
		return (studentId == null) ? dao.findAll() : dao.findByStudentId(studentId);
	}
}