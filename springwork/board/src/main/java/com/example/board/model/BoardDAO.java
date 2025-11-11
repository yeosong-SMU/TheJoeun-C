package com.example.board.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardDAO {
	List<BoardDTO> list(@Param("start") int start, 
			@Param("end") int end, 
			@Param("search_option") String search_option, 
			@Param("keyword") String keyword);
	
	void insert(BoardDTO dto);
	
	BoardDTO detail(int idx);
	
	void increase_hit(int idx);
	
	void update(BoardDTO dto);
	
	void delete(int idx);
	
	int count(@Param("search_option") String search_option, 
			@Param("keyword") String keyword);
	
	List<String> list_attach(int idx);
	
	void insert_attach(String file_name);
	
	void update_attach(@Param("file_name") String file_name, 
			@Param("idx") int idx);
	
	void delete_attach(String file_name);
}
