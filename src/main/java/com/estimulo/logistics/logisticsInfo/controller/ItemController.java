package com.estimulo.logistics.logisticsInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.logisticsInfo.serviceFacade.LogisticsInfoServiceFacade;
import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/logisticsInfo/*")
public class ItemController {

	@Autowired
	private LogisticsInfoServiceFacade logisticsSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
		
	private ModelMap modelMap = new ModelMap();

	// GSON 占쎌뵬占쎌뵠�뇡�슢�쑎�뵳占�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 占쎈꺗占쎄쉐揶쏅�れ뵠 null 占쎌뵥 占쎈꺗占쎄쉐占쎈즲 JSON 癰귨옙占쎌넎

	@RequestMapping("/searchItem.do")
	public ModelMap searchItem(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");
		String itemClassification = request.getParameter("itemClassification");
		String itemGroupCode = request.getParameter("itemGroupCode");
		String minPrice = request.getParameter("minPrice");
		String maxPrice = request.getParameter("maxPrice");

		ArrayList<ItemInfoTO> itemInfoList = null;
		String[] paramArray = null;

		try {

			switch (searchCondition) {

			case "ALL":

				paramArray = null;
				break;

			case "ITEM_CLASSIFICATION":

				paramArray = new String[] { itemClassification };
				break;

			case "ITEM_GROUP_CODE":

				paramArray = new String[] { itemGroupCode };
				break;

			case "STANDARD_UNIT_PRICE":

				paramArray = new String[] { minPrice, maxPrice };
				break;

			}

			itemInfoList = logisticsSF.getItemInfoList(searchCondition, paramArray);

			modelMap.put("gridRowJson", itemInfoList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping("/getStandardUnitPrice.do")
	public void getStandardUnitPrice(HttpServletRequest request, HttpServletResponse response) throws Exception{
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData= (PlatformData) request.getAttribute("outData");
		
		String itemCode = inData.getVariable("itemCode").getString();

		int price = logisticsSF.getStandardUnitPrice(itemCode);
		ItemTO item = new ItemTO();
		item.setStandardUnitPrice(price);

		datasetBeanMapper.beanToDataset(outData, item, ItemTO.class);
	}
	
	//@RequestMapping("/getStandardUnitPrice.do")
	public ModelMap getStandardUnitPriceBox(HttpServletRequest request, HttpServletResponse response) {

		String itemCode = request.getParameter("itemCode");
	

		int price = 0;

		try {
			price = logisticsSF.getStandardUnitPriceBox(itemCode);

			modelMap.put("gridRowJson", price);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping("/batchItemListProcess.do")
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {
		
		String batchList = request.getParameter("batchList");

		ArrayList<ItemTO> itemTOList = gson.fromJson(batchList, new TypeToken<ArrayList<ItemTO>>() {
		}.getType());

		try {

			HashMap<String, Object> resultMap = logisticsSF.batchItemListProcess(itemTOList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

}
