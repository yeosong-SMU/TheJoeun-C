package com.example.org;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/org")
public class OrgController {
	private final ProcService service;
	
	@GetMapping("/full")
	public String full(Model model) {
		model.addAttribute("rows", service.getFullOrg());
		return "org_full";
	}
	
	@GetMapping("/subtree")
	public String subtree(@RequestParam(name = "mgr", required = false) Long mgr,
			Model model) {
		if (mgr == null)
			mgr = 7698L;
		model.addAttribute("mgr", mgr);
		model.addAttribute("rows", service.getSubtree(mgr));
		return "org_subtree";
	}
	
	@GetMapping("/chain")
	public String chain(@RequestParam(name = "emp", required = false) Long emp,
			Model model) {
		if (emp == null)
			emp = 7499L;
		model.addAttribute("emp", emp);
		model.addAttribute("rows", service.getChainToCeo(emp));
		return "org_chain";
	}
	
	@GetMapping("/sum")
	public String sum(Model model) {
		model.addAttribute("rows", service.getSubtreeSalSum());
		return "org_sum";
	}
	
	@GetMapping("/leaves")
	public String leaves(Model model) {
		model.addAttribute("rows", service.getLeaves());
		return "org_leaves";
	}
}