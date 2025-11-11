package com.example.churn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/churn")
public class ChurnController {
	@Autowired
	private ChurnRepository churnRepository;
	
	@GetMapping
	public String listChurn(Model model) {
		List<Churn> churnList = churnRepository.findAll();
		model.addAttribute("churnList", churnList);
		return "list";
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("churn", new Churn());
		return "add";
	}
	
	@PostMapping("/add")
	public String addChurn(@ModelAttribute Churn churn) {
		if(churn.getChurnProbability() != null && churn.getChurnProbability() >= 0.5) {
			churn.setChurn(1);
		} else {
			churn.setChurn(0);
		}
		churnRepository.save(churn);
		return "redirect:/churn";
	}
	
	@GetMapping("/edit/{customerId}")
	public String showEditForm(@PathVariable(name = "customerId") Long customerId,
			Model model) {
		Churn churn = churnRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Churn entry not found with ID: " + customerId));
		model.addAttribute("churn", churn);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String updateChurn(@ModelAttribute Churn churn) {
		churnRepository.save(churn);
		return "redirect:/churn";
	}
	
	@PostMapping("/delete")
	public String deleteChurn(@RequestParam(name = "customerId") Long customerId) {
		churnRepository.deleteById(customerId);
		return "redirect:/churn";
	}
	
	@GetMapping("/upload")
	public String showAddForm() {
		return "upload";
	}
	
	@PostMapping("/save-predictions")
	@ResponseBody
	public void savePredictions(@RequestBody List<Churn> predictions) {
		for(Churn prediction: predictions) {
			churnRepository.save(prediction);
		}
	}
}