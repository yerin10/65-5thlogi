package com.estimulo.logistics.production.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estimulo.logistics.production.to.MrpTO;

@Repository
public interface MrpRepository extends JpaRepository<MrpTO, String> {

	//SELECT * FROM MRP WHERE MRP_GATHERING_NO = #{mrpGatheringNo}
	ArrayList<MrpTO> findAllByMrpGatheringNo(String mrpGatheringNo);
}
