package com.estimulo.logistics.production.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.production.to.MpsTO;


@Repository
public interface MpsRepository extends JpaRepository<MpsTO, String> {

	List<MpsTO> findAllByMpsPlanDate(String mpsPlanDate);
}
