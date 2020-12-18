package com.estimulo.basicInfo.repository;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.basicInfo.to.CompanyTO;


@Repository
public interface CompanyRepository extends JpaRepository<CompanyTO, String>{
	ArrayList<CompanyTO> findAllByOrderByCompanyCodeDesc();
}