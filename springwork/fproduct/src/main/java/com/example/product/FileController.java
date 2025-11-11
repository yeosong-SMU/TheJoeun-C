package com.example.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {
	@Value("${app.upload-dir}")
	private String uploadDir;
	
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
		String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String filename = UUID.randomUUID() + (ext != null ? "." + ext : "");
		Path dir = Paths.get(uploadDir);
		Files.createDirectories(dir);
		Path target = dir.resolve(filename);
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
		String url = "/files/" + filename; //정적 서빙 URL
		return ResponseEntity.ok(Map.of("url", url));
	}
}