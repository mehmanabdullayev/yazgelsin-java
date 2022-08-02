package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.yazgelsin.ordering_system.model.Container;
import com.yazgelsin.ordering_system.model.Store;
import com.yazgelsin.ordering_system.repository.ContainerRepository;
import com.yazgelsin.ordering_system.repository.StoreRepository;

@Controller
public class StoreController {
	private StoreRepository storeRepository;
	private ContainerRepository containerRepository;
	
	@Autowired
	public StoreController(StoreRepository repository1, ContainerRepository repository2) {
		this.storeRepository = repository1;
		this.containerRepository = repository2;
	}
	
	@GetMapping("/yeni_mağaza")
	public String newStore(ModelMap model) {
		model.addAttribute("createStore", new Store());
		return "create_store";
	}
	
	@PostMapping("/newStore")
	public String createStore(HttpServletRequest request, @RequestParam(value = "image") MultipartFile file, ModelMap model, @ModelAttribute("createStore") @Valid Store store, BindingResult result) throws IOException {
		store.setPhoto(file.getBytes());	
		Store store1 = new Store();
		if (!result.hasErrors() && storeRepository.findByPhone(store.getPhone()) == null) {
			store1 = storeRepository.save(store);
		    HttpSession session = request.getSession();
		    session.setAttribute("store", store1);
		} 
		else { model.addAttribute("error", true); return "create_store"; }
		return store(model, request);
	}
	
	@GetMapping("/mağazam_login")
	public String login(ModelMap model) {
		return "login_page";
	}
	
	@PostMapping("/login")
	public String loginStoreUser(HttpServletRequest request, ModelMap model, @RequestParam(value = "phone") String phone, @RequestParam(value = "password") String password) {
		try {
			Store store = storeRepository.findByPhoneAndPassword(phone, password);
			request.getSession().setAttribute("store", store);
			if (store == null) { model.addAttribute("error", true); return "login_page"; }
		} catch (Exception e) {}
		return "store";
	}
	
	@GetMapping("/logout")
	public String logoutStoreUser(HttpServletRequest request) {
		try {
			request.getSession().setAttribute("store", null);
		} catch (Exception e) {}
		return "main_page";
	}
	
	@GetMapping("/magazam")
	public String store(ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException {
		List<Container> categories = containerRepository.findByStore((Store)request.getSession().getAttribute("store"));
		request.getSession().setAttribute("categories", categories);
		return "store";
	}
}


