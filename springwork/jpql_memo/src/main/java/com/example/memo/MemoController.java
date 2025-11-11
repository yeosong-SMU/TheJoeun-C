package com.example.memo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemoController {
	@Autowired
	private MemoService memoService;
	
	@GetMapping
	public String listMemos(Model model, 
			@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name="type", required = false) String type) {
		List<Memo> memos;
		
		if(keyword != null && !keyword.isEmpty()) {
			switch(type) {
			case "title" :
				memos = memoService.searchByTitle(keyword);
				break;
			case "content":
				memos = memoService.searchByContent(keyword);
				break;
			case "titleOrContent":
				memos = memoService.searchByTitleOrContent(keyword);
				break;
			default:
				memos = memoService.getAllMemos();
			}
		} else {
			memos = memoService.getAllMemos();
		}
		
		model.addAttribute("memos", memos);
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		return "list";
	}
}