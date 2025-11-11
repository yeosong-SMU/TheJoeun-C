package com.example.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	@Autowired
	ProductRepository repo;
	
	public List<Product> findAll() {
		return repo.findAll();
	}
	
	public Product findById(Long id) {
		return repo.findById(id).orElseThrow();
	}
	
	@Transactional
	public Product create(ProductDTO dto) {
		Product p = new Product();
		p.setName(dto.getName());
		p.setPrice(dto.getPrice());
		p.setDescription(dto.getDescription());
		p.setImageUrl(dto.getImageUrl());
		
		return repo.save(p);
	}
	
	@Transactional
	public Product update(Long id, ProductDTO dto) {
		Product p = findById(id);
		p.setName(dto.getName());
		p.setPrice(dto.getPrice());
		p.setDescription(dto.getDescription());
		p.setImageUrl(dto.getImageUrl());
		return p;
	}
	
	@Transactional
	public void delete(Long id) {
		repo.deleteById(id);
	}
}