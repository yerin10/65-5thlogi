package com.estimulo.logistics.purchase.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.purchase.to.OrderInfoTO;




@Repository
public interface OrderRepository extends JpaRepository<OrderInfoTO, String> {

	//SELECT * FROM ORDER_INFO WHERE ORDER_INFO_STATUS = '운송중'
	ArrayList<OrderInfoTO> findAllByOrderInfoStatus(String transit);
}
