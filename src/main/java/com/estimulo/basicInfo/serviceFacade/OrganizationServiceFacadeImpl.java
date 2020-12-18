package com.estimulo.basicInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.estimulo.basicInfo.applicationService.CompanyApplicationService;
import com.estimulo.basicInfo.applicationService.DepartmentApplicationService;
import com.estimulo.basicInfo.applicationService.WorkplaceApplicationService;
import com.estimulo.basicInfo.to.CompanyTO;
import com.estimulo.basicInfo.to.DepartmentTO;
import com.estimulo.basicInfo.to.WorkplaceTO;

@Service
public class OrganizationServiceFacadeImpl implements OrganizationServiceFacade {
	
	@Autowired
	private CompanyApplicationService companyAS;
	@Autowired
	private WorkplaceApplicationService workplaceAS;
	@Autowired
	private DepartmentApplicationService deptAS;
	
	@Override
	public ArrayList<CompanyTO> getCompanyList() {

		return companyAS.getCompanyList();
	}

	@Override
	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {

		ArrayList<WorkplaceTO> workplaceList = null;

		try {

			workplaceList = workplaceAS.getWorkplaceList(companyCode);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return workplaceList;
	}

	@Override
	public ArrayList<DepartmentTO> getDepartmentList(String searchCondition, String companyCode,
			String workplaceCode) {

		ArrayList<DepartmentTO> departmentList = null;

		try {

			departmentList = deptAS.getDepartmentList(searchCondition, companyCode, workplaceCode);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return departmentList;
	}

	@Override
	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

		HashMap<String, Object> resultMap = null;

		try {

			resultMap = companyAS.batchCompanyListProcess(companyList);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {

		HashMap<String, Object> resultMap = null;

		try {

			resultMap = workplaceAS.batchWorkplaceListProcess(workplaceList);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchDepartmentListProcess(ArrayList<DepartmentTO> departmentList) {

		HashMap<String, Object> resultMap = null;

		try {

			resultMap = deptAS.batchDepartmentListProcess(departmentList); 

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw e;
		}

		return resultMap;
	}

}
