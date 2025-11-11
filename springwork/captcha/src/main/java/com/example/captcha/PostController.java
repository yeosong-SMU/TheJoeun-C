package com.example.captcha;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PostController {
	@Autowired
	DefaultKaptcha captchaProducer;
	
	@Autowired
	PostRepository repo;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("posts", repo.findAll());
		return "list";
	}
	
	@GetMapping("/post")
	public String form(Model model) {
		model.addAttribute("post", new Post());
		return "form";
	}
	
	@PostMapping("/post")
	public String save(@ModelAttribute Post post,
			@RequestParam("captchaInput") String captchaInput,
			HttpSession session, Model model) {
		String expected = (String) session.getAttribute("captcha");
		if (expected == null || !expected.equalsIgnoreCase(captchaInput)) {
			model.addAttribute("post", post);
			model.addAttribute("error", "캡차 인증 실패");
			return "form";
		}
		
		session.removeAttribute("captcha");
		
		repo.save(post);
		return "redirect:/";
	}
	
	@GetMapping("/captcha")
	public void captcha(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
		String text = captchaProducer.createText();
		request.getSession().setAttribute("captcha", text);
		
		BufferedImage image = captchaProducer.createImage(text);
		try (OutputStream out = response.getOutputStream()) {
			ImageIO.write(image, "jpg", out);
			out.flush();
		}
	}
}