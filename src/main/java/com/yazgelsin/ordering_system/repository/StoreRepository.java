package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
	Store findByPhone(String phone);
	Store findByPhoneAndPassword(String phone, String password);
	List<Store> findByRankingGreaterThan(int ranking);
}
