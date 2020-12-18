package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.sales.dao.ContractDAO;
import com.estimulo.logistics.sales.dao.ContractDetailDAO;
import com.estimulo.logistics.sales.dao.EstimateDAO;
import com.estimulo.logistics.sales.dao.EstimateDetailDAO;
import com.estimulo.logistics.sales.repository.ContractRepository;
import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateTO;

@Component
public class ContractApplicationServiceImpl implements ContractApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private ContractDAO contractDAO;
	@Autowired
	private ContractDetailDAO contractDetailDAO;
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired
	private EstimateDetailDAO estimateDetailDAO;
	@Autowired
	private ContractRepository contractRepository;
	
	public ArrayList<ContractInfoTO> getContractList(String searchCondition, String[] paramArray) {
		
		ArrayList<ContractInfoTO> contractInfoTOList = null;
		HashMap<String, String> param = new HashMap<>();
			switch (searchCondition) {

			case "searchByDate":

				String startDate = paramArray[0];
				String endDate = paramArray[1];
				param.put("startDate", startDate);
				param.put("endDate", endDate);

				contractInfoTOList = contractDAO.selectContractListByDate(param);

				break;

			case "searchByCustomer":

				String customerCode = paramArray[0];

				contractInfoTOList = contractDAO.selectContractListByCustomer(customerCode);

				break;

			}
			/*
			 * for (ContractInfoTO bean : contractInfoTOList) {
			 * 
			 * bean.setContractDetailTOList(contractDetailDAO.selectContractDetailList(bean.
			 * getContractNo()));
			 * 
			 * }
			 */

		return contractInfoTOList;

	}

	public ArrayList<ContractInfoTO> getDeliverableContractList(String searchCondition, String[] paramArray) {

		ArrayList<ContractInfoTO> contractInfoTOList = null;
		HashMap<String, String> param = new HashMap<>();
			switch (searchCondition) {

			case "searchByDate":

				String startDate = paramArray[0];
				String endDate = paramArray[1];
				param.put("startDate", startDate);
				param.put("endDate", endDate);

				contractInfoTOList = contractDAO.selectDeliverableContractListByDate(param);

				break;

			case "searchByCustomer":

				String customerCode = paramArray[0];

				contractInfoTOList = contractDAO.selectDeliverableContractListByCustomer(customerCode);

				break;

			}

		
		return contractInfoTOList;

	}
	
	@Override
	public ArrayList<ContractDetailTO> getContractDetailList(String contractNo) {

		return contractDetailDAO.selectContractDetailList(contractNo);
	}

	@Override
	public ArrayList<EstimateTO> getEstimateListInContractAvailable(String startDate, String endDate) {
		System.out.println("ㄱㄱㄱ?");
		ArrayList<EstimateTO> estimateListInContractAvailable = null;
		HashMap<String, String> param = new HashMap<>();
			param.put("startDate", startDate);
			param.put("endDate", endDate);
		
			estimateListInContractAvailable = contractDAO.selectEstimateListInContractAvailable(param);
			//estimateListInContractAvailable = EstimateListInContractAvailable
			/*
			 * sysout for (EstimateTO bean : estimateListInContractAvailable) {
			 * 
			 * bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean)
			 * );
			 * 
			 * }
			 */
		return estimateListInContractAvailable;
	}

	@Override
	public String getNewContractNo(String contractDate) {

		StringBuffer newContractNo = null;

			int i = contractDAO.selectContractCount(contractDate) + 1;
			newContractNo = new StringBuffer();
			newContractNo.append("CO");
			newContractNo.append(contractDate.replace("-", ""));
			newContractNo.append(String.format("%02d", i));	//CO + contractDate + 01

		return newContractNo.toString();
	}

	@Override
	public HashMap<String, Object> addNewContract(String contractDate, String personCodeInCharge,
			ContractTO workingContractBean) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		String newContractNo = getNewContractNo(contractDate);//CO + contractDate + 01 <= 01은 첫번째라는 뜻 2번째이며 02 로 부여가 됨

		workingContractBean.setContractNo(newContractNo); // 새로운 수주일련번호 세팅
		workingContractBean.setContractDate(contractDate); // 뷰에서 전달한 수주일자 세팅
		workingContractBean.setPersonCodeInCharge(personCodeInCharge); // 뷰에서 전달한 수주담당자코드 세팅

						contractRepository.save(workingContractBean);
			
			// 견적 테이블 수주상태 Y로바꿈
			changeContractStatusInEstimate(workingContractBean.getEstimateNo(), "Y");

		HashMap<String, String> param = new HashMap<>();
			param.put("estimateNo", workingContractBean.getEstimateNo());
			param.put("contractNo", newContractNo);
			//ES ... 견적일련번호 , CO ... 수주일련번호
			contractDetailDAO.insertContractDetail(param);
			resultMap.put("errorCode",param.get("ERROR_CODE"));
			resultMap.put("errorMsg", param.get("ERROR_MSG"));  

		return resultMap;
	}

	@Override
	public HashMap<String, Object> batchContractDetailListProcess(ArrayList<ContractDetailTO> contractDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			for (ContractDetailTO bean : contractDetailTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					/*contractDetailDAO.insertContractDetail(bean);*/
					insertList.add(bean.getContractDetailNo());

					break;

				case "UPDATE":

					/*contractDetailDAO.updateContractDetail(bean);*/
					updateList.add(bean.getContractDetailNo());

					break;

				case "DELETE":

					contractDetailDAO.deleteContractDetail(bean);
					deleteList.add(bean.getContractDetailNo());

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;
	}

	public void changeContractStatusInEstimate(String estimateNo, String contractStatus) {
			
		HashMap<String, String> param = new HashMap<>();
			param.put("estimateNo", estimateNo);
			param.put("contractStatus", contractStatus);
		
		estimateDAO.changeContractStatusOfEstimate(param);

	}

}
