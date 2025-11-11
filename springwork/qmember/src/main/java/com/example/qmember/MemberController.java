package com.example.qmember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MemberController {
	@Autowired
	MemberRepository repo;
	
	@GetMapping({ "/", "/members" })
	public String list(Model model, 
			@ModelAttribute("cond") MemberSearchDTO cond) {
		Page<Member> page = repo.search(cond);
		model.addAttribute("page", page);
		model.addAttribute("members", page.getContent());
		model.addAttribute("total", page.getTotalElements());
		model.addAttribute("pageIndex", page.getNumber());
		model.addAttribute("pageSize", page.getSize());
		return "list";
	}
}