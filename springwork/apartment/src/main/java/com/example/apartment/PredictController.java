package com.example.apartment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PredictController {
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	@GetMapping("/predict-all")
	public String predictAll() {
		List<Apartment> list = apartmentRepository.findAll();
		
		for (Apartment apt : list) {
			if(apt.getPredictedPrice() != null)
				continue;   //이미 예측된 경우 skip
			
			try {
				String area = String.valueOf(apt.getArea());
				String floor = String.valueOf(apt.getFloor());
				String rooms = String.valueOf(apt.getRooms());
				String builtYear = String.valueOf(apt.getBuiltYear());
				
				ProcessBuilder pb = new ProcessBuilder("python", "d:/python/apartment/predict.py", area, floor, rooms, builtYear);
				pb.redirectErrorStream(true);
				
				Process process = pb.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if(line.matches("\\d+")) {
						int predicted = Integer.parseInt(line);
						apt.setPredictedPrice(predicted);
						apartmentRepository.save(apt);
						break;
					}
				}
			} catch (Exception e) {
				System.err.println("예측 실패 (ID: " + apt.getId() + "): " + e.getMessage());
			}
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/predict/{id}")
	public String predictOne(@PathVariable(name="id") Long id, Model model) {
		Apartment apt = apartmentRepository.findById(id).orElse(null);
		
		if(apt != null) {
			try {
				String area = String.valueOf(apt.getArea());
				String floor = String.valueOf(apt.getFloor());
				String rooms = String.valueOf(apt.getRooms());
				String builtYear = String.valueOf(apt.getBuiltYear());
				
				ProcessBuilder pb = new ProcessBuilder("python", "d:/python/apartment/predict.py", area, floor, rooms, builtYear);
				pb.redirectErrorStream(true);
				
				Process process = pb.start();
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				String line;
				while ((line = reader.readLine()) != null) {
					line = line.trim();
					if(line.matches("\\d+")) {
						int latestPredicted = Integer.parseInt(line);
						model.addAttribute("latestPredicted", latestPredicted);
						break;
					}
				}
			} catch (Exception e) {
				model.addAttribute("latestPredicted", null);
			}
			
			model.addAttribute("apartment", apt);
		}
		
		return "edit";
	}
}