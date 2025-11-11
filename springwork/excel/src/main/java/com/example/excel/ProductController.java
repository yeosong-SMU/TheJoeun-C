package com.example.excel;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;
	
	private boolean hasXlsx(String filename) {
		if (!StringUtils.hasText(filename))
			return false;
		String ext = StringUtils.getFilenameExtension(filename);
		return ext != null && ext.equalsIgnoreCase("xlsx");
	}
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("items", service.findAll());
		return "list";
	}
	
	@GetMapping("/upload")
	public String uploadForm() {
		return "upload";
	}
	
	@PostMapping("/upload")
	public String upload(Model model, 
			@RequestParam("file") MultipartFile file) {
		if (file.isEmpty() || !hasXlsx(file.getOriginalFilename())) {
			model.addAttribute("error", "xlsx 파일을 업로드해주세요.");
			return "product/upload";
		}
		try (InputStream is = file.getInputStream()) {
			List<Product> items = ExcelHelper.parseProducts(is);
			int saved = service.saveAll(items);
			model.addAttribute("message", saved + "건 저장되었습니다.");
		} catch (Exception e) {
			model.addAttribute("error", "업로드 실패: " + e.getMessage());
		}
		return "upload";
	}
	
	@GetMapping("/export")
	public void export(HttpServletResponse response) throws Exception {
		List<Product> data = service.findAll();
		
		try (Workbook wb = ExcelHelper.toWorkbook(data)) {
			String fileName = URLEncoder.encode("products.xlsx", StandardCharsets.UTF_8);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0L);
			
			try (ServletOutputStream out = response.getOutputStream()) {
				wb.write(out);
				out.flush();
			}
		}
	}
}