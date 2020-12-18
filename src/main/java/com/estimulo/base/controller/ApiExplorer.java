package com.estimulo.base.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiExplorer {

	private ModelMap modelMap = new ModelMap();
	
	@RequestMapping(value="/base/openapi*", method=RequestMethod.GET)
	public ModelMap handleRequestInternal() {

        BufferedReader br = null;
        String result=null;
		
		try{
			
			String yesterday = yesterday();
			
			String urlstr = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=79d1f38f60a45970207642f83be879e7&targetDt=" + yesterday;
		    URL url = new URL(urlstr);
		    
		    HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
		    urlconnection.setRequestMethod("GET");
		    br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(),"UTF-8"));
		    
		    result ="";
		    String line;
		    while((line=br.readLine()) != null) {
		    	result = result+line;
		    }
		    
		    
		    modelMap.put("gridRowJson", result);
		    modelMap.put("error_code", 0);	
		    modelMap.put("error_msg", "성공");
		    
		    System.out.println("홈피로 부터 받아온 값 : ");
		    System.out.println(result);
		    
		}catch(Exception e) {
			modelMap.put("error-code", -1);	
			modelMap.put("error-msg", "내부서버오류");	
			e.printStackTrace();
		}
		return modelMap;
	}
	
	public String yesterday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar today = Calendar.getInstance();
		today.add(Calendar.DATE, -1);
	    String yesterday = sdf.format(today.getTime());
        return yesterday;
	}
}
