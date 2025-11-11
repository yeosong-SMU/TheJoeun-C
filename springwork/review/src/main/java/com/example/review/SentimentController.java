package com.example.review;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class SentimentController {
	@Autowired
	RestTemplate restTemplate;
	
	private String mlUrl = "http://127.0.0.1:5001/predict";
	
	@GetMapping({"/", "/index"})
	public String index() {
		return "index";   //templates/index.html
	}
	
	@PostMapping("/sentiment")
	public String analyze(@RequestParam("text") String text, 
			Model model) {
		text = text == null ? "" : text.trim();
		if(text.isEmpty()) {
			model.addAttribute("error", "텍스트를 입력해 주세요.");
			return "index";
		}
		
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Map<String, String>> req = new HttpEntity<>(Map.of("text", text), headers);
			
			ResponseEntity<Map> res = restTemplate.postForEntity(mlUrl, req, Map.class);
			Map<?, ?> payload = res.getBody();
			
			if (res.getStatusCode().is2xxSuccessful() && payload != null && payload.get("label") != null) {
				model.addAttribute("label", payload.get("label"));
				model.addAttribute("score", payload.get("score"));
				model.addAttribute("text", text);
			} else {
				model.addAttribute("error", "ML 서비스 응답이 비었습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("error", "ML 호출 실패: " + e.getMessage());
		}
		return "index";
	}
}