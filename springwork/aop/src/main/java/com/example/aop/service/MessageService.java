package com.example.aop.service;

import java.util.List;

import com.example.aop.model.MessageDTO;
import com.example.aop.model.UserDTO;

public interface MessageService {
	List<UserDTO> list_user();
	List<MessageDTO> list_message();
	void insertMessage(MessageDTO dto);
	void read(String userid, int idx);
}
