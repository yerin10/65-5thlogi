package com.estimulo.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.purchase.dao.OrderDAO;
import com.estimulo.logistics.purchase.repository.OrderRepository;
import com.estimulo.logistics.purchase.to.OrderInfoTO;

@Component
public class OrderApplicationServiceImpl implements OrderApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {
		
		HashMap<String, String> param = new HashMap<>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
		
		orderDAO.getOrderList(param);
			
		HashMap<String,Object> resultMap = new HashMap<>();
			
			resultMap.put("gridRowJson",param.get("RESULT"));
	        resultMap.put("errorCode",param.get("ERROR_CODE"));
	        resultMap.put("errorMsg", param.get("ERROR_MSG")); 
			
		return resultMap;
	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr) {

			String mrpNoList = mrpNoArr.toString().replace("[", "").replace("]", "");
						
		HashMap<String, String> param = new HashMap<>();
			param.put("mrpNoList", mrpNoList);	
			
		orderDAO.getOrderDialogInfo(param);
		
        HashMap<String,Object> resultMap = new HashMap<>();
			resultMap.put("gridRowJson",param.get("RESULT"));
			resultMap.put("errorCode",param.get("ERROR_CODE"));
			resultMap.put("errorMsg", param.get("ERROR_MSG"));
			
		return resultMap;
	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {
        
		String mpsNoList = mrpGaNoArr.toString().replace("[", "").replace("]", "");

		HashMap<String, String> param = new HashMap<>();
			param.put("mpsNoList", mpsNoList);	
		
		orderDAO.order(param);
			
		HashMap<String,Object> resultMap = new HashMap<>();
        	resultMap.put("errorCode",param.get("ERROR_CODE"));
        	resultMap.put("errorMsg", param.get("ERROR_MSG")); 

    	return resultMap;
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {

		HashMap<String, String> param = new HashMap<>();
			param.put("itemCode", itemCode);
			param.put("itemAmount", itemAmount);	
    	
			orderDAO.optionOrder(param);
			
		HashMap<String,Object> resultMap = new HashMap<>();
        	resultMap.put("errorCode",param.get("ERROR_CODE"));
        	resultMap.put("errorMsg", param.get("ERROR_MSG")); 
        	
		return resultMap;	
		
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {
		//ArrayList<OrderInfoTO> orderList = orderMapper.getOrderInfoListOnDelivery();

		// &&& jpa processing complete
		ArrayList<OrderInfoTO> orderList = orderRepository.findAllByOrderInfoStatus("운송중");

		return orderList;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {
		
		HashMap<String, String> param = new HashMap<>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
		
		return orderDAO.getOrderInfoList(param);
		
	}

}
