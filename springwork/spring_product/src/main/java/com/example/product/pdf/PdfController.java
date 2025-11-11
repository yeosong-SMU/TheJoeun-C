package com.example.product.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pdf/*")
public class PdfController {
	@Autowired
	PdfService pdfService;
	
	@GetMapping("list.do")
	public String list(HttpSession session, Model model) {
		String userid = (String) session.getAttribute("userid");
		if(userid != null) {
			String result = pdfService.createPdf(userid);
			model.addAttribute("message", result);
			return "pdf/result";
		} else {
			return "redirect:/member/login.do?message=nologin";
		}
	}
}