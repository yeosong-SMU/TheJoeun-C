package com.example.jpashop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.jpashop.dto.CartDTO;
import com.example.jpashop.entity.Cart;
import com.example.jpashop.entity.Member;
import com.example.jpashop.repository.CartRepository;
import com.example.jpashop.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/cart/*")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberReposioty;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("delete")
	public String delete(@RequestParam(name = "cartId") long cartId) {
		cartRepository.deleteById(cartId);
		return "redirect:/cart/list";
	}
	
	@Transactional
	@GetMapping("deleteAll")
	public String deleteAll(HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			return "redirect:/member/login";
		}
		
		Optional<Member> result = memberReposioty.findByUserid(userid);
		List<Cart> cartList = (List<Cart>) result.get().getCartList();
		for(Cart c : cartList) {
			cartRepository.deleteById(c.getCartId());
		}
		return "redirect:/cart/list";
	}
	
	@Transactional
	@PostMapping("update")
	public String update(@RequestParam(name = "amount") int[] amount, 
			@RequestParam(name = "cartId") long[] cartId, 
			@RequestParam(name = "productCode") int[] productCode, 
			HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null)
			return "redirect:/member/login";
		for(int i = 0; i<cartId.length; i++) {
			if(amount[i] == 0) {
				cartRepository.deleteById(cartId[i]);
			} else {
				CartDTO dto = new CartDTO();
				dto.setUserid(userid);
				dto.setCartId(cartId[i]);
				dto.setAmount(amount[i]);
				dto.setProductCode(productCode[i]);
				Cart c = modelMapper.map(dto,  Cart.class);
				c.setMember(new Member(userid));
				cartRepository.save(c);
			}
		}
		return "redirect:/cart/list";
	}
	
	@GetMapping("list")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		Map<String, Object> map = new HashMap<>();
		String userid = (String) session.getAttribute("userid");
		Optional<Member> result = memberReposioty.findByUserid(userid);
		if(result.isPresent()) {
			List<Cart> cartList = (List<Cart>) result.get().getCartList();
			int sumMoney = 0;
			for(Cart c : cartList) {
				sumMoney += c.getProduct().getPrice() * c.getAmount();
			}
			int fee = sumMoney >= 30000 ? 0 : 2500;
			map.put("sumMoney", sumMoney);
			map.put("fee", fee);
			map.put("sum", sumMoney + fee);
			map.put("list", cartList);
			map.put("count", cartList.size());
		} else {
			map.put("count", 0);
		}
		mav.setViewName("shop/cart_list");
		mav.addObject("map", map);
		return mav;
	}
	
	@Transactional
	@PostMapping("insert")
	public String insert(CartDTO dto, 
			HttpServletRequest request, 
			HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			String referer = request.getHeader("Referer");
			session.setAttribute("redirectURI", referer);
			return "redirect:/member/login";
		}
		Cart c = modelMapper.map(dto,  Cart.class);
		c.setMember(new Member(userid));
		cartRepository.save(c);
		return "redirect:/cart/list";
	}
}