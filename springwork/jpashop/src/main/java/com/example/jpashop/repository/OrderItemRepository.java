package com.example.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}