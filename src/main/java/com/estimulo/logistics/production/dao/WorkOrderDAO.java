package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkOrderSimulationTO;
import com.estimulo.logistics.production.to.WorkOrderableMrpListTO;
import com.estimulo.logistics.production.to.WorkSiteLog;
import com.estimulo.logistics.production.to.WorkSiteSimulationTO;

@Mapper
public interface WorkOrderDAO {

	public ArrayList<WorkOrderableMrpListTO> getWorkOrderableMrpList(HashMap<String, String> param);
	
	public ArrayList<WorkOrderSimulationTO> getWorkOrderSimulationList(HashMap<String, String> param);	
	
	public HashMap<String,Object> workOrder(HashMap<String, String> param);
	
	public ArrayList<WorkOrderInfoTO> selectWorkOrderInfoList();
	
	public HashMap<String,Object> workOrderCompletion(HashMap<String, String> param);
	
	public ArrayList<ProductionPerformanceInfoTO> selectProductionPerformanceInfoList();
	
	public ArrayList<WorkSiteSimulationTO> selectWorkSiteSituation(HashMap<String,String> param);
	
	public void updateWorkCompletionStatus(HashMap<String,String> param);
	
	public ArrayList<WorkSiteLog> workSiteLogList(String workSiteLogDate);
	
}
