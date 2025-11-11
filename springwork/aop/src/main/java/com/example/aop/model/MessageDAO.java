package com.example.aop.model;

import java.util.List;

public interface MessageDAO {
	List<UserDTO> list_user();
	List<MessageDTO> list_message();
	void create(MessageDTO dto);
	void read(int idx);
}
