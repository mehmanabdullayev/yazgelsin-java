package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazgelsin.ordering_system.model.Cart;
import com.yazgelsin.ordering_system.model.CartDetail;
import com.yazgelsin.ordering_system.model.Order;
import com.yazgelsin.ordering_system.model.Store;
import com.yazgelsin.ordering_system.repository.CartDetailRepository;
import com.yazgelsin.ordering_system.repository.CartRepository;
import com.yazgelsin.ordering_system.repository.OrderRepository;

@Controller
public class OrderController {
	private OrderRepository orderRepository;
	private CartRepository cartRepository;
	private CartDetailRepository cartDetailRepository;
	
	@Autowired
	public OrderController(OrderRepository repository1, CartRepository repository2, CartDetailRepository repository3) {
		this.orderRepository = repository1;
		this.cartRepository = repository2;
		this.cartDetailRepository = repository3;
	}
	
	@PostMapping("/order")
	public String order() {
		return "sifaris";
	}
	
	@PostMapping("/complete_order")
	public String completeOrder(HttpServletRequest request, @RequestParam(value = "fullname") String fullname, @RequestParam(value = "phone") String phone, @RequestParam(value = "address") String address) {
		Cart cart = null;
		try { cart = cartRepository.findByBuyerId(request.getSession().getId()); }
		catch (Exception e) {}
		if (cart != null) {
			for (CartDetail d : cartDetailRepository.findByCart(cart)) {
				Order order = new Order();
				order.setBuyerFullname(fullname);
				order.setBuyerAddress(address);
				order.setBuyerPhone(Long.valueOf(phone));
				order.setBuyerId(request.getSession().getId());
				order.setStore(d.getProduct().getContainer().getStore());
				order.setPrice(d.getProduct().getPrice());
				order.setQuantity(d.getQuantity());
				order.setCartDetail(d);
				orderRepository.save(order);
			}
		}
		return "order_filled";
	}
	
	@GetMapping("/order_completed")
	public String orderCompleted(HttpServletRequest request, @RequestParam(value = "id") String id) {
		Order order = orderRepository.getReferenceById(Long.valueOf(id));
		Store store = (Store) request.getSession().getAttribute("store");
		if (order.getStore().getId() == store.getId()) 
			order.setFilled(true);
			orderRepository.save(order);
		return "redirect:/magazam";
	}
}


