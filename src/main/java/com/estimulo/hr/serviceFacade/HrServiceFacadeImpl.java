package com.estimulo.hr.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.hr.applicationService.EmpApplicationService;
import com.estimulo.hr.to.EmpInfoTO;
import com.estimulo.hr.to.EmployeeBasicTO;
import com.estimulo.hr.to.EmployeeDetailTO;
import com.estimulo.hr.to.EmployeeSecretTO;

@Service
public class HrServiceFacadeImpl implements HrServiceFacade {

	@Autowired
	private EmpApplicationService empAS;

	@Override
	public ArrayList<EmpInfoTO> getAllEmpList(String searchCondition, String[] paramArray) { 

		return empAS.getAllEmpList(searchCondition, paramArray);
	}

	@Override
	public EmpInfoTO getEmpInfo(String companyCode, String empCode) {

		return empAS.getEmpInfo(companyCode, empCode);
	}


	@Override
	public String getNewEmpCode(String companyCode) {

		return empAS.getNewEmpCode(companyCode);
	}
	
	@Override
	public HashMap<String, Object> batchEmpBasicListProcess(ArrayList<EmployeeBasicTO> empBasicList) {

		return empAS.batchEmpBasicListProcess(empBasicList);

	}

	@Override
	public HashMap<String, Object> batchEmpDetailListProcess(ArrayList<EmployeeDetailTO> empDetailList) {

		return empAS.batchEmpDetailListProcess(empDetailList);
	}

	@Override
	public HashMap<String, Object> batchEmpSecretListProcess(ArrayList<EmployeeSecretTO> empSecretList) {

		return empAS.batchEmpSecretListProcess(empSecretList);
	}

	@Override
	public Boolean checkUserIdDuplication(String companyCode, String newUserId) {

		return empAS.checkUserIdDuplication(companyCode, newUserId);
	}

	@Override
	public Boolean checkEmpCodeDuplication(String companyCode, String newEmpCode) {

		return empAS.checkEmpCodeDuplication(companyCode, newEmpCode);
	}


}
