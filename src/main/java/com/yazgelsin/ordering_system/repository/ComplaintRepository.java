package com.yazgelsin.ordering_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazgelsin.ordering_system.model.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{
	List<Complaint> findByResolved(boolean resolved);
}
