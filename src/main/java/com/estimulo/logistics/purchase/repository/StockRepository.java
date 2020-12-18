package com.estimulo.logistics.purchase.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.purchase.to.StockTO;




@Repository
public interface StockRepository extends JpaRepository<StockTO, String> {

	ArrayList<StockTO> findAllByOrderByItemCode();
}
