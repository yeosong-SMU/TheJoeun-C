package com.example.jpashop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
	List<Reply> findByBoardIdx(int board_idx);
	
	int countByBoardIdx(int board_idx);
}