package com.example.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
}