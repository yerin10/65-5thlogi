package com.estimulo.logistics.purchase.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.purchase.serviceFacade.PurchaseServiceFacade;

import com.estimulo.logistics.purchase.to.BomDeployTO;
import com.estimulo.logistics.purchase.to.BomInfoTO;
import com.estimulo.logistics.purchase.to.BomTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/purchase/*")
public class BomController {

	@Autowired
	private PurchaseServiceFacade purchaseSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	
	private ModelMap modelMap = new ModelMap();
	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	/* bom.xfdl -> bom 조회 */
	@RequestMapping("/searchBomDeploy.do")
	public void searchBomDeploy(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String deployCondition = inData.getVariable("deployCondition").getString();
		String itemCode = inData.getVariable("itemCode").getString();
		String itemClassificationCondition = inData.getVariable("itemClassificationCondition").getString();
		// System.out.println(deployCondition);
		// forward 정전개 || reverse 역전개
		// System.out.println(itemCode);
		// CodeController를 사용하여 검색한 후 선택하여 텍스트박스에 들어있던 값을 파라미터로 받아옴
		// ex ] DK-01

		ArrayList<BomDeployTO> bomDeployList = purchaseSF.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);

		datasetBeanMapper.beansToDataset(outData, bomDeployList, BomDeployTO.class);
		

	}
	/* bom.xfdl -> tabpage2 -> bom 조회 */
	@RequestMapping("/searchBomInfo.do")
	public void searchBomInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String parentItemCode = inData.getVariable("parentItemCode").getString();

		ArrayList<BomInfoTO> bomInfoList = purchaseSF.getBomInfoList(parentItemCode);
		datasetBeanMapper.beansToDataset(outData, bomInfoList, BomInfoTO.class);
	}

	

	@RequestMapping("/searchAllItemWithBomRegisterAvailable.do")
	public ModelMap searchAllItemWithBomRegisterAvailable(HttpServletRequest request,
			HttpServletResponse response) {

		try {

			ArrayList<BomInfoTO> allItemWithBomRegisterAvailable = purchaseSF.getAllItemWithBomRegisterAvailable();

			modelMap.put("gridRowJson", allItemWithBomRegisterAvailable);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}

		return modelMap;

	}


	/* bom.xfdl -> tabpage2 -> 일괄저장 */
	@RequestMapping("batchBomListProcess.do")
	public void batchBomListProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		ArrayList<BomTO> batchBomList = (ArrayList<BomTO>) datasetBeanMapper.datasetToBeans(inData, BomTO.class);
		purchaseSF.batchBomListProcess(batchBomList);
	}

}
