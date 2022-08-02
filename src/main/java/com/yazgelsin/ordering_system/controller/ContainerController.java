package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazgelsin.ordering_system.model.Container;
import com.yazgelsin.ordering_system.model.Product;
import com.yazgelsin.ordering_system.model.Store;
import com.yazgelsin.ordering_system.repository.ContainerRepository;
import com.yazgelsin.ordering_system.repository.ProductRepository;

@Controller
public class ContainerController {
	private ContainerRepository containerRepository;
	private ProductRepository productRepository;
	
	@Autowired
	public ContainerController(ContainerRepository repository1, ProductRepository repository2) {
		this.containerRepository = repository1;
		this.productRepository = repository2;
	}
	
	@GetMapping("/yeni_kateqoriya")
	public String newCategory(ModelMap model) {
		model.addAttribute("createContainer", new Container());
		return "container";
	}
	
	@PostMapping("/createContainer")
	public String createContainer(HttpServletRequest request, @ModelAttribute("createContainer") @Valid Container container, BindingResult result) {
		Store store = (Store) request.getSession().getAttribute("store");
		container.setStore(store);
		if (!result.hasErrors()) 
			containerRepository.save(container);
		return "redirect:/magazam";
	}
	
	@GetMapping("/add_product")
	public String addProduct(@RequestParam(value = "containerId") String id, ModelMap model) {
		model.addAttribute("containerId", id);
		model.addAttribute("addProduct", new Product());
		return "container_add";
	}
	
	@PostMapping("/addProduct")
	public String containerAdd(ModelMap model, HttpServletRequest request, @RequestParam(value = "containerId") String id, @RequestParam(value = "file1") MultipartFile file1, @RequestParam(value = "file2") MultipartFile file2, @RequestParam(value = "file3") MultipartFile file3, @ModelAttribute("addProduct") Product product, BindingResult result) throws IOException {
		System.out.println(model.getAttribute("containerId"));
		product.setContainer(containerRepository.getReferenceById(Long.valueOf(id)));
		product.setPhoto1(file1.getBytes());	
		product.setPhoto2(file2.getBytes());
		product.setPhoto3(file3.getBytes());
		if (!result.hasErrors()) 
			productRepository.save(product);
		return "redirect:/magazam";
	}
	
	@GetMapping("/photos/{productId}/1")
	@ResponseBody
	public void diplay(@PathVariable("productId") long id, HttpServletResponse response, HttpServletRequest request) throws IOException {
		Product product = productRepository.getReferenceById(id);
		response.setContentType("image/png");
		response.getOutputStream().write(product.getPhoto1());
		response.getOutputStream().close();
	}
	
	@GetMapping("/delete_product")
	public String deleteProduct(HttpServletRequest request, @RequestParam(value = "containerId") String id1, @RequestParam(value = "productId") String id2) {
		try {
			Store store = (Store) request.getSession().getAttribute("store");
			Container container = containerRepository.findByIdAndStore(Long.valueOf(id1), store);
			Product product = productRepository.getReferenceById(Long.valueOf(id2));
			if (productRepository.findByContainer(container).contains(product))
				productRepository.deleteById(product.getId()); 
		} catch (Exception e) {}
		return "redirect:/magazam";
	}
	
	@GetMapping("/delete_category")
	public String deleteCategory(HttpServletRequest request, @RequestParam(value = "id") String id) {
		Store store = (Store) request.getSession().getAttribute("store");
		Container container = containerRepository.findByIdAndStore(Long.valueOf(id), store);
		try { containerRepository.deleteById(container.getId()); }
		catch (Exception e) {}
		return "redirect:/magazam";
	}
}


