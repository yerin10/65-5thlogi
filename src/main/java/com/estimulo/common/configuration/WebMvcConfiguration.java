package com.estimulo.common.configuration;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.estimulo.common.interceptor.LoggerInterceptor;
import com.estimulo.common.interceptor.LoginInterceptor;
import com.estimulo.common.interceptor.XplatformInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Autowired
	XplatformInterceptor xplatformInterceptor;
	
	//�씤�꽣�뀎�꽣 愿��젴
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoginInterceptor())   //濡쒓렇�씤 �씤�꽣�뀎�꽣
			.addPathPatterns("/*")
			.addPathPatterns("/*/*.html")
			.excludePathPatterns("/*login*")
			.excludePathPatterns("/basicInfo/codeModal*")
			.excludePathPatterns("/*logout*");
			//.addPathPatterns("/*.html").addPathPatterns("**/*.html");   //�씤�꽣�뀎�꽣瑜� �벑濡앺븿.
		
		registry.addInterceptor(new LoggerInterceptor());   //濡쒓퉭 �씤�꽣�뀎�꽣
		registry.addInterceptor(xplatformInterceptor);
		WebMvcConfigurer.super.addInterceptors(registry);
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * registry.addResourceHandler("/ImgServer/**") .addResourceLocations(
		 * "file:///C:/dev/http/apache2.2/htdocs/estimulo4/ImgServer/");
		 */
		registry.addResourceHandler("/**")
		.addResourceLocations("file:///C:/dev/http/apache2.2/htdocs/estimulo4/");

	}
	
	// 2媛쒖쓽 鍮덉� �씤肄붾뵫 愿��젴.
	@Bean
	public Filter characterEncodingFilter(){
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();    //CharacterEncodingFilter�뒗 �뒪�봽留곸씠 �젣怨듯븯�뒗 �겢�옒�뒪濡� �쎒�뿉�꽌 二쇨퀬諛쏅뒗 �뜲�씠�꽣�쓽 �뿤�뜑媛믪쓣 UTF-8濡� �씤肄붾뵫 �빐以�.
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);  //湲곕낯媛믪� false濡� �꽕�젙�릺�뼱 �엳�쓬.
		
		return characterEncodingFilter;
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter(){
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));   //StringHttpMessageConverter�뒗 @ResponseBody瑜� �씠�슜�븯�뿬 寃곌낵瑜� 異쒕젰�븷 �븣 寃곌낵瑜� UTF-8 濡� �꽕�젙�븿.
	}
	
}
