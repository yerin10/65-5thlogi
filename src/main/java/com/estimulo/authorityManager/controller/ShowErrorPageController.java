package com.estimulo.authorityManager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowErrorPageController {
	
	private ModelAndView modelAndView;
	private ModelMap modelMap = new ModelMap();

	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) {


		String viewName = "redirect:" + request.getContextPath() + "/hello.html";


		if (request.getRequestURI().contains("accessDenied")) {
			modelMap.put("errorCode", -1);
			modelMap.put("errorTitle", "Access Denied");
			modelMap.put("errorMsg", "액세스 거부되었습니다");
			viewName = "errorPage";
		}

		modelAndView = new ModelAndView(viewName, modelMap);

		return modelAndView;
	}

}
