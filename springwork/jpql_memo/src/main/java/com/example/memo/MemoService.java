package com.example.memo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemoService {
	@Autowired
	private MemoRepository memoRepository;
	
	public List<Memo> getAllMemos() {
		return memoRepository.findAll();
	}
	
	public List<Memo> searchByTitle(String keyword) {
		return memoRepository.findByTitleContaining(keyword);
	}
	
	public List<Memo> searchByContent(String keyword) {
		return memoRepository.findByContentContaining(keyword);
	}
	
	public List<Memo> searchByTitleOrContent(String keyword) {
		return memoRepository.findByTitleOrContent(keyword);
	}
}