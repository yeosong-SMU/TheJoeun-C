package com.example.guestbook;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestbookDAO {
	List<GuestbookDTO> list();
	void insert(GuestbookDTO dto);
	GuestbookDTO view(int idx);
	void update(GuestbookDTO dto);
	void delete(int idx);
}
