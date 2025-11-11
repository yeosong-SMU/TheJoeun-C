package com.example.procedure;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemoService {
	private final DataSource dataSource;
	
	public void addMemo(String content) {
		new SimpleJdbcCall(dataSource)
		.withProcedureName("proc_memo_insert")    //프로시저이름
		.execute(Map.of("p_content", content));   //(변수명, 전달값)
	}
	
	public List<Memo> listMemos() {
		var call = new SimpleJdbcCall(dataSource)
				.withProcedureName("proc_memo_list")
				.returningResultSet("o_cursor",   //커서변수명 
						(rs, n) -> {   //(레코드셋의 시작주소, 레코드 개수)
							Memo m = new Memo();
							m.setId(rs.getLong("ID"));
							m.setContent(rs.getString("CONTENT"));
							m.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
							return m;
						});
		Map<String, Object> out = call.execute();
		return (List<Memo>) out.get("o_cursor");
	}
	
	public Memo getMemo(Long id) {
		var call = new SimpleJdbcCall(dataSource)
				.withProcedureName("proc_memo_get")
				.returningResultSet("o_cursor", 
						(rs, n) -> {
							Memo m = new Memo();
							m.setId(rs.getLong("ID"));
							m.setContent(rs.getString("CONTENT"));
							m.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
							return m;
						});
		Map<String, Object> out = call.execute(Map.of("p_id", id));
		var list = (List<Memo>) out.get("o_cursor");
		if (list.isEmpty())
			throw new IllegalArgumentException("메모가 없습니다.");
		return list.get(0);
	}
	
	public void updateMemo(Long id, String content) {
		new SimpleJdbcCall(dataSource)
		.withProcedureName("proc_memo_update")
		.execute(Map.of("p_id", id, "p_content", content));
	}
	
	public void deleteMemo(Long id) {
		new SimpleJdbcCall(dataSource)
		.withProcedureName("proc_memo_delete")
		.execute(Map.of("p_id", id));
	}
}