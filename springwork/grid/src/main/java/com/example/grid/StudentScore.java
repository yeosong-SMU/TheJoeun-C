package com.example.grid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentScore {
	@Id
	private String studentNum;
	
	private String studentName;
	
	private Integer kor;
	private Integer eng;
	private Integer math;
	
	//테이블에서 자동계산되므로 jpa에서 추가, 수정 금지 처리해야 함
	@Column(name = "tot", insertable = false, updatable = false)
	private Integer tot;
	
	//테이블에서 자동계산되므로 jpa에서 추가, 수정 금지 처리해야 함
	@Column(name = "avg", insertable = false, updatable = false)
	private Double avg;
	
	public void updateScores(String studentName, 
			Integer kor,
			Integer eng,
			Integer math) {
		this.studentName = studentName;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
	}
}