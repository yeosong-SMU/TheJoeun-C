package com.example.grid;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor   //생성자 자동 생성해줌
public class StudentScoreService {
	private final StudentScoreRepository repo;
	
	@Transactional   //DML 레코드 추가/수정/삭제
	public StudentScore save(StudentScore studentScore) {
		return repo.save(studentScore);
	}
	
	//읽기 전용 트랜잭션
	@Transactional(readOnly = true)  //변경사항이 없구나! 속도 빨라짐
	public List<StudentScore> findAllScores() {
		return repo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<StudentScore> findByStudentNum(String studentNum) {
		return repo.findById(studentNum);
	}
	
	@Transactional
	public StudentScore updateScores(String studentNum, 
			String studentName, 
			Integer kor,
			Integer eng,
			Integer math) {
		StudentScore studentScore = repo.findById(studentNum)
				.orElseThrow(() -> new IllegalArgumentException("해당 학번(" + studentNum + ")의 학생을 찾을 수 없습니다."));
		
		studentScore.updateScores(studentName, kor, eng, math);
		
		return repo.save(studentScore);
	}
	
	@Transactional
	public void deleteByStudentNum(String studentNum) {
		repo.deleteById(studentNum);
	}
}