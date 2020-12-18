package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;

import com.estimulo.logistics.production.to.ContractDetailInMpsAvailableTO;
import com.estimulo.logistics.production.to.MpsTO;
import com.estimulo.logistics.production.to.SalesPlanInMpsAvailableTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/production/*")
public class MpsController {

	@Autowired
	private ProductionServiceFacade productionSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	private ModelMap modelMap = new ModelMap();

	// gson �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚
	
	@RequestMapping("/searchMpsInfo.do")
	public void searchMpsInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String startDate       = inData.getVariable("startDate").getString();
		String endDate         = inData.getVariable("endDate").getString();
		String includeMrpApply = inData.getVariable("includeMrpApply").getString(); 

		ArrayList<MpsTO> mpsTOList = productionSF.getMpsList(startDate, endDate, includeMrpApply);

		datasetBeanMapper.beansToDataset(outData, mpsTOList, MpsTO.class);
	}
	@RequestMapping("/searchContractDetailInMpsAvailable.do")
	public void searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String startDate       = inData.getVariable("startDate").getString();
		String endDate         = inData.getVariable("endDate").getString();
		String searchCondition = inData.getVariable("searchCondition").getString();
	

			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = productionSF.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
													   //contractDate, 2019-07-01, 2019-07-31
		
	
			datasetBeanMapper.beansToDataset(outData, contractDetailInMpsAvailableList, ContractDetailInMpsAvailableTO.class);
		
	}

	@RequestMapping("/searchSalesPlanInMpsAvailable.do")
	public ModelMap searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		try {

			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = productionSF
					.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			modelMap.put("gridRowJson", salesPlanInMpsAvailableList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping("/convertContractDetailToMps.do")
	public void convertContractDetailToMps(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");	
		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList= (ArrayList<ContractDetailInMpsAvailableTO>) datasetBeanMapper.datasetToBeans(inData, ContractDetailInMpsAvailableTO.class);
		
		

			productionSF.convertContractDetailToMps(contractDetailInMpsAvailableList);

		
	}

	@RequestMapping("/convertSalesPlanToMps.do")
	public ModelMap convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");

		ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
				new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
				}.getType());

		try {

			HashMap<String, Object> resultMap = productionSF.convertSalesPlanToMps(salesPlanInMpsAvailableList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

}
