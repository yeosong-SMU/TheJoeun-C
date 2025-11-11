package com.example.mongodb.guestbook;

import java.util.List;

public interface GuestbookDAO {
	List<GuestbookDTO> list();
	void insert(GuestbookDTO dto);
	void update(GuestbookDTO dto);
	void delete(String _id);
	GuestbookDTO detail(String _id);
}