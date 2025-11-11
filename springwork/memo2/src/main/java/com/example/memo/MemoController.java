package com.example.memo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memo2")
public class MemoController {
	@Autowired
	private MemoDAO memoDao;
	
	@GetMapping
	public List<Map<String, Object>> list() {
		return memoDao.list();
	}
	
	@GetMapping("/search")
	public List<Map<String, Object>> search(@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		return memoDao.search("%" + keyword + "%");
	}
	
	@GetMapping("/{id}")
	public Map<String, Object> detail(@PathVariable(name="id") int id) {
		return memoDao.detail(id);
	}
	
	@PostMapping
	public void insert(@RequestBody Map<String, Object> memo) {
		memoDao.insert(memo);
	}
	
	@PutMapping
	public void update(@RequestBody Map<String, Object> memo) {
		memoDao.update(memo);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name="id") int id) {
		memoDao.delete(id);
	}
}