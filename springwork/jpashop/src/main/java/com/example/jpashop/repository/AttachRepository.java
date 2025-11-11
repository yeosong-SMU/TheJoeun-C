package com.example.jpashop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpashop.entity.Attach;

public interface AttachRepository extends JpaRepository<Attach, String> {
	void deleteByFileName(String fileName);
	
	int countByBoardIdx(int board_idx);
}