package com.example.mongodb.memo;

import java.util.List;

public interface MemoDAO {
	List<MemoDTO> list();
	void insert(MemoDTO dto);
	MemoDTO detail(String _id);
	void update(MemoDTO dto);
	void delete(String _id);
}