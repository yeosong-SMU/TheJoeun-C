package com.example.backend.memo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoController {
	@Autowired
	MemoDAO memoDao;
	
	@RequestMapping("/api/memo")
	public List<Map<String, Object>> list(@RequestParam(name = "memo", defaultValue = "") String memo) {
		return memoDao.list("%" + memo + "%");
	}
	
	@PostMapping("/api/memo/insert")
	void insert(@RequestParam Map<String, Object> map) {
		memoDao.insert(map);
	}
	
	@GetMapping("/api/memo/detail/{idx}")
	public Map<String, Object> detail(@PathVariable(name = "idx") int idx) {
		return memoDao.detail(idx);
	}
	
	@PostMapping("/api/memo/update")
	public void update(@RequestParam Map<String, Object> map) {
		memoDao.update(map);
	}
	
	@GetMapping("/api/memo/delete")
	public void delete(@RequestParam(name = "idx") int idx) {
		memoDao.delete(idx);
	}
}