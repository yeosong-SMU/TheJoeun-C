package com.example.pdf;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmpController {
	private final EmpService eservice;
	private final PdfService pservice;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("employees", eservice.list());
		return "list";
	}
	
	@GetMapping(value = "/pdf",
			produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> export(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<>();
		m.put("employees", eservice.list());
		
		// html 내부의 상대경로 매칭 작업
		String baseUrl = request.getScheme() + "://" 
		+ request.getServerName() + ":" 
				+ request.getServerPort() 
				+ request.getContextPath() + "/";
		
		byte[] pdf = pservice.renderTemplateToPdf("list_pdf", m, baseUrl);
		
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						"attachment; filename=employee-list.pdf")
				.body(pdf);
	}
}