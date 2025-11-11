package com.example.jpashop.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByProductNameContaining(String productName, Sort sort);
}