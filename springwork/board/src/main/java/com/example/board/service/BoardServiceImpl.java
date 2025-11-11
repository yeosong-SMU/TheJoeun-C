package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.model.BoardDAO;
import com.example.board.model.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO boardDao;
	
	@Override
	public List<BoardDTO> list(int start, int end, String search_option, String keyword) {
		return boardDao.list(start, end, search_option, keyword);
	}

	@Transactional
	@Override
	public void insert(BoardDTO dto) {
		boardDao.insert(dto);
		String[] files = dto.getFiles();
		if(files == null)
			return;
		for(String name : files) {
			boardDao.insert_attach(name);
		}
	}

	@Override
	public BoardDTO detail(int idx) {
		increase_hit(idx);
		return boardDao.detail(idx);
	}

	@Override
	public void increase_hit(int idx) {
		boardDao.increase_hit(idx);
	}

	@Transactional
	@Override
	public void update(BoardDTO dto) {
		boardDao.update(dto);
		String[] files = dto.getFiles();
		if(files == null)
			return;
		for(String name : files) {
			boardDao.update_attach(name, dto.getIdx());
		}
	}

	@Override
	public void delete(int idx) {
		boardDao.delete(idx);
	}

	@Override
	public int count(String search_option, String keyword) {
		return boardDao.count(search_option, keyword);
	}

	@Override
	public List<String> list_attach(int idx) {
		return boardDao.list_attach(idx);
	}

	@Override
	public void delete_attach(String file_name) {
		boardDao.delete_attach(file_name);
	}
}