package com.estimulo.base.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.estimulo.base.serviceFacade.BaseServiceFacade;

import com.estimulo.base.to.AddressTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base/*")
public class AddressController {

	// serviceFacade 참조변수 선언
	@Autowired
	private BaseServiceFacade baseSF;
	
	private ModelMap modelMap = new ModelMap();

	@RequestMapping("/searchAddressList.do")
	public ModelMap searchAddressList(HttpServletRequest request, HttpServletResponse response) {


		String sidoName = request.getParameter("sidoName");
		String searchAddressType = request.getParameter("searchAddressType");
		String searchValue = request.getParameter("searchValue");
		String mainNumber = request.getParameter("mainNumber");


		try {

			ArrayList<AddressTO> addressList = baseSF.getAddressList(sidoName, searchAddressType, searchValue,
					mainNumber);

			modelMap.put("addressList", addressList);
			modelMap.put("errorCode", 1);
			modelMap.put("errorMsg", "성공");

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}

		return modelMap;

	}

}
