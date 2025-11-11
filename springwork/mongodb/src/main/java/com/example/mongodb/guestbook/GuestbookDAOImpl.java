package com.example.mongodb.guestbook;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class GuestbookDAOImpl implements GuestbookDAO {
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public List<GuestbookDTO> list() {
		Query query = new Query().with(Sort.by(Direction.DESC, "post_date"));
		List<GuestbookDTO> items = mongoTemplate.find(query, GuestbookDTO.class, "guestbook");
		for(GuestbookDTO dto : items) {
			String contents = dto.getContents();
			contents = contents.replace("  ", "&nbsp;&nbsp;");
			contents = contents.replace("<", "&lt;");
			contents = contents.replace(">", "&gt;");
			contents = contents.replace("\n", "<br>");
			dto.setContents(contents);
		}
		return items;
	}

	@Override
	public void insert(GuestbookDTO dto) {
		dto.setPost_date(new Date());
		mongoTemplate.insert(dto, "guestbook");
	}

	@Override
	public void update(GuestbookDTO dto) {
		Query query = new Query(new Criteria("_id").is(dto.get_id()));
		Update update = new Update();
		update.set("name", dto.getName());
		update.set("email", dto.getEmail());
		update.set("contents", dto.getContents());
		mongoTemplate.updateFirst(query, update, "guestbook");
	}

	@Override
	public void delete(String _id) {
		Query query = new Query(new Criteria("_id").is(_id));
		mongoTemplate.remove(query, "guestbook");
	}

	@Override
	public GuestbookDTO detail(String _id) {
		return mongoTemplate.findById(_id, GuestbookDTO.class, "guestbook");
	}
}