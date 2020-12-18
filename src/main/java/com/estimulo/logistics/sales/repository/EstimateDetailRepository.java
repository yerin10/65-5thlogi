package com.estimulo.logistics.sales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.sales.to.EstimateDetailTO;

@Repository
public interface EstimateDetailRepository extends JpaRepository<EstimateDetailTO, String> {

	List<EstimateDetailTO> findAllByEstimateNo(String estimateNo);
	
	List<EstimateDetailTO> findAllEstimateDetailNoByEstimateNo(String estimateNo);

}
