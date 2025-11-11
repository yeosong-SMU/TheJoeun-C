package com.example.aop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.aop.model.MessageDAO;
import com.example.aop.model.MessageDTO;
import com.example.aop.model.PointDAO;
import com.example.aop.model.UserDTO;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageDAO messageDao;
	
	@Autowired
	PointDAO pointDao;
	
	@Transactional
	@Override
	public void insertMessage(MessageDTO dto) {
		messageDao.create(dto);
		pointDao.updatePoint(dto.getSender(), 10);
	}
	
	@Override
	public List<UserDTO> list_user() {
		return messageDao.list_user();
	}

	@Override
	public List<MessageDTO> list_message() {
		return messageDao.list_message();
	}

	@Transactional
	@Override
	public void read(String userid, int idx) {
		messageDao.read(idx);
		pointDao.updatePoint(userid, 5);
	}
}