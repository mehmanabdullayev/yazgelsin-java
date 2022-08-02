package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.yazgelsin.ordering_system.model.Cart;
import com.yazgelsin.ordering_system.model.Store;
import com.yazgelsin.ordering_system.repository.CartRepository;
import com.yazgelsin.ordering_system.repository.StoreRepository;

@Controller
public class MainPageController {
	private StoreRepository storeRepository;
	private CartRepository cartRepository;
	
	@Autowired
	public MainPageController(StoreRepository repository1, CartRepository repository2) {
		this.storeRepository = repository1;
		this.cartRepository = repository2;
	}
	
	@GetMapping("/")
	public String mainPage(ModelMap model, HttpServletRequest request) {
		List<Store> stores = storeRepository.findByRankingGreaterThan(4);
		model.addAttribute("popularStores", stores);
		Cart cart = null;
		try { cart = cartRepository.findByBuyerId(request.getSession().getId()); }
		catch (Exception e) {}
		if (cart != null) model.addAttribute("cart", cart); 
		return "main_page";
	}
}
