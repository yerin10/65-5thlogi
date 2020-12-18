package com.estimulo.logistics.sales.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.sales.serviceFacade.SalesServiceFacade;

import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.logistics.sales.to.EstimateTO;

import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/business/*")
public class EstimateController {

	@Autowired
	private SalesServiceFacade salesSF;
	
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;

	


	/* estimate.xfdl -> 견적조회 버튼 */
	@RequestMapping("/searchEstimate.do")	
	public void searchEstimateInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println(">>????");
		PlatformData inData         = (PlatformData) request.getAttribute("inData");
		PlatformData outData        = (PlatformData) request.getAttribute("outData");
		System.out.println("inData::"+inData);
		/* dateSearchCondition: 견적일자 or 유효일자 */

		String dateSearchCondition  = inData.getVariable("dateSearchCondition").getString();
	
		String startDate            = inData.getVariable("startDate").getString();
	
		String endDate              = inData.getVariable("endDate").getString();
		
		System.out.println("엥?");
		ArrayList<EstimateTO> estimateTOList = salesSF.getEstimateList(dateSearchCondition, startDate, endDate);
		List<EstimateDetailTO> estimateDetailTOList= new ArrayList<>();

	
	
		for(int i=0; i<estimateTOList.size();i++) {
			for(int n=0; n<estimateTOList.get(i).getEstimateDetailTOList().size(); n++) {
				estimateDetailTOList.add(estimateTOList.get(i).getEstimateDetailTOList().get(n));
			}
		}
		//estimateDetailTOList=estimateTOList.get(0).getEstimateDetailTOList().get(0);
		//System.out.println("estimateDetailTOList:222:::"+estimateDetailTOList);
		 
		
		datasetBeanMapper.beansToDataset(outData, estimateTOList, EstimateTO.class);
		datasetBeanMapper.beansToDataset(outData, estimateDetailTOList, EstimateDetailTO.class);
		
		
	}



	@RequestMapping("/addNewEstimate.do")
	public void addNewEstimate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlatformData inData =(PlatformData)request.getAttribute("inData");
		String estimateDate = inData.getVariable("estimateDate").getString();
		EstimateTO newEstimateTO = datasetBeanMapper.datasetToBean(inData, EstimateTO.class);
		System.out.println("1333331");
		List<EstimateDetailTO> estimateDetailTOList = datasetBeanMapper.datasetToBeans(inData, EstimateDetailTO.class);
		System.out.println("1222222211");
			newEstimateTO.setEstimateDetailTOList(estimateDetailTOList);
				System.out.println("1211");
				salesSF.addNewEstimate(estimateDate, newEstimateTO);
		

		
	}

	@RequestMapping("/batchEstimateDetailListProcess.do")
	public void batchListProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

			PlatformData inData = (PlatformData) request.getAttribute("inData");
			
			ArrayList<EstimateDetailTO> batchEsDetailList = (ArrayList<EstimateDetailTO>) datasetBeanMapper.datasetToBeans(inData, EstimateDetailTO.class);
			//System.out.println("        @ esDetailTO: "+batchEsDetailList);
			salesSF.batchEstimateDetailListProcess(batchEsDetailList);
		}
}
