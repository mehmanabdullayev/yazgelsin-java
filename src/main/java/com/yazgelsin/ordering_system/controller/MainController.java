package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.yazgelsin.ordering_system.model.Careers;
import com.yazgelsin.ordering_system.model.Cart;
import com.yazgelsin.ordering_system.model.Complaint;
import com.yazgelsin.ordering_system.model.Store;
import com.yazgelsin.ordering_system.repository.CareersRepository;
import com.yazgelsin.ordering_system.repository.CartRepository;
import com.yazgelsin.ordering_system.repository.ComplaintRepository;
import com.yazgelsin.ordering_system.repository.StoreRepository;

@Controller
public class MainController {
	private StoreRepository storeRepository;
	private CartRepository cartRepository;
	private CareersRepository careersRepository;
	private ComplaintRepository complaintRepository;
	
	@Autowired
	public MainController(StoreRepository repository1, CartRepository repository2, CareersRepository repository3, ComplaintRepository repository4) {
		this.storeRepository = repository1;
		this.cartRepository = repository2;
		this.careersRepository = repository3;
		this.complaintRepository = repository4;
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
	
	@GetMapping("/kuryer")
	public String careers(ModelMap model) {
		model.addAttribute("newCourier", new Careers());
		return "careers";
	}
	
	@PostMapping("/newCourier")
	public String newCourier(ModelMap model, @ModelAttribute("newCourier") Careers careers, BindingResult result) {
		if (!result.hasErrors()) 
			careersRepository.save(careers);
		 	model.addAttribute("success", true);
		return "careers";
	}
	
	@GetMapping("/müştəri_xidmətləri")
	public String customerService(ModelMap model) {
		model.addAttribute("newComplaint", new Complaint());
		return "help";
	}
	
	@PostMapping("/newComplaint")
	public String newComplaint(ModelMap model, @ModelAttribute("newComplaint") Complaint complaint, BindingResult result) {
		if (!result.hasErrors()) 
			complaintRepository.save(complaint);
		 	model.addAttribute("success", true);
		return "help";
	}
	
	@GetMapping("/haqqımızda")
	public String about() {
		return "about";
	}
}


