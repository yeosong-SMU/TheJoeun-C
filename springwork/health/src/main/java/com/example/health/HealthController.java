package com.example.health;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/health")
public class HealthController {
	@Autowired
	private HealthRepository healthRepository;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Health> list = healthRepository.findAll();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name="id") Long id, 
			Model model) {
		Health health = healthRepository.findById(id).orElse(null);
		model.addAttribute("health", health);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String update(@ModelAttribute Health health) {
		health.setBmi(calculateBmi(health.getHeight(), health.getWeight()));
		healthRepository.save(health);
		return "redirect:/health/list";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(name="id") Long id) {
		healthRepository.deleteById(id);
		return "redirect:/health/list";
	}
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String uploadCsv(@RequestParam(name="file") MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				
				if(fields.length != 10)
					continue;
				
				Health h = new Health();
				h.setAge(Integer.parseInt(fields[0].trim()));
				h.setGender(Integer.parseInt(fields[1].trim()));
				h.setHeight(Double.parseDouble(fields[2].trim()));
				h.setWeight(Double.parseDouble(fields[3].trim()));
				h.setBmi(calculateBmi(h.getHeight(), h.getWeight()));
				h.setSystolicBp(Integer.parseInt(fields[4].trim()));
				h.setDiastolicBp(Integer.parseInt(fields[5].trim()));
				h.setCholesterol(Integer.parseInt(fields[6].trim()));
				h.setSmoker(Integer.parseInt(fields[7].trim()));
				h.setExerciseFreq(Integer.parseInt(fields[8].trim()));
				h.setScore(Double.parseDouble(fields[9].trim()));
				h.setMessage("미입력");
				
				healthRepository.save(h);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/health/list";
	}
	
	private double calculateBmi(double height, double weight) {
		return Math.round(weight / Math.pow(height / 100, 2) * 10) / 10.0;
	}
}