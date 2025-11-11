package com.example.todo;

import java.util.List;

public interface TodoDAO {
	List<TodoDTO> list(String title);
	
	void insert(TodoDTO dto);
	
	TodoDTO detail(int idx);
	
	void update(TodoDTO dto);
	
	void change_status(TodoDTO dto);
	
	void delete(int idx);
	
	int count();
}
