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

import com.estimulo.logistics.production.to.MrpGatheringTO;
import com.estimulo.logistics.production.to.MrpTO;
import com.estimulo.logistics.production.to.OpenMrpTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/production/*")
public class MrpController {

	@Autowired
	private ProductionServiceFacade productionSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	// gson �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚

	private ModelMap modelMap = new ModelMap();
	
	@RequestMapping("/getMrpList.do")
	public void getMrpList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");

		String mrpGatheringStatusCondition = inData.getVariableList().getString("mrpGatheringStatusCondition");
		String dateSearchCondition = inData.getVariableList().getString("dateSearchCondition");
		String mrpStartDate = inData.getVariableList().getString("mrpStartDate");
		String mrpEndDate = inData.getVariableList().getString("mrpEndDate");
		String mrpGatheringNo = inData.getVariableList().getString("mrpGatheringNo");

			ArrayList<MrpTO> mrpList = null;
			
		
			if(mrpGatheringStatusCondition != null ) {
				//여기 null이라는 스트링값이 담겨저왔으니 null은 아님. 객체가있는상태.
				mrpList = productionSF.searchMrpList(mrpGatheringStatusCondition);
				
			} else if (dateSearchCondition != null) {
				
				mrpList = productionSF.searchMrpList(dateSearchCondition, mrpStartDate, mrpEndDate);
				
			} else if (mrpGatheringNo != null) {
				
				mrpList = productionSF.searchMrpListAsMrpGatheringNo(mrpGatheringNo);
				
			}
			
			datasetBeanMapper.beansToDataset(outData, mrpList, MrpTO.class);
	}
	
	@RequestMapping("/openMrp.do")
	@SuppressWarnings("unchecked")
	public void openMrp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");

		ArrayList<String> mpsNoArr = new ArrayList<>();
		String mpsNoList = inData.getVariableList().getString("mpsNoList");
		System.out.println("mpsNoList:::"+mpsNoList);
		mpsNoArr.add(mpsNoList);

		/*
		int index=mpsNoList.size();
		System.out.println("        @ index: "+index);
		for(int i=0; i<index; i++) {
			System.out.println("        @ mpsNoList0: "+mpsNoList.getString(i));
		}
		*/
		
		HashMap<String, Object> resultMap = productionSF.openMrp(mpsNoArr);
		
		//System.out.println("        @ : "+resultMap.get("gridRowJson"));

		ArrayList<OpenMrpTO> result=  (ArrayList<OpenMrpTO>) resultMap.get("gridRowJson");
		
		/*
		for(OpenMrpTO v:result)
		{
			System.out.println("        @22 : "+v);
		}
		*/
		
		datasetBeanMapper.beansToDataset(outData, result, OpenMrpTO.class);
	}

	@RequestMapping("/registerMrp.do")
public void registerMrp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		
		ArrayList<MrpTO> newMrpList = (ArrayList<MrpTO>) datasetBeanMapper.datasetToBeans(inData, MrpTO.class);
		String mrpRegisterDate      = inData.getVariable("mrpRegisterDate").getString();
		
		productionSF.registerMrp(mrpRegisterDate, newMrpList);	 
	}
	
	
	@RequestMapping("/getMrpGatheringList.do")
	public void  getMrpGatheringList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		ArrayList<String> mrpNoArr = new ArrayList<>();
		String mrpNoList = inData.getVariableList().getString("mrpNoList");
		System.out.println(mrpNoList+":::::");
		mrpNoArr.add(mrpNoList);

		ArrayList<MrpGatheringTO> mrpGatheringList = productionSF.getMrpGathering(mrpNoArr);
		
		datasetBeanMapper.beansToDataset(outData, mrpGatheringList, MrpGatheringTO.class);
		//modelMap.put("gridRowJson", mrpGatheringList);

	}
	
	
	@RequestMapping("/registerMrpGathering.do")
		public void registerMrpGathering(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData  = (PlatformData) request.getAttribute("inData");		 

		ArrayList<MrpGatheringTO> newMrpGatheringList = (ArrayList<MrpGatheringTO>) datasetBeanMapper.datasetToBeans(inData, MrpGatheringTO.class);
		String mrpGatheringRegisterDate = inData.getVariable("mrpGatheringRegisterDate").getString();
		String mrpNoAndItemCodeList     = inData.getVariable("mrpNoAndItemCodeList").getString();
		System.out.println(mrpNoAndItemCodeList+"L:::::::mrpNoAndItemCodeList");
		System.out.println(mrpGatheringRegisterDate+"LLL:::mrpGatheringRegisterDate");
		
		HashMap<String, String> mrpNoAndItemCodeMap = gson.fromJson(mrpNoAndItemCodeList, new TypeToken<HashMap<String, String>>() { }.getType());

		productionSF.registerMrpGathering(mrpGatheringRegisterDate, newMrpGatheringList, mrpNoAndItemCodeMap);	 
	}
	
	@RequestMapping("/searchMrpGathering.do")
	public void searchMrpGathering(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String searchDateCondition = inData.getVariable("searchDateCondition").getString();
		System.out.println(searchDateCondition+"::::searchDateCondition");
		String startDate = inData.getVariable("mrpGatheringStartDate").getString();
		String endDate = inData.getVariable("mrpGatheringEndDate").getString();
		
			ArrayList<MrpGatheringTO> mrpGatheringList = productionSF.searchMrpGatheringList(searchDateCondition, startDate, endDate);
			ArrayList<MrpTO> mrpList = new ArrayList<>();
			int size = mrpGatheringList.size();
			System.out.println("		@ size ::::: "+size);
			for(int i=0; i<size; i++) {
				int size2 = mrpGatheringList.get(i).getMrpTOList().size();
				for(int n=0; n<size2; n++) {
					mrpList.add(mrpGatheringList.get(i).getMrpTOList().get(n));
				}
			}
			
			datasetBeanMapper.beansToDataset(outData, mrpGatheringList, MrpGatheringTO.class);
			datasetBeanMapper.beansToDataset(outData, mrpList, MrpTO.class);
	
	
	}
}
