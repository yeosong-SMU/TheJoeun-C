package com.example.excel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository repo;
	
	@Transactional
	public int saveAll(List<Product> items) {
		repo.saveAll(items);
		return items.size();
	}
	
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return repo.findAll();
	}
}