package com.example.backend.shop;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ProductController {
	@Autowired
	ProductDAO productDao;

	@RequestMapping("/api/product/list")
	public List<Map<String, Object>> list(@RequestParam(name = "product_name", defaultValue = "") String product_name) {
		return productDao.list("%" + product_name + "%");
	}

	@PostMapping("/api/product/insert")
	public void insert(@RequestParam Map<String, Object> map, 
			@RequestParam(name = "img", required = false) MultipartFile img, 
			HttpServletRequest request) {
		String filename = "-";
		if(img != null && !img.isEmpty()) {
			filename = img.getOriginalFilename();
			try {
				ServletContext application = request.getSession().getServletContext();
				String path = application.getRealPath("/static/images/");
				img.transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		map.put("filename", filename);
		productDao.insert(map);
	}

	@GetMapping("/api/product/detail/{product_code}")
	public Map<String, Object> detail(@PathVariable(name = "product_code") int product_code) {
		return productDao.detail(product_code);
	}

	@PostMapping("/api/product/update")
	public void update(@RequestParam Map<String, Object> map, 
			@RequestParam(name = "img", required = false) MultipartFile img, 
			HttpServletRequest request) {
		String filename = "-";
		if(img != null && !img.isEmpty()) {
			filename = img.getOriginalFilename();
			try {
				ServletContext application = request.getSession().getServletContext();
				String path = application.getRealPath("/static/images/");
				img.transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			int product_code = Integer.parseInt(map.get("product_code").toString());
			Map<String, Object> product = productDao.detail(product_code);
			filename = product.get("filename").toString();
		}
		map.put("filename", filename);
		productDao.update(map);
	}

	@GetMapping("/api/product/delete/{product_code}")
	public void delete(@PathVariable(name = "product_code") int product_code, 
			HttpServletRequest request) {
		String filename = productDao.filename(product_code);
		if(filename != null && !filename.equals("-")) {
			ServletContext application = request.getSession().getServletContext();
			String path = application.getRealPath("/static/images/");
			File file = new File(path + filename);
			if(file.exists()) {
				file.delete();
			}
		}
		productDao.delete(product_code);
	}
}