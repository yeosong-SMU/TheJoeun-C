package com.example.rank;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RankController {
	private final EmpRepository repo;
	
	@GetMapping("/")
	public String list(Model model,
			@RequestParam(name = "rankMode", defaultValue = "bydept") String rankMode,
			@RequestParam(name = "dept", required = false) Integer dept) {
		int byDept = "bydept".equalsIgnoreCase(rankMode) ? 1 : 0;
		List<EmpRankView> rows = repo.findRanks(byDept, dept);
		List<Integer> depts = repo.findDistinctDeptNos();
		
		model.addAttribute("rows", rows);
		model.addAttribute("depts", depts);
		model.addAttribute("rankMode", rankMode);
		model.addAttribute("dept", dept);
		return "list";
	}
}