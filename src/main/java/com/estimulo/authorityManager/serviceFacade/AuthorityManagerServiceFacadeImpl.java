package com.estimulo.authorityManager.serviceFacade;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.authorityManager.applicationService.LogInApplicationService;
import com.estimulo.authorityManager.applicationService.UserMenuApplicationService;
import com.estimulo.authorityManager.exception.IdNotFoundException;
import com.estimulo.authorityManager.exception.PwMissMatchException;
import com.estimulo.authorityManager.exception.PwNotFoundException;
import com.estimulo.hr.to.EmpInfoTO;

@Service
public class AuthorityManagerServiceFacadeImpl implements AuthorityManagerServiceFacade {

	// AS 참조변수 선언
	@Autowired
	private LogInApplicationService logInAS;
	@Autowired
	private UserMenuApplicationService userMenuAS;	
	
	@Override
	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException {
			//Auto Commit 막아주는 아이  

		return logInAS.accessToAuthority(companyCode, workplaceCode, inputId, inputPassWord);
	}

	@Override
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {

		return userMenuAS.getUserMenuCode(workplaceCode, deptCode, positionCode, application);
	}

}
