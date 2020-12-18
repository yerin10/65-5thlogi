package com.estimulo.authorityManager.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.estimulo.authorityManager.exception.IdNotFoundException;
import com.estimulo.authorityManager.exception.PwMissMatchException;
import com.estimulo.authorityManager.exception.PwNotFoundException;
import com.estimulo.hr.dao.EmpSearchingDAO;
import com.estimulo.hr.dao.EmployeeSecretDAO;
import com.estimulo.hr.to.EmpInfoTO;
import com.estimulo.hr.to.EmployeeSecretTO;

@Component
public class LogInApplicationServiceImpl implements LogInApplicationService {

	// DAO 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private EmpSearchingDAO empSearchDAO;
	@Autowired
	private EmployeeSecretDAO empSecretDAO;

	public EmpInfoTO accessToAuthority(String companyCode, String workplaceCode, String inputId, String inputPassWord)
			throws IdNotFoundException, PwMissMatchException, PwNotFoundException, DataAccessException {

			EmpInfoTO bean = null;

			bean = checkEmpInfo(companyCode, workplaceCode, inputId);	// 데이터 베이스 에서 우리가 로그인 화면에서 입력한 값을 보내줘서 비교한후 있으면 들고와서 그사람의 정보를 bean 에 담는다
			checkPassWord(companyCode, bean.getEmpCode(), inputPassWord); // 비밀번호를 확인 해주는 메서드 
				
			return bean;
		}

		private EmpInfoTO checkEmpInfo(String companyCode, String workplaceCode, String inputId)
			throws IdNotFoundException {

			EmpInfoTO bean = null;
			HashMap<String, String> param = new HashMap<>();

			param.put("companyCode", companyCode);
			param.put("workplaceCode", workplaceCode);
			param.put("userId", inputId);

			ArrayList<EmpInfoTO> empInfoTOList = empSearchDAO.getTotalEmpInfo(param);

			if (empInfoTOList.size() == 1) {

				for (EmpInfoTO e : empInfoTOList) {
					bean = e;
				}

			} else if (empInfoTOList.size() == 0) {
				throw new IdNotFoundException("입력된 정보에 해당하는 사원은 없습니다.");
			}

			return bean;
		}

		private void checkPassWord(String companyCode, String empCode, String inputPassWord)
			throws PwMissMatchException, PwNotFoundException {
			
			HashMap<String, String> param = new HashMap<>();
			
			param.put("companyCode", companyCode);
			param.put("empCode", empCode);
			
			EmployeeSecretTO bean = empSecretDAO.selectUserPassWord(param);

			StringBuffer userPassWord = new StringBuffer();
			if (bean != null) {
				userPassWord.append(bean.getUserPassword());

				// 회원ID 는 있으나 passWord Data 가 없는 경우
			} else if (bean == null || bean.getUserPassword().equals("") || bean.getUserPassword() == null) {
				throw new PwNotFoundException("비밀번호 정보를 찾을 수 없습니다.");
			}

			if (!inputPassWord.equals(userPassWord.toString())) {
				throw new PwMissMatchException("비밀번호가 가입된 정보와 같지 않습니다.");
			}

		}

		}

