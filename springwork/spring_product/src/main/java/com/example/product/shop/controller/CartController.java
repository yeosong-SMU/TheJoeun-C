package com.example.product.shop.controller;

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

import com.example.product.shop.model.CartDAO;
import com.example.product.shop.model.CartDTO;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/shop/cart/*")
public class CartController {
	@Autowired
	CartDAO cartDao;
	
	@GetMapping("delete.do")
	public String delete(@RequestParam(name = "cart_id") int cart_id) {
		cartDao.delete(cart_id);
		return "redirect:/shop/cart/list.do";
	}
	
	@GetMapping("deleteAll.do")
	public String deleteAll(HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid != null) {
			cartDao.delete_all(userid);
		}
		return "redirect:/shop/cart/list.do";
	}
	
	@PostMapping("update.do")
	public String update(@RequestParam(name = "amount") int[] amount, @RequestParam(name = "cart_id") int[] cart_id, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			return "redirect:/member/login.do";
		}
		for(int i = 0; i< cart_id.length; i++) {
			if(amount[i] == 0) {
				cartDao.delete(cart_id[i]);
			} else {
				CartDTO dto = new CartDTO();
				dto.setUserid(userid);
				dto.setCart_id(cart_id[i]);
				dto.setAmount(amount[i]);
				cartDao.modify(dto);
			}
		}
		return "redirect:/shop/cart/list.do";
	}
	
	@GetMapping("list.do")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		Map<String, Object> map = new HashMap<> ();
		String userid = (String) session.getAttribute("userid");
		
		if(userid == null) {
			mav.setViewName("redirect:/member/login.do");
			return mav;
		}
		
		List<CartDTO> list = cartDao.list(userid);
		int sumMoney = cartDao.sum_money(userid);
		int fee = sumMoney >= 30000 ? 0 : 2500;
		map.put("sumMoney", sumMoney);
		map.put("fee", fee);
		map.put("sum", sumMoney + fee);
		map.put("list", list);
		map.put("count", list.size());
		mav.setViewName("shop/cart_list");
		mav.addObject("map", map);
		return mav;
	}
	
	@RequestMapping("insert.do")
	public String isnert(CartDTO dto, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			return "redirect:/member/login.do";
		}
		dto.setUserid(userid);
		cartDao.insert(dto);
		return "redirect:/shop/cart/list.do";
	}
}
