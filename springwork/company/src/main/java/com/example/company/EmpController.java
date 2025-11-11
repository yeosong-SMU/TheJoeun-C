package com.example.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmpController {
	@Autowired
	EmpDAO dao;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/list.do";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("list");
		List<EmpDTO> list = dao.list();
		mav.addObject("items", list);
		return mav;
	}
	
	@GetMapping("form.do")
	public String write() {
		return "form";
	}
	
	@PostMapping("insert.do")
	public String insert(EmpDTO dto) {
		if(dto.getHiredate() == null || dto.getHiredate().isEmpty()) {
			String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
			dto.setHiredate(today);
		}
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("detail.do")
	public ModelAndView view(@RequestParam(name = "empno") int empno, ModelAndView mav) {
		mav.setViewName("detail");
		mav.addObject("dto", dao.detail(empno));
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(EmpDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("delete.do")
	public String delete(@RequestParam(name="empno") int empno) {
		dao.delete(empno);
		return "redirect:/list.do";
	}
}