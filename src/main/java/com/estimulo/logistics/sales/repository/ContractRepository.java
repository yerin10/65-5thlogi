package com.estimulo.logistics.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.estimulo.logistics.sales.to.ContractTO;

@Repository
public interface ContractRepository extends JpaRepository<ContractTO, String> {

}
