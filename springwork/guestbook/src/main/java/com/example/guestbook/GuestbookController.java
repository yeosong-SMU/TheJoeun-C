package com.example.guestbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GuestbookController {
	@Autowired
	GuestbookDAO dao;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/list.do";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("list");
		List<GuestbookDTO> list = dao.list();
		mav.addObject("list", list);
		return mav;
	}
	
	@GetMapping("write.do")
	public String write() {
		return "write";
	}
	
	@PostMapping("insert.do")
	public String insert(GuestbookDTO dto) {
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("view.do")
	public ModelAndView view(@RequestParam(name="idx") int idx, 
			ModelAndView mav) {
		mav.setViewName("detail");
		mav.addObject("dto", dao.view(idx));
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(GuestbookDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@PostMapping("delete.do")
	public String delete(@RequestParam(name="idx") int idx) {
		dao.delete(idx);
		return "redirect:/list.do";
	}
}