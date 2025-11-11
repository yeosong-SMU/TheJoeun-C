package com.example.jpashop.api;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jpashop.dto.CartDTO;
import com.example.jpashop.entity.Cart;
import com.example.jpashop.entity.Member;
import com.example.jpashop.entity.Product;
import com.example.jpashop.repository.CartRepository;
import com.example.jpashop.repository.MemberRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/cart/*")
public class CartApi {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberReposioty;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("delete/{cartId}")
	public void delete(@PathVariable(name = "cartId") long cartId) {
		cartRepository.deleteById(cartId);
	}
	
	@Transactional
	@GetMapping("deleteAll")
	public void deleteAll(@RequestParam(name = "userid") String userid) {
		Optional<Member> result = memberReposioty.findByUserid(userid);
		List<Cart> cartList = (List<Cart>) result.get().getCartList();
		for(Cart c : cartList) {
			cartRepository.deleteById(c.getCartId());
		}
	}
	
	@Transactional
	@PostMapping("update")
	public void update(@RequestParam(name = "userid") String userid, 
			@RequestParam(name = "amount") int amount, 
			@RequestParam(name = "cartId") long cartId, 
			@RequestParam(name = "productCode") int productCode) {
		if(amount == 0) {
			cartRepository.deleteById(cartId);
		} else {
			CartDTO dto = new CartDTO();
			dto.setUserid(userid);
			dto.setCartId(cartId);
			dto.setAmount(amount);
			dto.setProductCode(productCode);
			Cart c = modelMapper.map(dto,  Cart.class);
			c.setCartId(cartId);
			c.setMember(new Member(userid));
			c.setProduct(new Product(productCode));
			c.setAmount(amount);
			cartRepository.save(c);
		}
	}
}