package com.example.diabetes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DiabetesController {
	@Autowired
	private DiabetesRepository diabetesRepository;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/diabetes";
	}
	
	@GetMapping("/diabetes")
	public String list(Model model) {
		List<Diabetes> list = diabetesRepository.findAll();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/diabetes/upload-form")
	public String uplodForm() {
		return "upload";
	}
	
	@PostMapping("/diabetes/upload")
	public String uploadCsv(@RequestParam("file") MultipartFile file) {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			List<Diabetes> list = new ArrayList<>();
			String line;
			boolean isFirst = true;
			
			while((line = reader.readLine()) != null) {
				if(isFirst) {
					isFirst = false;
					continue;
				}
				
				String [] tokens = line.split(",");
				
				Diabetes d = new Diabetes();
				d.setPregnancies(Integer.parseInt(tokens[0].trim()));
				d.setGlucose(Integer.parseInt(tokens[1].trim()));
				d.setBloodPressure(Integer.parseInt(tokens[2].trim()));
				d.setSkinThickness(Integer.parseInt(tokens[3].trim()));
				d.setInsulin(Integer.parseInt(tokens[4].trim()));
				d.setBmi(Double.parseDouble(tokens[5].trim()));
				d.setDiabetesPedigree(Double.parseDouble(tokens[6].trim()));
				d.setAge(Integer.parseInt(tokens[7].trim()));
				d.setPrediction(Integer.parseInt(tokens[8].trim()));
				d.setProbability(Double.parseDouble(tokens[9].trim()));
				
				list.add(d);
			}
			
			diabetesRepository.saveAll(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/diabetes";
	}
	
	@GetMapping("/diabetes/edit/{id}")
	public String editForm(@PathVariable(name="id") Long id, 
			Model model) {
		Diabetes diabetes = diabetesRepository.findById(id).orElseThrow();
		model.addAttribute("diabetes", diabetes);
		return "edit";
	}
	
	@PostMapping("/diabetes/edit/{id}")
	public String edit(@PathVariable(name = "id") Long id, 
			@ModelAttribute Diabetes diabetes) {
		diabetes.setId(id);
		diabetesRepository.save(diabetes);
		return "redirect:/diabetes";
	}
	
	@GetMapping("/diabetes/delete/{id}")
	public String delete(@PathVariable(name="id") Long id) {
		diabetesRepository.deleteById(id);
		return "redirect:/diabetes";
	}
}