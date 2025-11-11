package com.example.mongodb.guestbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("guestbook/*")
public class GuestbookController {
	@Autowired
	GuestbookDAO guestbookDao;
	
	@GetMapping("/")
	public ModelAndView guestbook() {
		return new ModelAndView("guestbook/index");
	}
	
	@GetMapping("list.do")
	public ModelAndView list() {
		List<GuestbookDTO> items = guestbookDao.list();
		Map<String, Object> map = new HashMap<>();
		map.put("list", items);
		map.put("count", items.size());
		return new ModelAndView("guestbook/list", "map", map);
	}
	
	@GetMapping("edit.do")
	public ModelAndView edit(@RequestParam(name = "_id") String _id) {
		GuestbookDTO dto = guestbookDao.detail(_id);
		return new ModelAndView("guestbook/edit", "dto", dto);
	}
	
	@GetMapping("write.do")
	public ModelAndView write() {
		return new ModelAndView("guestbook/write");
	}
	
	@PostMapping("insert.do")
	public String insert(@RequestParam(name = "name") String name, 
			@RequestParam(name = "email") String email, 
			@RequestParam(name = "contents") String contents) {
		if(name == null || name.equals("")) {
			return "redirect:/write.do";
		}
		GuestbookDTO dto = new GuestbookDTO();
		dto.setName(name);
		dto.setEmail(email);
		dto.setContents(contents);
		guestbookDao.insert(dto);
		return "redirect:/guestbook/";
	}
	
	@PostMapping("/update.do")
	public String update(@RequestParam(name = "_id") String _id, 
			@ModelAttribute GuestbookDTO dto) {
		dto.set_id(_id);
		guestbookDao.update(dto);
		return "redirect:/guestbook/";
	}
	
	@PostMapping("/delete.do")
	public String delete(@RequestParam(name = "_id") String _id) {
		guestbookDao.delete(_id);
		return "redirect:/guestbook/";
	}
}