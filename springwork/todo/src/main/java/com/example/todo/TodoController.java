package com.example.todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TodoController {
	@Autowired
	TodoDAO dao;
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(name="title", defaultValue = "") String title) {
		List<TodoDTO> items = dao.list(title);
		Map<String, Object> map = new HashMap<>();
		map.put("list", items);
		map.put("count", items.size());
		return new ModelAndView("list", "map", map);
	}
	
	@GetMapping("form.do")
	public String form() {
		return "form";
	}
	
	@ResponseBody
	@GetMapping("list_json.do")
	public Map<String, Object> list_json(@RequestParam(name="title", defaultValue = "") String title) {
		List<TodoDTO> items = dao.list(title);
		Map<String, Object> map = new HashMap<>();
		map.put("list", items);
		map.put("count", items.size());
		return map;
	}
	
	@PostMapping("insert.do")
	public String insert(@ModelAttribute TodoDTO dto) {
		dao.insert(dto);
		return "redirect:/list.do";
	}
	
	@GetMapping("detail.do")
	public ModelAndView detail(@RequestParam(name="idx") int idx) {
		TodoDTO dto = dao.detail(idx);
		return new ModelAndView("detail", "dto", dto);
	}
	
	@ResponseBody
	@GetMapping("detail_json.do")
	public TodoDTO detail_json(@RequestParam(name="idx") int idx) {
		return dao.detail(idx);
	}
	
	@PostMapping("update.do")
	public String update(@ModelAttribute TodoDTO dto) {
		dao.update(dto);
		return "redirect:/list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam(name="idx") int idx) {
		dao.delete(idx);
		return "redirect:/list.do";
	}
	
	@ResponseBody
	@PostMapping("change_status.do")
	public String change_status(@RequestParam(name="idx") int idx, @RequestParam(name="done") String done) {
		TodoDTO dto = new TodoDTO();
		dto.setIdx(idx);
		dto.setDone(done);
		dao.change_status(dto);
		return "success";
	}
}
