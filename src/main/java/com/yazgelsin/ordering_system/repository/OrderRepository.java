package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Order;
import com.yazgelsin.ordering_system.model.Store;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByStoreAndFilled(Store store, boolean condition);
	Order findByBuyerPhone(String buyerPhone);
}
