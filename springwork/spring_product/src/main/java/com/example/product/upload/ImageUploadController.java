package com.example.product.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ImageUploadController {
	@PostMapping("imageUpload.do")
	public void ImageUpload(HttpServletRequest request, 
			HttpServletResponse response, 
			@RequestParam(name = "upload") MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		OutputStream out = null;
		PrintWriter printWriter = null;
		String fileName = upload.getOriginalFilename();
		byte[] bytes = upload.getBytes();
		ServletContext application = request.getSession().getServletContext();
		String uploadPath = application.getRealPath("/resources/images/");
		out = new FileOutputStream(new File(uploadPath + fileName));
		out.write(bytes);
		printWriter = response.getWriter();
		String fileUrl = request.getContextPath() + "/resources/images/" + fileName;
		printWriter.println("{\"filename\" : \"" + fileName + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
		printWriter.flush();
	}
}
