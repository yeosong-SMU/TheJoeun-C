package com.example.mongodb.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public MemberDTO login(String userid, String passwd) {
		Query query = new Query(new Criteria("_id").is(userid).and("passwd").is(passwd));
		List<MemberDTO> items = mongoTemplate.find(query, MemberDTO.class, "member");
		if(items.size() > 0) {
			return items.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void join(MemberDTO dto) {
		mongoTemplate.insert(dto, "member");
	}
}