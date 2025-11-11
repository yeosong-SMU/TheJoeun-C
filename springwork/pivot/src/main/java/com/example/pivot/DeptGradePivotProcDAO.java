package com.example.pivot;

import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class DeptGradePivotProcDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall call;
	
	// bean이 생성된 후 자동 호출
	@PostConstruct
	void init() {
		this.call = new SimpleJdbcCall(jdbcTemplate)
				.withProcedureName("proc_dept_grade_pivot")
				.declareParameters(new SqlOutParameter("o_cursor", Types.REF_CURSOR))
				.returningResultSet("o_cursor", 
						(rs, rowNum) -> new DeptGradePivotRow(rs.getString("DEPT"),
						rs.getInt("G1"), rs.getInt("G2"), 
						rs.getInt("G3"), rs.getInt("G4")));
	}
	
	public List<DeptGradePivotRow> fetch() {
		Map<String, Object> out = call.execute(Collections.emptyMap());
		List<DeptGradePivotRow> list = (List<DeptGradePivotRow>) out.get("o_cursor");
		return list;
	}
}