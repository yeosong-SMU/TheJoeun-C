package com.example.first;

import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@GetMapping("/")
	public String main(Model model) {
		model.addAttribute("message", "welcome!");
		return "main";
	}
	
	@GetMapping("multi_table.do")
	public String multi_table(Model model) {
		return "multi_table";
	}
	
	@PostMapping("table_result.do")
	public String table_result(@RequestParam(value = "num", defaultValue = "3") int num, Model model) {
		String result = "";
		for(int i = 1; i <= 9; i++) {
			result += num + "X" + i + "=" + num*i + "<br>";
		}
		model.addAttribute("result", result);
		return "table_result";
	}
	
	@GetMapping("point.do")
	public String point() {
		return "point";
	}
	
	@PostMapping("point_result.do")
	public String point_result(@ModelAttribute PointDTO dto, Model model) {
		dto.setTotal(dto.getKor() + dto.getEng() + dto.getMat());
		String avg = String.format("%.2f", dto.getTotal() / 3.0);
		dto.setAverage(Double.parseDouble(avg));
		model.addAttribute("dto", dto);
		return "point_result";
	}
	
	@GetMapping("move.do")
	public String move() throws UnsupportedEncodingException {
		String name = URLEncoder.encode("김철수", "utf-8");
		return "redirect:/result.do?name=" + name + "&age=20";
	}
	
	@GetMapping("result.do")
	public String result(Model model, 
			@RequestParam(name = "name", defaultValue = "noname") String name, 
			@RequestParam(name = "age", defaultValue = "10") int age
			) throws Exception {
		name = URLDecoder.decode(name, "utf-8");
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "result";
	}
	
	@GetMapping("mav.do")
	public ModelAndView mav() {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", new ProductDTO("pen", 1000));
		return new ModelAndView("mav_result", "map", map);
	}
	
	@GetMapping("fetch.do")
	public String ajax() {
		return "fetch";
	}
	
	@ResponseBody
	@GetMapping("background.do")
	public ProductDTO background() {
		ProductDTO dto = new ProductDTO("TV", 500000);
		return dto;
	}
	
	@GetMapping("login.do")
	public String login() {
		return "login";
	}
	
	@GetMapping("login_result.do")
	public String login_result(
			@RequestParam(name = "id") String id, 
			@RequestParam(name = "pw") String pw, 
			Model model) {
		String result = "";
		if(id.equals("kim") && pw.equals("1234")) {
			result = "kim님 환영합니다.";
		} else {
			result = "아이디 또는 비밀번호가 틀렸습니다.";
		}
		model.addAttribute("result", result);
		return "login_result";
	}
	
	@GetMapping("fetch_table.do")
	public String ajax_gugu() {
		return "fetch_table";
	}
	
	@GetMapping("fetch_table_result.do")
	public String fetch_gugu_result(@RequestParam(name = "num") int num, Model model) {
		String result = "";
		for(int i = 1; i <= 9; i++) {
			result += num + "x" + i + "=" + num*i + "<br>";
		}
		model.addAttribute("result", result);
		return "fetch_table_result";
	}
}
