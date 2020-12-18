package com.estimulo.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import com.tobesoft.xplatform.data.DataSetList;
import com.tobesoft.xplatform.data.Debugger;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformRequest;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;
import com.tobesoft.xplatform.tx.PlatformType;
@Component
public class XplatformInterceptor extends HandlerInterceptorAdapter {

	/* 전처리 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	// HTTP 요청으로부터 데이터(PlatformData)를 수신받는다.
    	System.out.println("dd?zzzqq");
        HttpPlatformRequest httpPlatformRequest = new HttpPlatformRequest(request);
        
        /*
		송수신 형식(contentType)이 설정되지 않은 경우 HTTP 헤더의 ContentType 값으로부터 판단하며, 다음과 같이 적용된다.
		HTTP 헤더의 ContentType		적용되는 송수신 형식(contentType)
		text/xml					PlatformType.CONTENT_TYPE_XML
		application/octet-stream	PlatformType.CONTENT_TYPE_BINARY
		그 외							PlatformType.DEFAULT_CONTENT_TYPE
		*/
        httpPlatformRequest.receiveData();

        // 엑플에서 transaction 요청할 때, 입력값으로 보낼 Dataset의 ID들
        PlatformData inData = httpPlatformRequest.getData();
        
        // transaction 처리 결과를 받을 Dataset의 ID들
        PlatformData outData = new PlatformData();

        debug(inData.getDataSetList(), inData.getVariableList());

        request.setAttribute("inData", inData);
        request.setAttribute("outData", outData);

        System.out.println("		@XplatformInterceptor 의 preHandle 성공(true)");
        return true;
    }

    /* 컨트롤러 진입 후 view가 랜더링 되기 전 수행이 됩니다. */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	
    	// modelAndView == XplatformViewResolver ?
    	System.out.println("		@XplatformInterceptor 의 postHandle 성공(true)");
    	super.postHandle(request, response, handler, modelAndView);
    }

    
    /* 컨트롤러 진입 후 view가 정상적으로 랜더링 된 후 제일 마지막에 실행이 되는 메서드입니다. */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        PlatformData outData = (PlatformData) request.getAttribute("outData");

        /* 강의에서 사용안한다고 말함. 이유= outData가 없을 수 없다는데 이해 x.
        if(outData==null){
        	return;
        }
        */

        // 단일 데이터를 가지고 있는 Variable들을 저장한다. Variable은 식별자(name) 또는 위치(index)를 통하여 참조할 수 있다.
        VariableList variableList = outData.getVariableList();

        if (exception != null) {
            variableList.add("ErrorCode", -1);
            variableList.add("ErrorMsg", exception.getMessage());
        } else {
            variableList.add("ErrorCode", 0);
            variableList.add("ErrorMsg", "success");
        }

        // HTTP 응답으로 데이터(PlatformData)를 송신한다.
        // HttpServletResponse, 송수신 형식(contentType)과 문자셋(charset)을 가지는 생성자이다.
        HttpPlatformResponse httpPlatformResponse = new HttpPlatformResponse(response, PlatformType.CONTENT_TYPE_XML, "UTF-8");

        httpPlatformResponse.setData(outData);
        httpPlatformResponse.sendData();

        debug(outData.getDataSetList(), outData.getVariableList());

        outData = null;
        super.afterCompletion(request, response, handler, exception);
    }

    private void debug(DataSetList dataSetList, VariableList variableList) {
        Debugger debugger = new Debugger();
        
        // DEBUG - DataSet
        int dataSetListSize = dataSetList.size();
        for (int n = 0; n < dataSetListSize; n++) {
        	
        	// 개발시에 유용한 DataSetList의 자세한 정보를 반환한다.
            System.out.println(debugger.detail(dataSetList.get(n)));
        }
        
        // DEBUG - VariableList
        int variableListSize = variableList.size();
        for (int n = 0; n < variableListSize; n++) {
        	
        	// 개발시에 유용한 VariableList의 자세한 정보를 반환한다.
            System.out.println(debugger.detail(variableList.get(n)));
        }
    }
}