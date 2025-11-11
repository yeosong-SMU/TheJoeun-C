package com.example.todo;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TodoDAOImpl implements TodoDAO {
	@Autowired
	SqlSession session;
	
	@Override
	public List<TodoDTO> list(String title) {
		List<TodoDTO> items = session.selectList("todo.list", "%"+title+"%");
		for(int i = 0; i < items.size(); i++) {
			TodoDTO dto = items.get(i);
			String description = dto.getDescription();
			description = description.replace("<", "&lt;");
			description = description.replace(">", "&gt;");
			description = description.replace("  ", "&nbsp;&nbsp;");
			description = description.replace("\n", "<br>");
			dto.setDescription(description);
		}
		return items;
	}

	@Override
	public void insert(TodoDTO dto) {
		session.insert("todo.insert", dto);
	}

	@Override
	public TodoDTO detail(int idx) {
		return session.selectOne("todo.detail", idx);
	}

	@Override
	public void update(TodoDTO dto) {
		session.update("todo.update", dto);
	}

	@Override
	public void change_status(TodoDTO dto) {
		session.update("todo.change_status", dto);

	}

	@Override
	public void delete(int idx) {
		session.delete("todo.delete", idx);
	}

	@Override
	public int count() {
		return session.selectOne("todo.count");
	}

}
