package com.example.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}