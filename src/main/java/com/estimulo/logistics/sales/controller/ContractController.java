package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;

import com.estimulo.logistics.sales.to.ContractDetailTO;
import com.estimulo.logistics.sales.to.ContractInfoTO;
import com.estimulo.logistics.sales.to.ContractTO;
import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.logistics.sales.to.EstimateTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/business/*")
public class ContractController {

	@Autowired
	private SalesServiceFacade salesSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
		
	private ModelMap modelMap = new ModelMap();

	// GSON 占쎌뵬占쎌뵠�뇡�슢�쑎�뵳占�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 占쎈꺗占쎄쉐揶쏅�れ뵠 null 占쎌뵥 占쎈꺗占쎄쉐占쎈즲 癰귨옙占쎌넎
	
	@RequestMapping("/searchContract.do")
	public void searchContract(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String searchCondition  = inData.getVariable("searchCondition").getString();
		String customerCode     = inData.getVariable("customerCode").getString();
		String firstDate        = inData.getVariable("firstDate").getString();
		String endDate          = inData.getVariable("endDate").getString();


		ArrayList<ContractInfoTO> contractInfoTOList     = null;
		ArrayList<ContractDetailTO> contractDetailTOList = new ArrayList<>();
			if (searchCondition.equals("SEARCH_BY_PERIOD")) {

				String[] paramArray = { firstDate, endDate };
				contractInfoTOList = salesSF.getContractList("searchByDate", paramArray);

			} else if (searchCondition.equals("SEARCH_BY_CUSTOMER")) {

				String[] paramArray = { customerCode };
				contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

			}
			for(int i=0; i<contractInfoTOList.size();i++) {
				//System.out.println("		@@@@@@@@1: "+contractInfoTOList.get(i).getContractDetailTOList());
				for(int n=0; n<contractInfoTOList.get(i).getContractDetailTOList().size(); n++) {
					contractDetailTOList.add(contractInfoTOList.get(i).getContractDetailTOList().get(n));
					//System.out.println("		@@@@@@@@2: "+contractDetailTOList);
				}
			}

			datasetBeanMapper.beansToDataset(outData, contractInfoTOList, ContractInfoTO.class);
			datasetBeanMapper.beansToDataset(outData, contractDetailTOList, ContractDetailTO.class);
		
	}

//	占쎌삂占쎈씜餓ο옙	
	public ModelMap searchContractNO(HttpServletRequest request, HttpServletResponse response) {

		String searchCondition = request.getParameter("searchCondition");

			ArrayList<ContractInfoTO> contractInfoTOList = null;
			if (searchCondition.equals("searchByDate")) {
				String customerCode = "";
				String[] paramArray = { customerCode };
				contractInfoTOList = salesSF.getContractList("searchByCustomer", paramArray);

			}

			modelMap.put("gridRowJson", contractInfoTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");
		
		return modelMap;
	}

//	占쎌삂占쎈씜占쎄국
	@RequestMapping("/searchContractDetail.do")
	public ModelMap searchContractDetail(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("contractNo");

			ArrayList<ContractDetailTO> contractDetailTOList = salesSF.getContractDetailList(contractNo);

			modelMap.put("gridRowJson", contractDetailTOList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "占쎄쉐�⑨옙");

		return modelMap;
	}

	@RequestMapping("/searchEstimateInContractAvailable.do")
	public void searchEstimateInContractAvailable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			System.out.println("dD?");
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		String startDate     = inData.getVariable("startDate").getString();
		String endDate       = inData.getVariable("endDate").getString();
			ArrayList<EstimateTO> estimateListInContractAvailable = salesSF.getEstimateListInContractAvailable(startDate, endDate);
			ArrayList<EstimateDetailTO> estimateDetailTOList = new ArrayList<>();
		      
			for(int i=0; i<estimateListInContractAvailable.size();i++) {
				//System.out.println("              @@@@@@@@1: "+estimateTOList.get(i).getEstimateDetailTOList());
				for(int n=0; n<estimateListInContractAvailable.get(i).getEstimateDetailTOList().size(); n++) {
					estimateDetailTOList.add(estimateListInContractAvailable.get(i).getEstimateDetailTOList().get(n));
					//System.out.println("              @@@@@@@@2: "+estimateDetailTOList);
				}
			}

			datasetBeanMapper.beansToDataset(outData, estimateListInContractAvailable, EstimateTO.class);
			datasetBeanMapper.beansToDataset(outData, estimateDetailTOList, EstimateDetailTO.class);
		
	}

	@RequestMapping("/addNewContract.do")
	public void addNewContract(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData       = (PlatformData) request.getAttribute("inData"); 	
		String personCodeInCharge = inData.getVariable("personCodeInCharge").getString(); //EMP-01
		String contractDate       = inData.getVariable("contractDate").getString(); //오늘날짜
		

		ContractTO workingContractTO=datasetBeanMapper.datasetToBean(inData, ContractTO.class);
		List<ContractDetailTO> contractDetailTOList=datasetBeanMapper.datasetToBeans(inData, ContractDetailTO.class);
		workingContractTO.setContractDetailTOList(contractDetailTOList);

			salesSF.addNewContract(contractDate, personCodeInCharge,workingContractTO);

		
	}

	/* ContractRegister.xfdl -> 견적 취소 */
	@RequestMapping("/cancelEstimate.do")
	public void cancelEstimate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PlatformData inData = (PlatformData) request.getAttribute("inData");
		String estimateNo= inData.getVariable("estimateNo").getString();
		System.out.println("        @ estimateNo: "+estimateNo);
		salesSF.changeContractStatusInEstimate(estimateNo, "N");
	}
}