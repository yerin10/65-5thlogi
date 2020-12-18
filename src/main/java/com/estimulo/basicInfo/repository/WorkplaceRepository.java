package com.estimulo.basicInfo.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.basicInfo.to.WorkplaceTO;


@Repository
public interface WorkplaceRepository extends JpaRepository<WorkplaceTO, String>{
	ArrayList<WorkplaceTO> findByCompanyCode(String companyCode);
}