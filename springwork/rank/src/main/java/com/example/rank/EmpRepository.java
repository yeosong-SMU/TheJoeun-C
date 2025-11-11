package com.example.rank;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Long> {
	// byDept - 0 전체, 1 부서별
	// dept - null=전체부서, 값=특정부서
	@Query(value = """
			select
			e.emp_id 	as empId,
			e.dept_no	as deptNo,
			e.ename		as ename,
			e.salary	as salary,
			rank() over(
				partition by (case when :byDept = 1
					then e.dept_no else 1 end)
				order by e.salary desc
			) as rnk,
			dense_rank() over (
				partition by (case when :byDept = 1
					then e.dept_no else 1 end)
				order by e.salary desc
			) as drnk
			from emp e
			where (:dept is null or e.dept_no = :dept)
			order by
				case when :byDept = 1 then e.dept_no else 0 end,
				rnk, e.emp_id
			""", nativeQuery = true)
	List<EmpRankView> findRanks(@Param("byDept") int byDept,
			@Param("dept") Integer dept);
	
	@Query("select distinct e.deptNo from Emp e order by e.deptNo")
	List<Integer> findDistinctDeptNos();
}