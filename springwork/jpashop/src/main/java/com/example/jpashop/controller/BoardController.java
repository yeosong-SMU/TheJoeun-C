package com.example.jpashop.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.jpashop.dto.BoardDTO;
import com.example.jpashop.entity.Attach;
import com.example.jpashop.entity.Board;
import com.example.jpashop.entity.Member;
import com.example.jpashop.repository.AttachRepository;
import com.example.jpashop.repository.BoardRepository;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.ReplyRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	ReplyRepository replyRepository;
	
	@Autowired
	AttachRepository attachRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("write")
	public String write(HttpSession session, Model model) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			model.addAttribute("message", "nologin");
			return "member/login";
		} else {
			return "board/write";
		}
	}
	
	@Transactional
	@PostMapping("insert")
	public String insert(BoardDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		dto.setUserid(userid);
		Board b = modelMapper.map(dto, Board.class);
		b.setMember(new Member(userid));
		b.setRegdate(new Date());
		Board b2 = boardRepository.save(b);
		int idx = b2.getIdx();
		String[] files = dto.getFiles();
		if(files != null) {
			for (String name : files) {
				Attach attach = new Attach();
				attach.setFileName(name);
				attach.setBoard(new Board(idx));
				attach.setRegdate(new Date());
				attachRepository.save(attach);
			}
		}
		return "redirect:/board/list";
	}
	
	@RequestMapping("list")
	public ModelAndView list(Pageable pageable, 
			@RequestParam(name="search_option", defaultValue="title") String search_option, 
			@RequestParam(name="keyword", defaultValue = "") String keyword) {
		int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
		pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "idx");
		Page<Board> list = null;
		switch (search_option) {
		case "all" : 
			list = boardRepository.findByAll(keyword, pageable);
			break;
		case "name" : 
			list = boardRepository.findByKeyword(keyword, pageable);
			break;
		case "contents" : 
			list = boardRepository.findByContentsContaining(keyword, pageable);
			break;
		case "title" :
			list = boardRepository.findByTitleContaining(keyword, pageable);
			break;
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/list");
		mav.addObject("boardList", list);
		mav.addObject("count", list.getTotalElements());
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		mav.addObject("curPage", (page + 1));
		mav.addObject("previous", pageable.previousOrFirst().getPageNumber());
		mav.addObject("next", pageable.next().getPageNumber());
		int start = (int) (Math.floor(list.getNumber() / 10) * 10 + 1);  //페이지번호
		int last = start + 9 < list.getTotalPages() ? start + 9 : list.getTotalPages();
		mav.addObject("start", start);
		mav.addObject("last", last);
		return mav;
	}
	
	@GetMapping("detail")
	public ModelAndView detail(@RequestParam(name = "idx") int idx, 
			@RequestParam(name = "cur_page", defaultValue = "0") int cur_page, 
			@RequestParam(name = "search_option", defaultValue = "0") String search_option, 
			@RequestParam(name = "keyword", defaultValue = "0") String keyword, 
			HttpSession session) {
		Optional<Board> result = boardRepository.findById(idx);
		Board b = result.get();
		
		long read_time = 0;
		if(session.getAttribute("read_time_" + idx) != null) {
			read_time = (long) session.getAttribute("read_time_" + idx);
		}
		long current_time = System.currentTimeMillis();
		if(current_time - read_time > 5 * 1000) {
			b.setHit(b.getHit() + 1);
			boardRepository.save(b);
			session.setAttribute("read_time_" + idx, current_time);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/view");
		mav.addObject("dto", b);
		mav.addObject("cur_page", cur_page);
		mav.addObject("search_option", search_option);
		mav.addObject("keyword", keyword);
		return mav;
	}
	
	@PostMapping("update")
	public String update(BoardDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		Board b = modelMapper.map(dto,  Board.class);
		b.setMember(new Member(userid));
		b.setRegdate(new Date());
		Board b2 = boardRepository.save(b);
		int idx  = b2.getIdx();
		String[] files = dto.getFiles();
		if(files != null) {
			for(String name : files) {
				Attach attach = new Attach();
				attach.setFileName(name);
				attach.setBoard(new Board(idx));
				attach.setRegdate(new Date());
				attachRepository.save(attach);
			}
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam(name = "idx") int idx) {
		int count_reply = replyRepository.countByBoardIdx(idx);
		if(count_reply > 0) {
			return "redirect:/board/detail?idx=" + idx + "&msg=error_reply";
		}
		int count_attach = attachRepository.countByBoardIdx(idx);
		if(count_attach > 0) {
			return "redirect:/board/detail?idx=" + idx + "&msg = error_attach";
		}
		boardRepository.deleteById(idx);
		return "redirect:/board/list";
	}
	
	@GetMapping("list_attach/{idx}")
	@ResponseBody
	public List<String> list_attach(@PathVariable(name = "idx") int idx) {
		Optional<Board> result = boardRepository.findById(idx);
		List<Attach> list = result.get().getAttachList();
		List<String> items = new ArrayList<>();
		for(Attach a : list) {
			items.add(a.getFileName());
		}
		return items;
	}
}