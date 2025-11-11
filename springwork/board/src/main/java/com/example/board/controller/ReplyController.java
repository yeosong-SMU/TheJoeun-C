package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.model.ReplyDAO;
import com.example.board.model.ReplyDTO;
import com.example.board.service.PageUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/reply/*")
public class ReplyController {
	@Autowired
	ReplyDAO replyDao;
	
	@PostMapping("insert.do")
	public void insert(ReplyDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		dto.setReplyer(userid);
		replyDao.insert(dto);
	}
	
	@GetMapping("/delete/{idx}")
	public ResponseEntity<String> delete(@PathVariable(name = "idx") int idx) {
		ResponseEntity<String> entity = null;
		try {
			replyDao.delete(idx);
			entity = new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@GetMapping("/detail/{idx}")
	public ModelAndView detail(@PathVariable(name = "idx") int idx, ModelAndView mav) {
		ReplyDTO dto = replyDao.detail(idx);
		mav.setViewName("board/reply_detail");
		mav.addObject("dto", dto);
		return mav;
	}
	
	@GetMapping("list.do")
	public ModelAndView list(@RequestParam(name = "board_idx") int board_idx, 
			@RequestParam(name="curPage", defaultValue = "1") int curPage, 
			ModelAndView mav) {
		int count = replyDao.count(board_idx);
		PageUtil page_info = new PageUtil(count, curPage);
		int start = page_info.getPageBegin();
		int end = page_info.getPageEnd();
		List<ReplyDTO> list = replyDao.list(board_idx, start, end);
		mav.setViewName("board/reply_list");
		mav.addObject("list", list);
		mav.addObject("page_info", page_info);
		return mav;
	}
	
	@GetMapping("list_json.do")
	public @ResponseBody List<ReplyDTO> list_json(@RequestParam(name="curPage", defaultValue = "1") int curPage, 
			@RequestParam(name = "idx") int idx) {
		int count = replyDao.count(idx);
		PageUtil page_info = new PageUtil(count, curPage);
		int start = page_info.getPageBegin();
		int end = page_info.getPageEnd();
		List<ReplyDTO> list = replyDao.list(idx, start, end);
		return list;
	}
	
	@PostMapping("/update/{idx}")
	public ResponseEntity<String> update(@PathVariable(name="idx") int idx, 
			@RequestBody ReplyDTO dto) {
		ResponseEntity<String> entity = null;
		try {
			dto.setIdx(idx);
			replyDao.update(dto);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}