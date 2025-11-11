package com.example.board.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ReplyDAO {
	List<ReplyDTO> list(@Param("board_idx") int board_idx, @Param("start") int start, @Param("end") int end);
	int count(int board_idx);
	void insert(ReplyDTO dto);
	void update(ReplyDTO dto);
	void delete(int idx);
	ReplyDTO detail(int idx);
}
