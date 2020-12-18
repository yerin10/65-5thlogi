package com.estimulo.logistics.purchase.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.purchase.serviceFacade.PurchaseServiceFacade;

import com.estimulo.logistics.purchase.to.StockLogTO;
import com.estimulo.logistics.purchase.to.StockTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/purchase/*")
public class StockController {

	@Autowired
	private PurchaseServiceFacade purchaseSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
		
	private ModelMap modelMap = new ModelMap();


	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@RequestMapping("/searchStockList.do")
	public void searchStockList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData outData = (PlatformData) request.getAttribute("outData");
		ArrayList<StockTO> stockList = purchaseSF.getStockList();
	
		datasetBeanMapper.beansToDataset(outData, stockList, StockTO.class);
			
	}

	@RequestMapping("/searchStockLogList.do")
	public ModelMap searchStockLogList(HttpServletRequest request, HttpServletResponse response) {
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		try {

			ArrayList<StockLogTO> stockLogList = purchaseSF.getStockLogList(startDate,endDate);

			modelMap.put("gridRowJson", stockLogList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		
		return modelMap;
	}

	/* StockWarehouse.xfdl -> 현재 체크된 발주품목 입고 */
	@RequestMapping("/warehousing.do")
	public void warehousing(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData   = (PlatformData) request.getAttribute("inData");
		
		String orderNoListStr = inData.getVariable("orderNoList").getString();

		ArrayList<String> orderNoArr = gson.fromJson(orderNoListStr,new TypeToken<ArrayList<String>>(){}.getType());	

		HashMap<String, Object> resultMap = purchaseSF.warehousing(orderNoArr);
		
		Set<String> set = resultMap.keySet();
		for(String v:set) {
			System.out.println("        @@@@@ key: "+v+"  ,"+" value: "+resultMap.get(v));
		}

	}
	
	
	
}
