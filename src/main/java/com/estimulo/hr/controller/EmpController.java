package com.estimulo.hr.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.hr.serviceFacade.HrServiceFacade;

import com.estimulo.hr.to.EmpInfoTO;
import com.estimulo.hr.to.EmployeeBasicTO;
import com.estimulo.hr.to.EmployeeDetailTO;
import com.estimulo.hr.to.EmployeeSecretTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/hr/*")
public class EmpController {

	@Autowired
	private HrServiceFacade hrSF;	
	
	@Autowired
	DatasetBeanMapper datasetBeanMapper;
	
	
	private ModelMap modelMap = new ModelMap();
	
	// GSON 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환

	
	// EmpRegister.xfdl->조회 버튼
	@RequestMapping("/searchAllEmpList.do")
	public void searchAllEmpList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String searchCondition = inData.getVariable("searchCondition").getString();
		String companyCode     = inData.getVariable("companyCode").getString();
		String workplaceCode   = inData.getVariable("workplaceCode").getString();
		String deptCode        = inData.getVariable("deptCode").getString();

		String[] paramArray = null;

		switch (searchCondition) {

		case "ALL":

			paramArray = new String[] { companyCode };
			break;

		case "WORKPLACE":

			paramArray = new String[] { companyCode, workplaceCode };
			break;

		case "DEPT":

			paramArray = new String[] { companyCode, deptCode };
			break;

		case "RETIREMENT":

			paramArray = new String[] { companyCode };
			break;

		}
		EmployeeBasicTO employeeBasicTO = new EmployeeBasicTO();
		ArrayList<EmpInfoTO> empList = hrSF.getAllEmpList(searchCondition, paramArray);
		datasetBeanMapper.beansToDataset(outData, empList, EmpInfoTO.class);

	}
	
	/* EmpRegister.xfdl -> 직원 조회 그리드의 직원 한 명 클릭시 발생 */
	@RequestMapping("/searchEmpInfo.do")
	public void searchEmpInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String empCode       = inData.getVariable("empCode").getString();
		String companyCode   = inData.getVariable("companyCode").getString();

		EmpInfoTO empInfoTO  = hrSF.getEmpInfo(companyCode, empCode);
		
		ArrayList<EmployeeDetailTO> empDetailList = empInfoTO.getEmpDetailTOList();
		ArrayList<EmployeeSecretTO> empSecretList = empInfoTO.getEmpSecretTOList();
		
		EmployeeBasicTO employeeBasicTO = new EmployeeBasicTO();
		employeeBasicTO.setCompanyCode(empInfoTO.getCompanyCode());
		employeeBasicTO.setEmpCode(empInfoTO.getEmpCode());
		employeeBasicTO.setEmpName(empInfoTO.getEmpName());
		employeeBasicTO.setEmpEngName(empInfoTO.getEmpEngName());
		employeeBasicTO.setSocialSecurityNumber(empInfoTO.getSocialSecurityNumber());
		employeeBasicTO.setHireDate(empInfoTO.getHireDate());
		employeeBasicTO.setUserOrNot(empInfoTO.getUserOrNot());
		employeeBasicTO.setBirthDate(empInfoTO.getBirthDate());
		employeeBasicTO.setGender(empInfoTO.getGender());
		
		
	System.out.println("		@@@@@@@@ empDetailList: "+empDetailList);
	System.out.println("		@@@@@@@@ empSecretList: "+empSecretList);
	System.out.println("		@@@@@@@@ employeeBasicTO: "+employeeBasicTO);

		datasetBeanMapper.beanToDataset(outData, employeeBasicTO, EmployeeBasicTO.class);
		datasetBeanMapper.beansToDataset(outData, empDetailList, EmployeeDetailTO.class);
		datasetBeanMapper.beansToDataset(outData, empSecretList, EmployeeSecretTO.class);
	}
	@RequestMapping("/checkUserIdDuplication.do")
	public ModelMap checkUserIdDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newUserId = request.getParameter("newUseId");

		try {

			Boolean flag = hrSF.checkUserIdDuplication(companyCode, newUserId);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	@RequestMapping("/checkEmpCodeDuplication.do")
	public ModelMap checkEmpCodeDuplication(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = request.getParameter("newEmpCode");

		try {

			Boolean flag = hrSF.checkEmpCodeDuplication(companyCode, newEmpCode);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	@RequestMapping("/getNewEmpCode.do")
	public ModelMap getNewEmpCode(HttpServletRequest request, HttpServletResponse response) {

		String companyCode = request.getParameter("companyCode");
		String newEmpCode = null;

		try {

			newEmpCode = hrSF.getNewEmpCode(companyCode);

			modelMap.put("newEmpCode", newEmpCode);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}
	
	@RequestMapping("/batchListProcess.do")
	public ModelMap batchListProcess(HttpServletRequest request, HttpServletResponse response) {

		String batchList = request.getParameter("batchList");
		String tableName = request.getParameter("tableName");
		System.out.print("batchList"+batchList);
		System.out.print("tableName"+tableName);

		try {

			ArrayList<EmployeeBasicTO> empBasicList = null;
			ArrayList<EmployeeDetailTO> empDetailList = null;
			ArrayList<EmployeeSecretTO> empSecretList = null;

			HashMap<String, Object> resultMap = null;

			if (tableName.equals("BASIC")) {

				empBasicList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeBasicTO>>() {
				}.getType());

				resultMap = hrSF.batchEmpBasicListProcess(empBasicList);

			} else if (tableName.equals("DETAIL")) {

				empDetailList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeDetailTO>>() {
				}.getType());
				System.out.println(gson.toJson(empDetailList));
				
				resultMap = hrSF.batchEmpDetailListProcess(empDetailList);

			} else if (tableName.equals("SECRET")) {

				empSecretList = gson.fromJson(batchList, new TypeToken<ArrayList<EmployeeSecretTO>>() {
				}.getType());

				System.out.println(gson.toJson(empSecretList));

				
				resultMap = hrSF.batchEmpSecretListProcess(empSecretList);

			}

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

}
