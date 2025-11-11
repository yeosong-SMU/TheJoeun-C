package com.example.pivot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentPivotService {
	@Autowired
	DeptGradePivotProcDAO dao;
	
	public List<DeptGradePivotRow> getDeptGradePivot() {
		return dao.fetch();
	}
}