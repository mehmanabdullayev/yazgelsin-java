package com.yazgelsin.ordering_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Careers;

@Repository
public interface CareersRepository extends JpaRepository<Careers, Long>{
	
}
