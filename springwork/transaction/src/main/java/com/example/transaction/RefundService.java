package com.example.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RefundService {
	@Autowired
	JdbcTemplate jdbc;
	
	public void refund(Long orderId, String reason) {
		jdbc.execute("{ call proc_refund_simple(?, ?) }", (CallableStatementCallback<Void>) cs -> {
			cs.setLong(1,  orderId);
			cs.setString(2, reason);
			cs.execute();
			return null;
		});
	}
}