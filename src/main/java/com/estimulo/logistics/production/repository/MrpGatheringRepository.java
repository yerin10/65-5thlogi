package com.estimulo.logistics.production.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.production.to.MrpGatheringTO;

@Repository
public interface MrpGatheringRepository extends JpaRepository<MrpGatheringTO, String> {

}
