package com.example.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddressController {
	@Autowired
	AddressDAO dao;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "hello spring");
		return "home";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("list");
		List<AddressDTO> list = dao.list();
		mav.addObject("items", list);
		return mav;
	}
	
	@GetMapping("form.do")
	public String write() {
		return "form";
	}
	
	@PostMapping("insert.do")
	public String insert(AddressDTO dto) {
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("detail.do")
	public ModelAndView view(@RequestParam(name="idx") int idx, ModelAndView mav) {
		mav.setViewName("detail");
		mav.addObject("dto", dao.detail(idx));
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(AddressDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("delete.do")
	public String delete(@RequestParam(name="idx") int idx) {
		dao.delete(idx);
		return "redirect:/list.do";
	}
}
