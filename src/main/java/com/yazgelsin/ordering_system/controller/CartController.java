package com.yazgelsin.ordering_system.controller;

import javax.servlet.http.HttpServletRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yazgelsin.ordering_system.model.Cart;
import com.yazgelsin.ordering_system.model.CartDetail;
import com.yazgelsin.ordering_system.model.Product;
import com.yazgelsin.ordering_system.repository.CartDetailRepository;
import com.yazgelsin.ordering_system.repository.CartRepository;
import com.yazgelsin.ordering_system.repository.ProductRepository;

@Controller
public class CartController {

	private CartRepository cartRepository;
	private CartDetailRepository cartDetailRepository;
	private ProductRepository productRepository;
	
	@Autowired
	public CartController(CartRepository repository1, CartDetailRepository repository2, ProductRepository repository3) {
		this.cartRepository = repository1;
		this.cartDetailRepository = repository2;
		this.productRepository = repository3;
	}
	
	@GetMapping("/səbət")
	public String cart(HttpServletRequest request, ModelMap model) {
		Cart cart = null;
		try { cart = cartRepository.findByBuyerId(request.getSession().getId()); }
		catch (Exception e) {}
		if (cart != null) { 
			List<CartDetail> list = cartDetailRepository.findByCart(cart);
			List<CartDetail> newList = new ArrayList<>();
			if (list.size() > 0) {
				for (CartDetail d : list) {
					if (d.getOrder() == null)
						newList.add(d);
				}
				if (newList.size() > 0) {
					model.addAttribute("cart", cart); 
					model.addAttribute("list", newList); 
					double total = 0;
					for (CartDetail d1 : newList) {
						total += (d1.getProduct().getPrice().doubleValue() * d1.getQuantity());
					}
					total = Double.parseDouble(new DecimalFormat("##.##").format(total));
					model.addAttribute("total", total);
					return "cart";
				}
			}
		}
		return "no_cart";
	}
	
	@PostMapping("/add_cart")
	public String addCart(ModelMap model, HttpServletRequest request, @RequestParam(value  = "quantity") String quantity, @RequestParam(value = "id") long id) {
		Cart cart = null;
		try { cart = cartRepository.findByBuyerId(request.getSession().getId()); }
		catch (Exception e) {}
		if (cart == null) { 
			cart = new Cart();
			cart.setBuyerId(request.getSession().getId());
			cartRepository.save(cart);
		} 
		Product product = productRepository.getReferenceById(id);
		CartDetail item = new CartDetail();
		item.setCart(cart);
		item.setProduct(product);
		item.setQuantity(Integer.valueOf(quantity));
		cartDetailRepository.save(item);
		return cart(request, model);
	}
	
	@GetMapping("/remove_item")
	public String removeItem(HttpServletRequest request, ModelMap model, @RequestParam(value = "id") long id) {
		Cart cart = null;
		try { cart = cartRepository.findByBuyerId(request.getSession().getId()); }
		catch (Exception e) {}
		if (cart != null) cartDetailRepository.deleteById(id);
		return cart(request, model);
	}
}



