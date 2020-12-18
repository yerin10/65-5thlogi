package com.estimulo.logistics.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.purchase.to.BomTO;





@Repository
public interface BomRepository extends JpaRepository<BomTO, String> {

}
