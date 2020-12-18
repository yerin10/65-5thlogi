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
import com.estimulo.logistics.purchase.to.OrderDialogTempTO;
import com.estimulo.logistics.purchase.to.OrderInfoTO;
import com.estimulo.logistics.purchase.to.OrderTempTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/purchase/*")
public class OrderController {

	@Autowired
	private PurchaseServiceFacade purchaseSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
		
	private ModelMap modelMap = new ModelMap();


	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	@SuppressWarnings("unchecked")
	@RequestMapping("/getOrderList.do")
	public void getOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData =(PlatformData) request.getAttribute("inData");
		PlatformData outData=(PlatformData) request.getAttribute("outData");
		
		String startDate = inData.getVariable("startDate").getString();
		String endDate = inData.getVariable("endDate").getString();
		
		HashMap<String, Object> resultMap = new HashMap<>();

		

			resultMap = purchaseSF.getOrderList(startDate, endDate);
			
			ArrayList<OrderTempTO> otTO = new ArrayList<>();
			
			otTO = (ArrayList<OrderTempTO>) resultMap.get("gridRowJson");
			
			datasetBeanMapper.beansToDataset(outData, otTO, OrderTempTO.class);
		
	
		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/showOrderDialog.do")
	public void openOrderDialog(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String mrpGatheringNoListStr = inData.getVariable("mrpGatheringNoList").getString();
		
		ArrayList<String> mrpGatheringNoListArr = gson.fromJson(mrpGatheringNoListStr, new TypeToken<ArrayList<String>>() {
		}.getType());
		HashMap<String,Object> resultMap = purchaseSF.getOrderDialogInfo(mrpGatheringNoListArr);
		
		ArrayList<OrderDialogTempTO> odTO = new ArrayList<>();
		odTO = (ArrayList<OrderDialogTempTO>) resultMap.get("gridRowJson");
		datasetBeanMapper.beansToDataset(outData, odTO, OrderDialogTempTO.class);

		
	}

	@RequestMapping("/showOrderInfo.do")
public void showOrderInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String startDate = inData.getVariable("startDate").getString();
		String endDate = inData.getVariable("endDate").getString();

		ArrayList<OrderInfoTO> orderInfoList = purchaseSF.getOrderInfoList(startDate,endDate);

		datasetBeanMapper.beansToDataset(outData, orderInfoList, OrderInfoTO.class);

	}

	@RequestMapping("/searchOrderInfoListOnDelivery.do")
	public void searchOrderInfoListOnDelivery(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData outData = (PlatformData) request.getAttribute("outData");
		ArrayList<OrderInfoTO> orderInfoListOnDelivery = purchaseSF.getOrderInfoListOnDelivery();
		datasetBeanMapper.beansToDataset(outData, orderInfoListOnDelivery, OrderInfoTO.class);
	
	}

	@RequestMapping("/order.do")
	public void order(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData = (PlatformData) request.getAttribute("inData");
		
		String mrpGatheringNoListStr = inData.getVariable("mrpGatheringNoList").getString();
		System.out.println(mrpGatheringNoListStr+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		ArrayList<String> mrpGaNoArr = gson.fromJson(mrpGatheringNoListStr , new TypeToken<ArrayList<String>>() {}.getType());

///////////////////////////////시작부
		HashMap<String, Object> resultMap = purchaseSF.order(mrpGaNoArr);

		Set<String> set = resultMap.keySet();
		for(String v:set) {
			System.out.println("        @@@@@ key: "+v+"  ,"+" value: "+resultMap.get(v));
		}
	}

	@RequestMapping("/optionOrder.do")
	public void optionOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData = (PlatformData) request.getAttribute("inData");
		
		String itemCode = inData.getVariable("itemCode").getString();
		String itemAmount = inData.getVariable("itemAmount").getString();

		HashMap<String, Object> resultMap = purchaseSF.optionOrder(itemCode, itemAmount);

		Set<String> set = resultMap.keySet();
		for(String v:set) {
			System.out.println("        @@@@@ key: "+v+"  ,"+" value: "+resultMap.get(v));
		}

	}
	
	

}
