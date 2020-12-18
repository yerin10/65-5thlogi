package com.estimulo.authorityManager.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.estimulo.authorityManager.exception.*;
import com.estimulo.authorityManager.serviceFacade.AuthorityManagerServiceFacade;
import com.estimulo.basicInfo.to.CompanyTO;
import com.estimulo.basicInfo.to.WorkplaceTO;
import com.estimulo.common.mapper.DatasetBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.estimulo.hr.to.EmpInfoTO;
import com.tobesoft.xplatform.data.PlatformData;


@Controller
public class MemberLogInController{


	@Autowired
	private AuthorityManagerServiceFacade authorityManagerService;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;


	@RequestMapping("/login.do")
	public void LogInCheck(HttpServletRequest request, HttpServletResponse response) throws IdNotFoundException, PwMissMatchException, PwNotFoundException, Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");

		String companyCode   = datasetBeanMapper.datasetToBean(inData, CompanyTO.class).getCompanyCode();
		String workplaceCode = datasetBeanMapper.datasetToBean(inData, WorkplaceTO.class).getWorkplaceCode();
		String inputId       = inData.getVariable("userId").getString();
		String inputPassWord = inData.getVariable("userPassWord").getString();

		EmpInfoTO TO = authorityManagerService.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);

		if (TO != null) {
			datasetBeanMapper.beanToDataset(outData, TO, EmpInfoTO.class);
			System.out.println("		@ DeptCode: "+TO.getDeptCode());
			System.out.println("		@ EmpName: "+TO.getEmpName());
		}
	}

	
	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.getSession().invalidate();
	}

}