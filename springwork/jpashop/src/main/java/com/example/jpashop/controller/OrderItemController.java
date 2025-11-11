package com.example.jpashop.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.jpashop.dto.OrderItemDTO;
import com.example.jpashop.entity.Cart;
import com.example.jpashop.entity.Member;
import com.example.jpashop.entity.OrderDetail;
import com.example.jpashop.entity.OrderItem;
import com.example.jpashop.entity.Product;
import com.example.jpashop.repository.CartRepository;
import com.example.jpashop.repository.MemberRepository;
import com.example.jpashop.repository.OrderDetailRepository;
import com.example.jpashop.repository.OrderItemRepository;
import com.example.jpashop.repository.ProductRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/order/*")
public class OrderItemController {
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@RequestMapping("write")
	public String write(@RequestParam(name="productCode", defaultValue="0") long productCode, 
			@RequestParam(name="amount", defaultValue="0") int amount, 
			Model model, HttpServletRequest request, 
			HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			String referer = request.getHeader("Referer");
			session.setAttribute("redirectURI", referer);
			return "redirect:/member/login";
		}
		Optional<Member> result = memberRepository.findByUserid(userid);
		if(result.isPresent()) {
			int sumMoney = 0;
			int delivery = 0;
			if(productCode > 0) {
				Optional<Product> opt = productRepository.findById(productCode);
				Product p = opt.get();
				sumMoney = p.getPrice() * amount;
				delivery = sumMoney >= 30000 ? 0 : 2500;
			} else {
				List<Cart> cartList = (List<Cart>) result.get().getCartList();
				for(Cart c : cartList) {
					sumMoney += c.getProduct().getPrice() * c.getAmount();
				}
				delivery = sumMoney >= 30000 ? 0 : 2500;
			}
			model.addAttribute("productCode", productCode);
			model.addAttribute("amount", amount);
			model.addAttribute("money", sumMoney);
			model.addAttribute("delivery", delivery);
			model.addAttribute("totalMoney", sumMoney + delivery);
			model.addAttribute("zipcode", result.get().getZipcode());
			model.addAttribute("address1",result.get().getAddress1());
			model.addAttribute("address2",result.get().getAddress2());
			model.addAttribute("tel", result.get().getTel());
		}
		return "shop/order_write";
	}
	
	@GetMapping("list")
	public String list(Model model, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		int level = (Integer) session.getAttribute("level");
		if(level == 10) {
			List<OrderItem> orderItemList = orderItemRepository.findAll();
			model.addAttribute("list", orderItemList);
		} else {
			Optional<Member> result = memberRepository.findByUserid(userid);
			if(result.isPresent()) {
				List<OrderItem> orderItemList = (List<OrderItem>) result.get().getOrderItemList();
				model.addAttribute("list", orderItemList);
			}
		}
		return "shop/order_list";
	}
	
	@GetMapping("process/{orderIdx}")
	public String process(@PathVariable(name="orderIdx") int orderIdx, 
			Model model, HttpSession session) {
		Optional<OrderItem> result = orderItemRepository.findById(orderIdx);
		if(result.isPresent()) {
			OrderItem order = result.get();
			List<OrderDetail> detailList = order.getDetailList();
			model.addAttribute("dto", order);
			model.addAttribute("detailList", detailList);
		}
		return "shop/order_success";
	}
	
	@GetMapping("detail/{orderIdx}")
	public String detail(@PathVariable(name="orderIdx") int orderIdx, 
			Model model, HttpSession session) {
		Optional<OrderItem> result = orderItemRepository.findById(orderIdx);
		if(result.isPresent()) {
			OrderItem order = result.get();
			List<OrderDetail> detailList = order.getDetailList();
			model.addAttribute("dto", order);
			model.addAttribute("detailList", detailList);
		}
		return "shop/order_detail";
	}
	
	@Transactional
	@PostMapping("insert")
	public String insert(OrderItemDTO dto, 
			@RequestParam(name="productCode", defaultValue = "0") int productCode, 
			@RequestParam(name="amount", defaultValue = "0") int amount, 
			HttpServletRequest request, HttpSession session) {
		String userid = (String) session.getAttribute("userid");
		if(userid == null) {
			String referer = request.getHeader("Referer");
			session.setAttribute("redirectURI", referer);
			return "redirect:/member/login";
		}
		OrderItem o = modelMapper.map(dto, OrderItem.class);
		o.setMember(new Member(userid));
		o.setOrderDate(new Date());
		o.setStatus("주문접수");
		OrderItem order = orderItemRepository.save(o);
		
		if(productCode > 0) {
			//상품목록에서 직접 주문한 경우
			OrderDetail od = new OrderDetail();
			od.setOrder(new OrderItem(order.getOrderIdx()));
			od.setProduct(new Product(productCode));
			od.setAmount(amount);
			orderDetailRepository.save(od);
		} else {
			//장바구니 목록에서 주문한 경우
			Optional<Member> result = memberRepository.findByUserid(userid);
			List<Cart> cartList = (List<Cart>) result.get().getCartList();
			for(Cart c : cartList) {
				OrderDetail od = new OrderDetail();
				od.setOrder(new OrderItem(order.getOrderIdx()));
				od.setProduct(new Product(c.getProduct().getProductCode()));
				od.setAmount(c.getAmount());
				orderDetailRepository.save(od);
				cartRepository.deleteById(c.getCartId());
			}
		}
		return "redirect:/order/process/" + order.getOrderIdx();
	}
	
	@Transactional
	@PostMapping("cancel/{orderIdx}")
	public String cancel(@PathVariable(name = "orderIdx") int orderIdx, 
			@RequestParam(name = "cancelReason") String cancelReason, 
			HttpSession session, Model model) {
		String userid = (String) session.getAttribute("userid");
		Optional<OrderItem> result = orderItemRepository.findById(orderIdx);
		OrderItem o = result.get();
		o.setMember(new Member(userid));
		o.setStatus("주문취소요청");
		o.setCancelReason(cancelReason);
		orderItemRepository.save(o);
		model.addAttribute("cancelReason", cancelReason);
		return "shop/order_cancel_success";
	}
	
	@PostMapping("cancel_process/{orderIdx}")
	public String cancel_process(@PathVariable(name="orderIdx") int orderIdx, 
			Model model, HttpSession session) {
		Optional<OrderItem> result = orderItemRepository.findById(orderIdx);
		if(result.isPresent()) {
			OrderItem order = result.get();
			String cancelReason = order.getCancelReason();
			model.addAttribute("cancelReason", cancelReason);
		}
		return "shop/cancel_success";
	}
	
	@GetMapping("delete/{orderIdx}")
	public String delete(@PathVariable(name="orderIdx") int orderIdx) {
		orderItemRepository.deleteById(orderIdx);
		return "redirect:/order/list";
	}
	
	@PostMapping("change_status")
	public String change_status(@RequestParam(name="orderIdx") int orderIdx, 
			@RequestParam(name="status") String status) {
		Optional<OrderItem> result = orderItemRepository.findById(orderIdx);
		OrderItem o = result.get();
		o.setStatus(status);
		orderItemRepository.save(o);
		return "redirect:/order/list";
	}
}