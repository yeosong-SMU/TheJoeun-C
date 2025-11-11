package com.example.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "keyword", required = false) String keyword, 
			@RequestParam(name = "departmentId", required = false) Long departmentId, 
			Model model) {
		List<Employee> list;
		
		if(departmentId != null && keyword != null) {
			list = employeeRepository.findByDepartment_DeptIdAndEmpNameContaining(departmentId, keyword);
		} else if (departmentId != null) {
			list = employeeRepository.findByDepartment_DeptId(departmentId);
		} else if (keyword != null) {
			list = employeeRepository.findByEmpNameContaining(keyword);
		} else {
			list = employeeRepository.findAll();
		}
		
		model.addAttribute("list", list);
		model.addAttribute("departments", departmentRepository.findAll());
		model.addAttribute("keyword", keyword);
		model.addAttribute("departmentId", departmentId);
		
		return "list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("departments", departmentRepository.findAll());
		return "form";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute Employee emp) {
		if(emp.getDepartment() != null && emp.getDepartment().getDeptId() != null) {
			Department dept = departmentRepository.findById(emp.getDepartment().getDeptId()).orElse(null);
			emp.setDepartment(dept);
		}
		employeeRepository.save(emp);
		return "redirect:/employee/list";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam(name = "id") Long id, 
			Model model) {
		Employee emp = employeeRepository.findById(id).orElse(null);
		model.addAttribute("emp", emp);
		model.addAttribute("departments", departmentRepository.findAll());
		return "edit";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Employee emp) {
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
			
			employeeRepository.save(existing);
		}
		return "redirect:/employee/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(name = "id") Long id) {
		employeeRepository.deleteById(id);
		return "redirect:/employee/list";
	}
}