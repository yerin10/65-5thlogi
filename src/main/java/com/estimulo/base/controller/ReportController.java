package com.estimulo.base.controller;

import java.util.ArrayList;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.estimulo.base.serviceFacade.BaseServiceFacade;
import com.estimulo.base.to.ContractReportTO;
import com.estimulo.base.to.EstimateReportTO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/base/*")
public class ReportController {

	private ModelAndView modelAndView;
	//private ModelMap modelMap = new ModelMap();
	
	@Autowired
	private BaseServiceFacade baseSF;
	
	
	
	@RequestMapping(value = "/report.pdf", params = "method=estimateReport")
	public ModelAndView estimateReport(HttpServletRequest request, HttpServletResponse response) {
		
		String estimateNo = request.getParameter("orderDraftNo");
		
		try {
			
			ArrayList<EstimateReportTO> estimateList = baseSF.getEstimateReport(estimateNo);
			File file = ResourceUtils.getFile("classpath:Estimate.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(estimateList);
			Map<String, Object> parameters = new HashMap<>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;
	}

	@RequestMapping(value = "/report.pdf", params = "method=contractReport")
	public ModelAndView contractReport(HttpServletRequest request, HttpServletResponse response) {

		String contractNo = request.getParameter("orderDraftNo");

		try {

			ArrayList<ContractReportTO> contractList = baseSF.getContractReport(contractNo);
			
			File file = ResourceUtils.getFile("classpath:Contract.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(contractList);
			Map<String, Object> parameters = new HashMap<>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

		} catch (Exception e) {

			e.printStackTrace();
		}

		return modelAndView;
	}
}