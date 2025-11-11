package com.example.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {
	@Autowired
	StudentDAO dao;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/list.do";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(@RequestParam(name="majorno", defaultValue="0") String majorno, 
			ModelAndView mav) {
		mav.setViewName("list");
		List<StudentDTO> list;
		if(majorno.equals("0")) {
			list = dao.list_all();
		}else {
			list = dao.list(majorno);
		}
		mav.addObject("items", list);
		mav.addObject("majorno", majorno);
		mav.addObject("list_major", dao.list_major());
		return mav;
	}
	
	@GetMapping("form.do")
	public String write(Model model) {
		model.addAttribute("list_major", dao.list_major());
		model.addAttribute("list_prof", dao.list_prof());
		return "form";
	}
	
	@PostMapping("insert.do")
	public String insert(StudentDTO dto) {
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("detail.do")
	public ModelAndView view(@RequestParam(name="studno") int studno, ModelAndView mav) {
		mav.setViewName("detail");
		mav.addObject("dto", dao.detail(studno));
		mav.addObject("list_major", dao.list_major());
		mav.addObject("list_prof", dao.list_prof());
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(StudentDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("delete.do")
	public String delete(@RequestParam(name="studno") int studno) {
		dao.delete(studno);
		return "redirect:/list.do";
	}
}
