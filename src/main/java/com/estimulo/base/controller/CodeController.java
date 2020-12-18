package com.estimulo.base.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estimulo.base.serviceFacade.BaseServiceFacade;

import com.estimulo.base.to.CodeDetailTO;
import com.estimulo.base.to.CodeTO;
import com.estimulo.common.mapper.DatasetBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tobesoft.xplatform.data.PlatformData;

@RestController
@RequestMapping("/base/*")
public class CodeController {


	// serviceFacade 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private BaseServiceFacade baseSF;
	@Autowired
	private DatasetBeanMapper datasetBeanMapper;
	
	private ModelMap modelMap = new ModelMap();
	
	// gson �씪�씠釉뚮윭由�
	private static Gson gson = new GsonBuilder().serializeNulls().create(); // �냽�꽦媛믪씠 null �씤 �냽�꽦�룄 json 蹂��솚

	@RequestMapping("/searchCodeList.do")
	public ModelMap findCodeList(HttpServletRequest request, HttpServletResponse response) {

		try {

			ArrayList<CodeTO> codeList = baseSF.getCodeList();

			modelMap.put("codeList", codeList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}

		return modelMap;

	}
	
	@RequestMapping("/codeList.do")
	public void findDetailCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception {


		PlatformData inData = (PlatformData) request.getAttribute("inData");
		PlatformData outData= (PlatformData) request.getAttribute("outData");

		String divisionCode = inData.getVariable("divisionCode").getString();
		System.out.println("###############################divisionCode: "+divisionCode);

		ArrayList<CodeDetailTO> detailCodeList = baseSF.getDetailCodeList(divisionCode);

		datasetBeanMapper.beansToDataset(outData, detailCodeList, CodeDetailTO.class);
	}

	
	public ModelMap checkCodeDuplication(HttpServletRequest request, HttpServletResponse response) {


		String divisionCode = request.getParameter("divisionCode");
		String newDetailCode = request.getParameter("newCode");


		try {

			Boolean flag = baseSF.checkCodeDuplication(divisionCode, newDetailCode);

			modelMap.put("result", flag);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

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

		try {

			ArrayList<CodeTO> codeList = null;
			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			if (tableName.equals("CODE")) {

				codeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeTO>>() {
				}.getType());

				resultMap = baseSF.batchCodeListProcess(codeList);

			} else if (tableName.equals("CODE_DETAIL")) {

				detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
				}.getType());

				resultMap = baseSF.batchDetailCodeListProcess(detailCodeList);

			}

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}

	@RequestMapping("/changeCodeUseCheckProcess.do")
	public ModelMap changeCodeUseCheckProcess(HttpServletRequest request, HttpServletResponse response) {


		String batchList = request.getParameter("batchList");

		try {

			ArrayList<CodeDetailTO> detailCodeList = null;
			HashMap<String, Object> resultMap = null;

			detailCodeList = gson.fromJson(batchList, new TypeToken<ArrayList<CodeDetailTO>>() {
			}.getType());

			resultMap = baseSF.changeCodeUseCheckProcess(detailCodeList);

			modelMap.put("result", resultMap);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "�꽦怨�");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return modelMap;
	}
	
}
