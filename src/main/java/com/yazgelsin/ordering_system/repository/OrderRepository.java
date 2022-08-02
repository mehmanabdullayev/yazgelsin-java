package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Order;
import com.yazgelsin.ordering_system.model.Product;
import com.yazgelsin.ordering_system.model.Store;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByStore(Store store);
	List<Order> findByProduct(Product product);
	Order findByBuyerPhone(String buyerPhone);
}
