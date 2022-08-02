package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Cart;
import com.yazgelsin.ordering_system.model.CartDetail;
import com.yazgelsin.ordering_system.model.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long>{
	List<CartDetail> findByCart(Cart cart);
	List<CartDetail> findByProduct(Product product);
}
