package com.example.admission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
public class AdmissionController {
	@Autowired
	private AdmissionRepository admissionRepository;
	
	@GetMapping("/")
	public String list(Model model) {
		List<Admission> list = admissionRepository.findAll();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name = "id") Long id, 
			Model model) {
		Admission admission = admissionRepository.findById(id).orElse(null);
		System.out.println(admission);
		model.addAttribute("admission", admission);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String editSubmit(@ModelAttribute(name = "admission") Admission admission) {
		admissionRepository.save(admission);
		return "redirect:/";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") Long id) {
		admissionRepository.deleteById(id);
		return "redirect:/";
	}
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String handleUpload(@RequestParam(name = "file") MultipartFile file) {
		List<Admission> list = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line = reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(",");
				Admission a = new Admission();
				a.setKor(Integer.parseInt(tokens[0]));
				a.setEng(Integer.parseInt(tokens[1]));
				a.setMath(Integer.parseInt(tokens[2]));
				a.setWishMajor(tokens[3]);
				list.add(a);
			}
			admissionRepository.saveAll(list);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/";
	}
}