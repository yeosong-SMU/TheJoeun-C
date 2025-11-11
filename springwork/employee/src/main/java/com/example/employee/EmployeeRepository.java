package com.example.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByDepartment_DeptId(Long deptId);
	List<Employee> findByEmpNameContaining(String keyword);
	List<Employee> findByDepartment_DeptIdAndEmpNameContaining(Long deptId, String keyword);
}