package com.example.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.model.BoardDTO;
import com.example.board.model.ReplyDAO;
import com.example.board.service.BoardService;
import com.example.board.service.PageUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@Autowired
	ReplyDAO replyDao;
	
	@GetMapping("write.do")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("insert.do")
	public String insert(BoardDTO dto, HttpSession session) {
		String writer = (String) session.getAttribute("userid");
		dto.setWriter(writer);
		boardService.insert(dto);
		return "redirect:/board/list.do";
	}
	
	@RequestMapping("list.do")
	public ModelAndView list(@RequestParam(name = "curPage", defaultValue = "1") int curPage, 
			@RequestParam(name = "search_option", defaultValue = "all") String search_option,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		int count = boardService.count(search_option, keyword);
		PageUtil page_info = new PageUtil(count, curPage);
		int start = page_info.getPageBegin();
		int end = page_info.getPageEnd();
		List<BoardDTO> list = boardService.list(start, end, search_option, keyword);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list");
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("count", count);
		map.put("search_option", search_option);
		map.put("keyword", keyword);
		map.put("page_info", page_info);
		mav.addObject("map", map);
		return mav;
	}
	
	@GetMapping("detail.do")
	public ModelAndView detail(@RequestParam(name = "idx") int idx, 
			@RequestParam(name = "cur_page") int cur_page, 
			@RequestParam(name = "search_option") String search_option,
			@RequestParam(name = "keyword") String keyword) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/view");
		mav.addObject("dto", boardService.detail(idx));
		mav.addObject("count", replyDao.count(idx));
		mav.addObject("cur_page", cur_page);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		return mav;
	}
	
	@PostMapping("update.do")
	public String update(BoardDTO dto) {
		boardService.update(dto);
		return "redirect:/board/list.do";
	}
	
	@PostMapping("delete.do")
	public String delete(@RequestParam(name = "idx") int idx) {
		boardService.delete(idx);
		return "redirect:/board/list.do";
	}
	
	@PostMapping("list_attach/{idx}")
	@ResponseBody
	public List<String> list_attach(@PathVariable(name = "idx") int idx) {
		return boardService.list_attach(idx);
	}
}