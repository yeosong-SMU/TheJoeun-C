package com.example.expense;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExpenseController {
	@Autowired
	ExpenseDAO dao;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/list.do";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("list");
		List<ExpenseDTO> list = dao.list();
		mav.addObject("items", list);
		return mav;
	}
	
	@GetMapping("form.do")
	public String form() {
		return "form";
	}
	
	@PostMapping("insert.do")
	public String insert(ExpenseDTO dto) {
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("detail.do")
	public ModelAndView view(@RequestParam(name = "idx") int idx, ModelAndView mav) {
		mav.setViewName("detail");
		mav.addObject("dto", dao.detail(idx));
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(ExpenseDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("delete.do")
	public String delete(@RequestParam(name="idx") int idx) {
		dao.delete(idx);
		return "redirect:/list.do";
	}
}
