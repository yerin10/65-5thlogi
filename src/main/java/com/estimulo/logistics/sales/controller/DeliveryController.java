package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.DeliveryInfoTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/business/*")
public class DeliveryController {

	@Autowired
	private SalesServiceFacade salesSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
		
	private ModelMap modelMap = new ModelMap();

	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 변환

	@RequestMapping("/searchDeliveryInfoList.do")
	public ModelMap searchDeliveryInfoList(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<DeliveryInfoTO> deliveryInfoList = salesSF.getDeliveryInfoList();

			modelMap.put("gridRowJson", deliveryInfoList);
			modelMap.put("errorCode", 0);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	// batchListProcess

	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		try {

			List<DeliveryInfoTO> deliveryTOList = gson.fromJson(batchList, new TypeToken<ArrayList<DeliveryInfoTO>>() {
			}.getType());

			System.out.println(deliveryTOList);

			HashMap<String, Object> resultMap = salesSF.batchDeliveryListProcess(deliveryTOList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	/* deliveryInfo.xfdl -> 납품가능수주조회 */
	@RequestMapping("/searchDeliverableContractList.do")
	public void searchDeliverableContractList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String searchCondition  = inData.getVariable("searchCondition").getString();
		String customerCode     = inData.getVariable("customerCode").getString();
		String startDate        = inData.getVariable("startDate").getString();
		String endDate          = inData.getVariable("endDate").getString();

		ArrayList<ContractInfoTO> deliverableContractList = null;
		ArrayList<ContractDetailTO> deliverableCTDetailList = new ArrayList<>();

		if (searchCondition.equals("searchByDate")) {

			String[] paramArray = { startDate, endDate };
			deliverableContractList = salesSF.getDeliverableContractList("searchByDate", paramArray);

		} else if (searchCondition.equals("searchByCustomer")) {

			String[] paramArray = { customerCode };
			deliverableContractList = salesSF.getDeliverableContractList("searchByCustomer", paramArray);

		}
		
		for(int i=0; i<deliverableContractList.size();i++) {
			//System.out.println("		@@@@@@@@1: "+deliverableContractList.get(i).getContractDetailTOList());
			for(int n=0; n<deliverableContractList.get(i).getContractDetailTOList().size(); n++) {
				deliverableCTDetailList.add(deliverableContractList.get(i).getContractDetailTOList().get(n));
				//System.out.println("		@@@@@@@@2: "+deliverableCTDetailList);
			}
		}

		datasetBeanMapper.beansToDataset(outData, deliverableContractList, ContractInfoTO.class);
		datasetBeanMapper.beansToDataset(outData, deliverableCTDetailList, ContractDetailTO.class);
		
	}

	/* deliveryInfo.xfdl -> 납품 */
	@RequestMapping("/deliver.do")
	public void deliver(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		
		String contractDetailNo  = inData.getVariable("contractDetailNo").getString();
		
		salesSF.deliver(contractDetailNo);
	}

}
