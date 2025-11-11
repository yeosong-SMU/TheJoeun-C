package com.example.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Stud, Long> {
	// 모든 학생, 학고, 지도교수 조회
	@Query("select s.sname, s.grade, m.mname, p.pname " + 
			"from Stud s " + 
			"join s.major m " + 
			"join s.prof p")
	List<Object[]> findAllWithMajorAndProf();
	
	// 특정 학년 학생 조회 (grade가 0이면 전체)
	@Query("select s.sname, s.grade, m.mname, p.pname " + 
			"from Stud s " +
			"join s.major m " + 
			"join s.prof p " +
			"where (:grade = 0 or s.grade = :grade)")
	List<Object[]> findByGrade(@Param("grade") int grade);
	
	// 특정 교수 지도 학생 조회 (교수 이름 like 검색)
	@Query("select s.sname, s.grade, m.mname, p.pname " +
			"from Stud s " +
			"join s.major m " +
			"join s.prof p " +
			"where (:profName is null or p.pname like concat('%', :profName, '%'))")
	List<Object[]> findByProfName(@Param("profName") String profName);
}