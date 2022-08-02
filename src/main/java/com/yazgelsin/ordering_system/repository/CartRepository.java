package com.yazgelsin.ordering_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findByBuyerPhone(String buyerPhone);
	Cart findByBuyerId(String buyerId);
}
