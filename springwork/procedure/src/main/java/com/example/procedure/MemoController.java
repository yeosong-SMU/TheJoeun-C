package com.example.procedure;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemoController {
	private final MemoService service;
	
	@GetMapping("/")
	public String list(Model model) {
		model.addAttribute("memos", service.listMemos());
		return "index";
	}
	
	@PostMapping("/add")
	public String add(@RequestParam(name = "content") String content) {
		service.addMemo(content);
		return "redirect:/";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name = "id") Long id,
			Model model) {
		model.addAttribute("memo", service.getMemo(id));
		return "edit";
	}
	
	@PostMapping("/edit")
	public String edit(@RequestParam(name = "id") Long id,
			@RequestParam(name = "content") String content) {
		service.updateMemo(id, content);
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(name = "id") Long id) {
		service.deleteMemo(id);
		return "redirect:/";
	}
}