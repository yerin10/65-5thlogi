package com.estimulo.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@Component
@Aspect
@Slf4j
public class LoggerAspect {
	  //Logger log = LoggerFactory.getLogger(getClass());
	
	@Around("execution(* com..controller.*.*(..)) or execution(* com..serviceFacade.*.*(..)) or execution(* com..applicationService.*.*(..)) or execution(* com..dao.*.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		if (name.indexOf("Controller") > -1) {
			type = "Controller  \t:  ";
		}
		else if (name.indexOf("Facade") > -1) {
			type = "ServiceFacadeImpl  \t:  ";
		}
		else if (name.indexOf("Service") > -1) {
			type = "ServiceImpl  \t:  ";
		}
		else if (name.indexOf("DAO") > -1) {
			type = "DAO  \t\t:  ";
		}
		log.info(type + name + "." + joinPoint.getSignature().getName() + "()");
		Object obj = joinPoint.proceed();
		
		return obj;
	}
}
