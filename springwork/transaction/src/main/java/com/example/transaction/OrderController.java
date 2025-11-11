package com.example.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {
	@Autowired
	OrderRepository repo;
	
	@Autowired
	RefundService service;
	
	@GetMapping
	public String list(Model model, 
			@RequestParam(name = "msg", required = false) String msg) {
		model.addAttribute("orders", repo.findAll());
		model.addAttribute("msg", msg);
		return "list";
	}
	
	@PostMapping("/orders/{id}/refund")
	public String refund(@PathVariable("id") Long id,
			@RequestParam(name = "reason", defaultValue = "고객요청") String reason) {
		service.refund(id, reason);
		return "redirect:/?msg=" + java.net.URLEncoder.encode("환불 처리 완료", java.nio.charset.StandardCharsets.UTF_8);
	}
}