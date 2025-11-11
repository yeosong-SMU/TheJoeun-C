package com.example.org;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleTypes;

@Service
@RequiredArgsConstructor
public class ProcService {
	private final DataSource dataSource;
	
	private SimpleJdbcCall callFullOrg() {
		return new SimpleJdbcCall(new JdbcTemplate(dataSource))
				.withProcedureName("proc_emp_full_org")
				.declareParameters(
						new SqlOutParameter("o_rc", 
								OracleTypes.CURSOR, 
								new ColumnMapRowMapper())
				);
	}
	
	private SimpleJdbcCall callSubtree() {
		return new SimpleJdbcCall(new JdbcTemplate(dataSource))
				.withProcedureName("proc_emp_subtree")
				.declareParameters(
						new SqlOutParameter("o_rc", 
								OracleTypes.CURSOR, 
								new ColumnMapRowMapper())
				);
	}
	
	private SimpleJdbcCall callChainToCeo() {
		return new SimpleJdbcCall(new JdbcTemplate(dataSource))
				.withProcedureName("proc_emp_chain_to_ceo")
				.declareParameters(
						new SqlOutParameter("o_rc", 
								OracleTypes.CURSOR, 
								new ColumnMapRowMapper())
				);
	}
	
	private SimpleJdbcCall callSubtreeSalSum() {
		return new SimpleJdbcCall(new JdbcTemplate(dataSource))
				.withProcedureName("proc_emp_subtree_sal_sum")
				.declareParameters(
						new SqlOutParameter("o_rc", 
								OracleTypes.CURSOR, 
								new ColumnMapRowMapper())
				);
	}
	
	private SimpleJdbcCall callLeaves() {
		return new SimpleJdbcCall(new JdbcTemplate(dataSource))
				.withProcedureName("proc_emp_leaves")
				.declareParameters(
						new SqlOutParameter("o_rc", 
								OracleTypes.CURSOR, 
								new ColumnMapRowMapper())
				);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFullOrg() {
		Map<String, Object> out = callFullOrg().execute();
		return (List<Map<String, Object>>) out.get("o_rc");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSubtree(Number mgrEmpno) {
		Map<String, Object> out = callSubtree().execute(Map.of("p_mgr_empno", mgrEmpno));
		return (List<Map<String, Object>>) out.get("o_rc");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getChainToCeo(Number empno) {
		Map<String, Object> out = callChainToCeo().execute(Map.of("p_empno", empno));
		return (List<Map<String, Object>>) out.get("o_rc");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSubtreeSalSum() {
		Map<String, Object> out = callSubtreeSalSum().execute();
		return (List<Map<String, Object>>) out.get("o_rc");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getLeaves() {
		Map<String, Object> out = callLeaves().execute();
		return (List<Map<String, Object>>) out.get("o_rc");
	}
}