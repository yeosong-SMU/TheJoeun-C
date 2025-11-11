package com.example.memo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/memos")
public class MemoController {
	@Autowired
	MemoRepository repo;
	
	@GetMapping
	public List<Memo> list() {
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Memo get(@PathVariable(name = "id") Long id) {
		return repo.findById(id).orElseThrow();
	}
	
	@PostMapping
	public Memo create(@RequestBody Memo memo) {
		return repo.save(memo);
	}
	
	@PutMapping("/{id}")
	public Memo update(@PathVariable(name="id") Long id,
			@RequestBody Memo memo) {
		Memo existing = repo.findById(id).orElseThrow();
		existing.setContent(memo.getContent());
		return repo.save(existing);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(name="id") Long id) {
		repo.deleteById(id);
	}
}