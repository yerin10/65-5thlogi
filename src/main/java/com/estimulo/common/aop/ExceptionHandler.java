package com.estimulo.common.aop;


import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.estimulo.authorityManager.exception.DataNotInputException;
import com.estimulo.authorityManager.exception.IdNotFoundException;
import com.estimulo.authorityManager.exception.PwMissMatchException;
import com.estimulo.authorityManager.exception.PwNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {
	 
	  @org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)
	  public ModelAndView idNotFoundExceptionHandler(HttpServletRequest request, IdNotFoundException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -2);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################IdNotFoundException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  @org.springframework.web.bind.annotation.ExceptionHandler(DataNotInputException.class)
	  public ModelAndView dataNotInputExceptionHandler(HttpServletRequest request, DataNotInputException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -1);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################DataNotInputException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  @org.springframework.web.bind.annotation.ExceptionHandler(PwMissMatchException.class)
	  public ModelAndView pwMissMatchException(HttpServletRequest request, PwMissMatchException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -4);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################PwMissMatchException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  @org.springframework.web.bind.annotation.ExceptionHandler(PwNotFoundException.class)
	  public ModelAndView pwNotFoundExceptionHandler(HttpServletRequest request, PwNotFoundException e) {
	     
		  ModelAndView mv = new ModelAndView("/loginform");
		  
		  mv.addObject("errorCode", -3);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#######################PwNotFoundException#################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	  @org.springframework.web.bind.annotation.ExceptionHandler(DataAccessException.class)
	  public ModelAndView pwNotFoundExceptionHandler(HttpServletRequest request, DataAccessException e) {
	     
		  ModelAndView mv = new ModelAndView("/errorPage");
		  
		  mv.addObject("errorCode", -3);
		  mv.addObject("errorMsg", e.getMessage());
	      System.out.println("#####################DataAccessException###################1");
	     	      
	      log.error("Request: " + request.getRequestURL() +"\n"+ " raised " + e);
	      
	      return mv;
	  }
	  
	 @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	 public ModelAndView defaultExceptionHandler(HttpServletRequest request,Exception exception){ 
		
		 ModelAndView mv = new ModelAndView("/errorPage"); 
		 mv.addObject("exception", exception);
		 System.out.println("******************** 전체익셉션");
		 log.error("defaultExceptionHandler", exception);
	 
	 return mv; 
	 
	 }
	 
}
