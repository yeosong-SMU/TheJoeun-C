package com.example.mongodb.memo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("memo/*")
public class MemoController {
	@Autowired
	MemoDAO memoDao;
	
	@GetMapping("/")
	public ModelAndView memo() {
		return new ModelAndView("memo/memo");
	}
	
	@GetMapping("list.do")
	public ModelAndView list() {
		Map<String, Object> map = new HashMap<>();
		List<MemoDTO> list = memoDao.list();
		map.put("items", list);
		return new ModelAndView("memo/list", "map", map);
	}
	
	@PostMapping("insert.do")
	public String memo_insert(MemoDTO dto) {
		memoDao.insert(dto);
		return "redirect:/memo/";
	}
	
	@GetMapping("view.do")
	public ModelAndView memo_view(@RequestParam(name="_id") String _id) {
		MemoDTO dto = memoDao.detail(_id);
		return new ModelAndView("memo/view", "dto", dto);
	}
	
	@PostMapping("update.do")
	public String memo_update(@RequestParam(name="_id") String _id, 
			@RequestParam(name = "writer") String writer, 
			@RequestParam(name = "memo") String memo) {
		MemoDTO dto = new MemoDTO();
		dto.set_id(_id);
		dto.setWriter(writer);
		dto.setMemo(memo);
		memoDao.update(dto);
		return "redirect:/memo/";
	}
	
	@PostMapping("delete.do")
	public String memo_delete(@RequestParam(name="_id") String _id) {
		memoDao.delete(_id);
		return "redirect:/memo/";
	}
}