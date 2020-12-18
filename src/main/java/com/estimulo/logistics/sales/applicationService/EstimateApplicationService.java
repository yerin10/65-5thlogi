package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.logistics.sales.to.EstimateTO;

public interface EstimateApplicationService {
	
	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate);
	/*
	 * public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo);
	 */
	
	// ApplicationService �븞�뿉�꽌留� �샇異�
	public String getNewEstimateNo(String estimateDate);

	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO);

	public HashMap<String, Object> batchEstimateDetailListProcess(List<EstimateDetailTO> estimateDetailTOList);	
	
}  