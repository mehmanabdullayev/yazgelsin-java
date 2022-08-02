package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Container;
import com.yazgelsin.ordering_system.model.Store;

@Repository
public interface ContainerRepository extends JpaRepository<Container, Long> {
	List<Container> findByStore(Store store);
	Container findByIdAndStore(long id, Store store);
}
