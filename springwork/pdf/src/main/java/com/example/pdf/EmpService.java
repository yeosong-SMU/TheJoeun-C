package com.example.pdf;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpService {
	private final EmpRepository repo;
	
	public List<Emp> list() {
		return repo.findAllWithDept();
	}
}