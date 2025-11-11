package com.example.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeApiController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/employee/list")
	public List<Employee> getAllEmployees(@RequestParam(name = "keyword", required = false) String keyword, 
			@RequestParam(name = "departmentId", required = false) Long departmentId) {
		if(departmentId != null && keyword != null) {
			return employeeRepository.findByDepartment_DeptIdAndEmpNameContaining(departmentId, keyword);
		} else if (departmentId != null) {
			return employeeRepository.findByDepartment_DeptId(departmentId);
		} else if (keyword != null) {
			return employeeRepository.findByEmpNameContaining(keyword);
		} else {
			return employeeRepository.findAll();
		}
		
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployeeById(@PathVariable(name = "id") Long id) {
		return employeeRepository.findById(id).orElse(null);
	}
	
	@PostMapping("/employee/save")
	public Employee saveEmployee(@RequestBody Employee emp) {
		if(emp.getDepartment() != null && emp.getDepartment().getDeptId() != null) {
			Department dept = departmentRepository.findById(emp.getDepartment().getDeptId()).orElse(null);
			emp.setDepartment(dept);
		}
		return employeeRepository.save(emp);
	}
	
	@PostMapping("/employee/update")
	public Employee updateEmployee(@RequestBody Employee emp) {
		Employee existing = employeeRepository.findById(emp.getEmpId()).orElse(null);
		if(existing != null) {
			existing.setEmpName(emp.getEmpName());
			existing.setPosition(emp.getPosition());
			existing.setSalary(emp.getSalary());
			existing.setHireDate(emp.getHireDate());
			
			if(emp.getDepartment() != null && emp.getDepartment().getDeptId() != null) {
				Department dept = departmentRepository.findById(emp.getDepartment().getDeptId()).orElse(null);
				existing.setDepartment(dept);
			}
			
			return employeeRepository.save(existing);
		}
		return null;
	}
	
	@GetMapping("/employee/delete")
	public void deleteEmployee(@RequestParam(name = "id") Long id) {
		employeeRepository.deleteById(id);
	}
	
	@GetMapping("/department/list")
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}
}
