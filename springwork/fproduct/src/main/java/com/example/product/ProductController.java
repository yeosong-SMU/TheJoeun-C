package com.example.product;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	ProductService service;
	
	@GetMapping
	public List<Product> list() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Product get(@PathVariable(name = "id") Long id) {
		return service.findById(id);
	}
	
	// json을 dto로 매핑하면서 유효성 검증 수행
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody @Validated ProductDTO dto) {
		Product saved = service.create(dto);
		return ResponseEntity.created(URI.create("/api/products/" + saved.getId())).body(saved);
	}
	
	@PutMapping("/{id}")
	public Product update(@PathVariable(name = "id") Long id, 
			@RequestBody @Validated ProductDTO dto) {
		return service.update(id, dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}