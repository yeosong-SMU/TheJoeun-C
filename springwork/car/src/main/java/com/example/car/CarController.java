package com.example.car;

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

@Controller
@RequestMapping("/cars")
public class CarController {
	@Autowired
	private CarRepository carRepository;
	
	@GetMapping
	public String list(Model model) {
		List<Car> carList = carRepository.findAll();
		model.addAttribute("carList", carList);
		return "list";
	}
	
	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("car", new Car());
		return "add";
	}
	
	@PostMapping("/add")
	public String addSubmit(@ModelAttribute Car car) {
		carRepository.save(car);
		return "redirect:/cars";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable(name = "id") Long carId, 
			Model model) {
		Car car = carRepository.findById(carId).orElse(null);
		model.addAttribute("car", car);
		return "edit";
	}
	
	@PostMapping("/edit")
	public String editSubmit(@ModelAttribute Car car) {
		carRepository.save(car);
		return "redirect:/cars";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(name = "id") Long carId) {
		carRepository.deleteById(carId);
		return "redirect:/cars";
	}
}