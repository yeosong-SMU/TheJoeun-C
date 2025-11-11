package com.example.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
	@Autowired
	TodoRepository repo;
	
	@GetMapping
	public List<Todo> list(@RequestParam(name="done", required = false) Boolean done) {
		if(done == null)
			return repo.findAll();
		return repo.findByDone(done);
	}
	
	@GetMapping("/{id}")
	public Todo get(@PathVariable(name="id") Long id) {
		return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Todo create(@RequestBody CreateReq req) {
		if(req.getText() == null || req.getText().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "text is required");
		}
		
		Todo t = new Todo();
		t.setText(req.getText().trim());
		t.setDone(false);
		
		return repo.save(t);
	}
	
	@PutMapping("/{id}")
	public Todo update(@PathVariable(name = "id") Long id, 
			@RequestBody UpdateReq req) {
		Todo t = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		if(req.text != null)
			t.setText(req.text.trim());
		if(req.done != null)
			t.setDone(req.done);
		return repo.save(t);
	}
	
	@PatchMapping("/{id}/toggle")
	public Todo toggle(@PathVariable(name = "id") Long id) {
		Todo t = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		t.setDone(!t.isDone());
		return repo.save(t);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(name = "id") Long id) {
		if(!repo.existsById(id))
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		repo.deleteById(id);
	}
	
	@Getter
	@Setter
	public static class CreateReq {
		@NotBlank
		private String text;
	}
	
	@Getter
	@Setter
	public static class UpdateReq {
		private String text;
		private Boolean done;
	}
}