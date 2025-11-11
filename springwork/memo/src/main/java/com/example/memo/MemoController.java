package com.example.memo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemoController {
	@Autowired
	MemoDAO memoDao;
	
	@GetMapping("/")
	public ModelAndView list(ModelAndView mav) {
		List<MemoDTO> items = memoDao.list();
		mav.setViewName("list");
		mav.addObject("list", items);
		return mav;
	}
	
	@PostMapping("/insert.do")
	public String insert(MemoDTO dto) {
		memoDao.insert(dto.getWriter(), dto.getMemo());
		return "redirect:/";
	}
	
	@GetMapping("/view/{idx}")
	public ModelAndView view(@PathVariable(name="idx") int idx, ModelAndView mav) {
		mav.setViewName("view");
		mav.addObject("dto", memoDao.view(idx));
		return mav;
	}
	
	@PostMapping("/update/{idx}")
	public String update(@PathVariable(name="idx") int idx, MemoDTO dto) {
		memoDao.update(dto);
		return "redirect:/";
	}
	
	@PostMapping("/delete/{idx}")
	public String delete(@PathVariable(name="idx") int idx) {
		memoDao.delete(idx);
		return "redirect:/";
	}
}
