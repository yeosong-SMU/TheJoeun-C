package com.example.unpivot;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class ScoreUnpivotDAO {
	@Autowired
	JdbcTemplate jdbc;
	
	private SimpleJdbcCall call;
	
	@PostConstruct
	void init() {
		this.call = new SimpleJdbcCall(jdbc)
				.withProcedureName("proc_score_unpivot")
				.declareParameters(new SqlOutParameter("o_cursor", Types.REF_CURSOR))
				.returningResultSet("o_cursor", (rs, rowNum) -> {
					Number n = (Number) rs.getObject("score");
					Integer score = (n == null) ? null : n.intValue();
					
					return new ScoreUnpivotRow(
							rs.getLong("student_id"), 
							rs.getString("subject"), 
							score);
				});
	}
	
	public List<ScoreUnpivotRow> findAll() {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("p_student_id", null, Types.NUMERIC);
		List<ScoreUnpivotRow> list = (List<ScoreUnpivotRow>) call.execute(params)
				.get("o_cursor");
		return list;
	}
	
	public List<ScoreUnpivotRow> findByStudentId(long studentId) {
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("p_student_id", studentId, Types.NUMERIC);
		List<ScoreUnpivotRow> list = (List<ScoreUnpivotRow>) call.execute(params)
				.get("o_cursor");
		return list;
	}
}