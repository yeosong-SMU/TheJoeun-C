package com.example.jpashop.api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.jpashop.dto.ProductDTO;
import com.example.jpashop.entity.Product;
import com.example.jpashop.repository.ProductRepository;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product/*")
public class ProductApi {
	@Autowired
	ProductRepository pr;
	
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping(value="list", method= {RequestMethod.GET, RequestMethod.POST})
	public List<ProductDTO> list(@RequestParam(name = "productName", defaultValue="") String productName) {
		List<Product> items = null;
		if(productName.equals("")) {
			items = pr.findAll(Sort.by(Sort.Direction.ASC, "productName"));
		} else {
			items = pr.findByProductNameContaining(productName, Sort.by(Sort.Direction.ASC, "productName"));
		}
		List<ProductDTO> new_items = new ArrayList<>();
		for (Product p : items) {
			ProductDTO dto = new ProductDTO(p.getProductCode(), p.getProductName(), p.getPrice(), p.getDescription(), p.getFilename());
			new_items.add(dto);
		}
		return new_items;
	}
	
	@PostMapping("insert")
	public void insert(ProductDTO dto, HttpServletRequest request) {
		String filename = "-";
		if(dto.getImg() != null && !dto.getImg().isEmpty()) {
			filename = dto.getImg().getOriginalFilename();
			try {
				String path = request.getSession().getServletContext().getRealPath("/images/");
				new File(path).mkdir();
				dto.getImg().transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dto.setFilename(filename);
		Product p = modelMapper.map(dto, Product.class);
		pr.save(p);
	}
	
	@GetMapping("detail/{productCode}")
	public Map<String, Object> detail(@PathVariable(name = "productCode") long productCode, 
			ModelAndView mav) {
		Optional<Product> opt = pr.findById(productCode);
		Product p = opt.get();
		ProductDTO dto = new ProductDTO();
		dto.setProductCode(p.getProductCode());
		dto.setProductName(p.getProductName());
		dto.setPrice(p.getPrice());
		dto.setDescription(p.getDescription());
		dto.setFilename(p.getFilename());
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		return map;
	}
	
	@PostMapping("update")
	public void update(ProductDTO dto, 
			HttpServletRequest request) {
		String filename = "-";
		if(dto.getImg() != null && !dto.getImg().isEmpty()) {
			filename = dto.getImg().getOriginalFilename();
			try {
				String path = request.getSession().getServletContext().getRealPath("/images/");
				new File(path).mkdir();
				dto.getImg().transferTo(new File(path + filename));
			} catch (Exception e) {
				e.printStackTrace();
			}
			dto.setFilename(filename);
		} else {
			Optional<Product> opt = pr.findById(dto.getProductCode());
			Product dto2 = opt.get();
			dto.setFilename(dto2.getFilename());
		}
		Product p = modelMapper.map(dto, Product.class);
		pr.save(p);
	}
	
	@GetMapping("delete/{productCode}")
	public void delete(@PathVariable(name = "productCode") long productCode, 
			HttpServletRequest request) {
		Optional<Product> opt = pr.findById(productCode);
		Product dto = opt.get();
		String filename = dto.getFilename();
		if(filename != null && !filename.equals("-")) {
			String path = request.getSession().getServletContext().getRealPath("/images/");
			File f = new File(path + filename);
			if(f.exists()) {
				f.delete();
			}
		}
		pr.deleteById(productCode);
	}
}