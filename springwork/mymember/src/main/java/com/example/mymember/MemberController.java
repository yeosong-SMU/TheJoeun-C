package com.example.mymember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
	@Autowired
	MemberDAO memberDao;
	
	@GetMapping("/")
	public String memberList(Model model) {
		List<MemberDTO> list = memberDao.list();
		model.addAttribute("list", list);
		return "list";
	}
	
	@GetMapping("write.do")
	public String write() {
		return "write";
	}
	
	@PostMapping("insert.do")
	public String insert(@ModelAttribute MemberDTO dto) {
		memberDao.insert(dto);
		return "redirect:/";
	}
	
	@GetMapping("view.do")
	public String view(@RequestParam(name="userid") String userid, Model model) {
		model.addAttribute("dto", memberDao.detail(userid));
		return "detail";
	}
	
	@PostMapping("update.do")
	public String update(@ModelAttribute MemberDTO dto, Model model) {
		boolean result = memberDao.check_passwd(dto.getUserid(), dto.getPasswd());
		if(result) {
			memberDao.update(dto);
			return "redirect:/";
		} else {
			MemberDTO dto2 = memberDao.detail(dto.getUserid());
			dto.setJoin_date(dto2.getJoin_date());
			model.addAttribute("dto", dto);
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			return "detail";
		}
	}
	
	@PostMapping("delete.do")
	public String delete(
			@RequestParam(name="userid") String userid, 
			@RequestParam(name="passwd") String passwd, 
			Model model) {
		boolean result = memberDao.check_passwd(userid, passwd);
		if(result) {
			memberDao.delete(userid);
			return "redirect:/";
		} else {
			model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("dto", memberDao.detail(userid));
			return "detail";
		}
	}
}
