package com.estimulo.logistics.production.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.production.dao.WorkOrderDAO;
import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkSiteLog;

@Component
public class WorkOrderApplicationServiceImpl implements WorkOrderApplicationService {
		
		@Autowired
		private WorkOrderDAO workOrderDAO;
		
		@Override
		public HashMap<String,Object> getWorkOrderableMrpList() {
			
			HashMap<String, String> param = new HashMap<>();	
			
			workOrderDAO.getWorkOrderableMrpList(param);
			
			HashMap<String,Object> resultMap = new HashMap<>();
				resultMap.put("gridRowJson",param.get("RESULT"));
				resultMap.put("errorCode",param.get("ERROR_CODE"));
				resultMap.put("errorMsg", param.get("ERROR_MSG"));
			
			return resultMap;			
			
	}

		@Override
		public HashMap<String,Object> getWorkOrderSimulationList(String mrpNo) {

			HashMap<String, String> param = new HashMap<>();
				param.put("mrpNo", mrpNo);	
			
			workOrderDAO.getWorkOrderSimulationList(param);
			
			HashMap<String,Object> resultMap = new HashMap<>();			

			resultMap.put("gridRowJson",param.get("RESULT"));
			resultMap.put("errorCode",param.get("ERROR_CODE"));
		    resultMap.put("errorMsg", param.get("ERROR_MSG"));
			
			return resultMap;
			
		}

		@Override
		public HashMap<String,Object> workOrder(String workPlaceCode,String productionProcess) {

			HashMap<String, String> param = new HashMap<>();
				param.put("workPlaceCode", workPlaceCode);
				param.put("productionProcess", productionProcess);
				
			workOrderDAO.workOrder(param);	
			
			HashMap<String,Object> resultMap = new HashMap<>();			

			resultMap.put("errorCode",param.get("ERROR_CODE"));
		    resultMap.put("errorMsg", param.get("ERROR_MSG"));
		
		return resultMap; 	
			
		}

		@Override
		public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {
			
			return workOrderDAO.selectWorkOrderInfoList();
			
		}

		@Override
		public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {

			HashMap<String, String> param = new HashMap<>();
				param.put("workOrderNo", workOrderNo);
				param.put("actualCompletionAmount", actualCompletionAmount);	
			
				workOrderDAO.workOrderCompletion(param);
				
				HashMap<String,Object> resultMap = new HashMap<>();			

				resultMap.put("errorCode",param.get("ERROR_CODE"));
			    resultMap.put("errorMsg", param.get("ERROR_MSG"));
			
			return resultMap;
		}
		
		@Override
		public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

			return workOrderDAO.selectProductionPerformanceInfoList();
			
		}

		@Override
		public HashMap<String,Object> showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication) {
			
			HashMap<String,String> param = new HashMap<>();
				param.put("workSiteCourse", workSiteCourse);
				param.put("workOrderNo", workOrderNo);
				param.put("itemClassIfication", itemClassIfication);
		  	
				workOrderDAO.selectWorkSiteSituation(param);
								 
				HashMap<String,Object> resultMap = new HashMap<>();
		    	  	resultMap.put("gridRowJson",param.get("RESULT"));
		    	  	resultMap.put("errorCode",param.get("ERROR_CODE"));
		    	  	resultMap.put("errorMsg",param.get("ERROR_MSG"));
		  	  
				return resultMap;
		}

		@Override
		public void workCompletion(String workOrderNo, String itemCode ,  ArrayList<String> itemCodeListArr) {

				String itemCodeList=itemCodeListArr.toString().replace("[", "").replace("]", "");
				
				HashMap<String,String> param = new HashMap<>();
					param.put("workOrderNo", workOrderNo);
					param.put("itemCode", itemCode);
					param.put("itemCodeList", itemCodeList);
				
				workOrderDAO.updateWorkCompletionStatus(param);

		}

		@Override
		public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {

			ArrayList<WorkSiteLog> list = workOrderDAO.workSiteLogList(workSiteLogDate);
			
			HashMap<String,Object> resultMap = new HashMap<>();
     	  		resultMap.put("gridRowJson",list);

     	  	return resultMap;
			
		}
		
}
