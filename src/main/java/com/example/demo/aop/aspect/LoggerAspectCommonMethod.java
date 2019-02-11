package com.example.demo.aop.aspect;

import com.example.demo.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.constant.AOPConst.LOGGER_ORDER;
import static com.example.demo.constant.AOPConst.POINTCUT_CONTROLLERLAYER;

@Component
public class LoggerAspectCommonMethod {    
    
    public String getMethodName(ProceedingJoinPoint joinPoint) {
    	String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        String signatureName = joinPoint.getSignature().getName();
        String methodName = String.format("%s：%s ", targetClassName, signatureName);
		return methodName;
	}
    
    public Map<String,Object> getServiceCostTime(ProceedingJoinPoint jp) {
    	long beginTime = System.currentTimeMillis();
    	Object result = new Object();
        try {
			result = jp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long serviceCostTime = System.currentTimeMillis() - beginTime;
        Map<String,Object>  map = new HashMap<String,Object>();
        map.put("result", result);
        map.put("time", serviceCostTime);
 		return map;
	}
    
    public String getargs(JoinPoint joinPoint) {
    	String args = Arrays.stream(joinPoint.getArgs())
                .filter(e -> !(e instanceof byte[]))
                .map(JsonUtil::convertObjectToJsonString)
                .collect(Collectors.joining(", "));
		return args;
	}
	
    
    
}
