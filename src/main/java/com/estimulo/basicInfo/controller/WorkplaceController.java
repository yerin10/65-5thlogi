package com.estimulo.basicInfo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estimulo.basicInfo.serviceFacade.OrganizationServiceFacade;
import com.estimulo.basicInfo.to.CompanyTO;
import com.estimulo.basicInfo.to.WorkplaceTO;
import com.estimulo.common.mapper.DatasetBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/basicInfo/*")
public class WorkplaceController {
	
	@Autowired
	private OrganizationServiceFacade orgSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	private ModelMap modelMap = new ModelMap();
	
	// GSON �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 JSON 蹂��솚
		
	@RequestMapping("/searchWorkplace.do")
	public void searchWorkplaceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData =(PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
			System.out.println("ddzzz??");
			
		String companyCode = datasetBeanMapper.datasetToBean(inData, CompanyTO.class).getCompanyCode();

			ArrayList<WorkplaceTO> workplaceList = null;
			workplaceList = orgSF.getWorkplaceList(companyCode);
		
		if(workplaceList != null) {
			System.out.println("찍히나?");
	datasetBeanMapper.beansToDataset(outData, workplaceList, WorkplaceTO.class);
		
		}

	}

	@RequestMapping(value="/batchFinancialAccountAssociatesListProcess.do",method=RequestMethod.POST)
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {


		String batchList = request.getParameter("batchList");

		ArrayList<WorkplaceTO> workplaceList = gson.fromJson(batchList, new TypeToken<ArrayList<WorkplaceTO>>() {
		}.getType());

		try {

			HashMap<String, Object> resultMap = orgSF.batchWorkplaceListProcess(workplaceList);

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
