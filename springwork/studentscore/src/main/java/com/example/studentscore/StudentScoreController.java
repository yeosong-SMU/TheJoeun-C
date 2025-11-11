package com.example.studentscore;

import java.io.BufferedReader;
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
public class StudentScoreController {
	@Autowired
	private StudentScoreRepository repo;
	
	@GetMapping("/")
	public String start() {
		return "redirect:/scores";
	}
	
	@GetMapping("/scores")
	public String list(Model model) {
		List<StudentScore> list = repo.findAll();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("/scores/edit/{id}")
	public String edit(@PathVariable(name="id") Long id, Model model) {
		StudentScore score = repo.findById(id).orElse(null);
		model.addAttribute("score", score);
		return "edit";
	}
	
	@PostMapping("/scores/edit")
	public String update(@ModelAttribute(name="score") StudentScore score) {
		repo.save(score);
		return "redirect:/scores";
	}
	
	@PostMapping("/scores/delete/{id}")
	public String delete(@PathVariable(name="id") Long id) {
		repo.deleteById(id);
		return "redirect:/scores";
	}
	
	@GetMapping("/scores/upload")
	public String uploadPage() {
		return "upload";
	}
	
	@PostMapping("/scores/upload")
	public String uploadCsv(@RequestParam(name="file") MultipartFile file, 
			Model model) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean skip = true;
			List<StudentScore> list = new ArrayList<>();
			
			while ((line = br.readLine()) != null) {
				if(skip) {
					skip = false;
					if(line.startsWith("\uFEFF")) {
						line = line.substring(1);   //utf-8 bom 제거
					}
					continue;
				}
				
				String [] tokens = line.split(";");
				if(tokens.length != 9) {
					throw new IllegalArgumentException("CSV 형식 오류: " + line);
				}
				
				StudentScore s = new StudentScore();
				s.setAge(Integer.parseInt(tokens[0]));
				s.setStudytime(Integer.parseInt(tokens[1]));
				s.setFailures(Integer.parseInt(tokens[2]));
				s.setAbsences(Integer.parseInt(tokens[3]));
				s.setG1(Integer.parseInt(tokens[4]));
				s.setG2(Integer.parseInt(tokens[5]));
				s.setG3(Integer.parseInt(tokens[6]));
				s.setMedu(Integer.parseInt(tokens[7]));
				s.setFedu(Integer.parseInt(tokens[8]));
				
				list.add(s);
			}
			
			repo.saveAll(list);
			model.addAttribute("message", "CSV 업로드 완료(" + list.size() + "건)");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "CSV 업로드 실패: " + e.getMessage());
		}
		
		return "upload";
	}
}