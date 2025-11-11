package com.example.grid;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController   //json 리턴
@RequestMapping("/api/scores")
public class ApiController {
	@Autowired
	private StudentScoreRepository repo;
	
	@GetMapping
	public List<StudentScore> getAll() {
		return repo.findAll();
	}
	
	@PostMapping
	public StudentScore saveOrUpdate(@RequestBody StudentScore score) {
		if (score.getStudentNum() != null && repo.existsById(score.getStudentNum())) {
			StudentScore exist = repo.findById(score.getStudentNum()).get();
			exist.setStudentName(score.getStudentName());
			exist.setKor(score.getKor());
			exist.setEng(score.getEng());
			exist.setMath(score.getMath());
			exist.setTot(score.getTot());
			exist.setAvg(score.getAvg());
			return repo.save(exist);
		} else {
			if (score.getStudentNum() == null || score.getStudentNum().isEmpty()) {
				score.setStudentNum(UUID.randomUUID().toString());
			}
			return repo.save(score);
		}
	}
	
	@DeleteMapping("/{studentNum}")
	public void delete(@PathVariable("studentNum") String studentNum) {
		repo.deleteById(studentNum);
	}
	
	@PutMapping("/{studentNum}")
	public StudentScore update(@PathVariable("studentNum") String studentNum,
			@RequestBody StudentScore score) {
		if(!repo.existsById(studentNum))
			throw new RuntimeException("학생 없음");
		score.setStudentNum(studentNum);
		return repo.save(score);
	}
}