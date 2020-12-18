package com.estimulo.logistics.production.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estimulo.basicInfo.to.WorkplaceTO;
import com.estimulo.common.mapper.DatasetBeanMapper;
import com.estimulo.logistics.production.serviceFacade.ProductionServiceFacade;

import com.estimulo.logistics.production.to.ProductionPerformanceInfoTO;
import com.estimulo.logistics.production.to.WorkOrderInfoTO;
import com.estimulo.logistics.production.to.WorkOrderSimulationTO;
import com.estimulo.logistics.production.to.WorkOrderableMrpListTO;
import com.estimulo.logistics.production.to.WorkSiteLog;
import com.estimulo.logistics.production.to.WorkSiteSimulationTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/production/*")
public class WorkOrderController {
	
	@Autowired
	private ProductionServiceFacade productionSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	// gson 라이브러리
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // 속성값이 null 인 속성도 json 변환
	
	private ModelMap modelMap = new ModelMap();
	
	@RequestMapping("/getWorkOrderableMrpList.do")
	@SuppressWarnings("unchecked")
	public void getWorkOrderableMrpList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		HashMap<String, Object> resultMap = productionSF.getWorkOrderableMrpList();
			
		List<WorkOrderableMrpListTO> workOrderableMrpList=(List<WorkOrderableMrpListTO>)resultMap.get("gridRowJson");
		System.out.println("        @ gridRowJson: "+workOrderableMrpList);
		
		datasetBeanMapper.beansToDataset(outData, workOrderableMrpList, WorkOrderableMrpListTO.class);
	}


	/* WorkInstruction.xfdl -> 모의 작업지시 (BY MRP) -> dialog_workInstruction_simulation.xfdl */
	@RequestMapping("/showWorkOrderDialog.do")
	@SuppressWarnings("unchecked")
	public void showWorkOrderDialog(HttpServletRequest request, HttpServletResponse response) throws Exception{

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		String mrpNo         = inData.getVariable("MRP_NO").getString();
		HashMap<String,Object> resultMap                = productionSF.getWorkOrderSimulationList(mrpNo);
		List<WorkOrderSimulationTO> workOrderSimulation =(List<WorkOrderSimulationTO>)resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(outData, workOrderSimulation, WorkOrderSimulationTO.class);

	}
	/*   WorkInstruction.xfdl -> dialog_workInstruction_simulation.xfdl -> 현재 모의 전개된 결과 작업지시 */
	@RequestMapping("/workOrder.do")
	public void workOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData      = (PlatformData) request.getAttribute("inData");
		
		String workPlaceCode     = datasetBeanMapper.datasetToBean(inData, WorkplaceTO.class).getWorkplaceCode();
		
		String productionProcess = inData.getVariable("productionProcessCode").getString();
		System.out.println(productionProcess+"::::::productionProcess");
		System.out.println(workPlaceCode+"::::workPlaceCode");
		productionSF.workOrder(workPlaceCode,productionProcess);

	}

	/* WorkInstruction.xfdl -> 작업시지 현황 -> 작업지시 현황 조회 */
	@RequestMapping("/showWorkOrderInfoList.do")
	public void showWorkOrderInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData outData      = (PlatformData) request.getAttribute("outData");
		ArrayList<WorkOrderInfoTO> workOrderInfoList = productionSF.getWorkOrderInfoList();
		datasetBeanMapper.beansToDataset(outData, workOrderInfoList, WorkOrderInfoTO.class);
	}
	
	/* WorkInstruction.xfdl -> 작업시지 현황 -> 작업완료 등록 */
	@RequestMapping("/workOrderCompletion.do")
	public void workOrderCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");

		
		String workOrderNo            = inData.getVariable("workOrderNo").getString();
		System.out.println("        @ workOrderNo: "+workOrderNo);
		String actualCompletionAmount            = inData.getVariable("actualCompletionAmount").getString();
		System.out.println("        @ actualCompletionAmount: "+actualCompletionAmount);

		productionSF.workOrderCompletion(workOrderNo,actualCompletionAmount);

		
	}
	
	
	@RequestMapping("/getProductionPerformanceInfoList.do")
	public void getProductionPerformanceInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		List<ProductionPerformanceInfoTO> productionPerformanceInfoList = productionSF.getProductionPerformanceInfoList();
		datasetBeanMapper.beansToDataset(outData, productionPerformanceInfoList, ProductionPerformanceInfoTO.class);
	}
	
	
	/* WorkSite.xfdl -> 원재료 검사, 제품제작, 판매제품 검사 -> WorkSiteSituation.xfdl(onload) */
	@RequestMapping("/showWorkSiteSituation.do")
	@SuppressWarnings("unchecked")
	public void showWorkSiteSituation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String workSiteCourse = inData.getVariable("workSiteCourse").getString();
		String workOrderNo = inData.getVariable("workOrderNo").getString();
		String itemClassIfication = inData.getVariable("itemClassIfication").getString();

		System.out.println("        @ workSiteCourse: "+workSiteCourse);
		System.out.println("        @ workOrderNo: "+workOrderNo);
		System.out.println("        @ itemClassIfication: "+itemClassIfication);
		
		HashMap<String, Object> resultMap = productionSF.showWorkSiteSituation(workSiteCourse,workOrderNo,itemClassIfication);

	
		ArrayList<WorkSiteSimulationTO> workSiteSimulList = (ArrayList<WorkSiteSimulationTO>) resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(outData, workSiteSimulList, WorkSiteSimulationTO.class);

	}
	
	
	/* WorkSiteSituation.xfdl -> 검사 및 제작완료 */
	@RequestMapping("/workCompletion.do")
	public void workCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		
		String workOrderNo = inData.getVariable("workOrderNo").getString();
		String itemCode = inData.getVariable("itemCode").getString();
		String itemCodeList = inData.getVariable("itemCodeList").getString();
		System.out.println("itemCodeList::::"+itemCodeList);
		System.out.println("itemCode::::"+itemCode);
		System.out.println("workOrderNo::::"+workOrderNo);


		ArrayList<String> itemCodeListArr = gson.fromJson(itemCodeList, new TypeToken<ArrayList<String>>() {}.getType());

		productionSF.workCompletion(workOrderNo,itemCode,itemCodeListArr);

	}
	
	
	/* WorkSiteSituation.xfdl -> 검사 및 제작완료 후 -> Tab00_tabpage2_Button00_onclick 자동 실행 */
	@RequestMapping("/workSiteLog.do")
	@SuppressWarnings("unchecked")
	public void workSiteLogList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		PlatformData inData  = (PlatformData) request.getAttribute("inData");
		PlatformData outData = (PlatformData) request.getAttribute("outData");
		
		String workSiteLogDate = inData.getVariable("workSiteLogDate").getString();
		
		HashMap<String, Object> resultMap=productionSF.workSiteLogList(workSiteLogDate);

		/*
		Set<String> set = resultMap.keySet();
		for(String v:set) {
			System.out.println("        @@@@@ key: "+v+"  ,"+" value: "+resultMap.get(v));
		}
		*/
		
		ArrayList<WorkSiteLog> workSiteLogList = (ArrayList<WorkSiteLog>) resultMap.get("gridRowJson");
		
		datasetBeanMapper.beansToDataset(outData, workSiteLogList, WorkSiteLog.class);
	}
	
}