package com.estimulo.logistics.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.sales.to.EstimateTO;

@Repository
public interface EstimateRepository  extends JpaRepository<EstimateTO, String> {
	int countByEstimateDate(String estimateDate);
}
