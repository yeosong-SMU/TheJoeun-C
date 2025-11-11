package com.example.pdf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpRepository extends JpaRepository<Emp, Long> {
	@Query("""
			select e from Emp e
			left join fetch e.dept d
			order by d.deptno, e.empno
			""")
	List<Emp> findAllWithDept();
}