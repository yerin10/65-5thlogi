package com.estimulo.basicInfo.controller;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estimulo.basicInfo.serviceFacade.OrganizationServiceFacade;

import com.estimulo.basicInfo.to.CompanyTO;
import com.estimulo.common.mapper.DatasetBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/basicInfo/*")
public class CompanyController {


	// serviceFacade 참조변수 선언
	@Autowired
	private OrganizationServiceFacade organizationService;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	

	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 JSON 변환

	@RequestMapping("/searchCompany.do")
	public void searchCompanyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("dd");
		//PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		System.out.println(outData+"???");
		
		ArrayList<CompanyTO> companyList = organizationService.getCompanyList();
		System.out.println("??");
		
		if (companyList != null) {
			System.out.println("#############################: "+companyList.get(0));
			datasetBeanMapper.beansToDataset(outData, companyList, CompanyTO.class);
			System.out.println("#############################111: "+companyList.get(0));
		}
	}

	@RequestMapping(value="/batchCompanyListProcess.do",method=RequestMethod.POST)
	public void batchListProcess(HttpServletRequest request, HttpServletResponse response) {


		String batchList = request.getParameter("batchList");

		ArrayList<CompanyTO> companyList = gson.fromJson(batchList, new TypeToken<ArrayList<CompanyTO>>() {
		}.getType());

		organizationService.batchCompanyListProcess(companyList);

	}

}
