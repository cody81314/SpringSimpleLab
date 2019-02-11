package com.example.demo.aop.aspect;

import com.example.demo.util.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.constant.AOPConst.LOGGER_ORDER;
import static com.example.demo.constant.AOPConst.POINTCUT_CONTROLLERLAYER;
import static com.example.demo.constant.AOPConst.POINTCUT_SERVICELAYER;

@Component
@Aspect
@Order(LOGGER_ORDER)
public class LoggerAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

    LoggerAspectCommonMethod loggerACM = new LoggerAspectCommonMethod();
    
    @Before(value = POINTCUT_SERVICELAYER)
    public void logBeforeService(JoinPoint joinPoint) {
        String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        String signatureName = joinPoint.getSignature().getName();
        String methodName = String.format("%s：%s ", targetClassName, signatureName);
        String args = Arrays.stream(joinPoint.getArgs())
                .filter(e -> !(e instanceof byte[]))
                .map(JsonUtil::convertObjectToJsonString)
                .collect(Collectors.joining(", "));

        LOGGER.info("{}({}) Start...", methodName, args);
    }

    @After(value = POINTCUT_SERVICELAYER)
    public void logAfterService(JoinPoint joinPoint) {
        String targetClassName = joinPoint.getTarget().getClass().getSimpleName();
        String signatureName = joinPoint.getSignature().getName();
        String methodName = String.format("%s：%s ", targetClassName, signatureName);

        LOGGER.info("{}() End...", methodName);
    }

    @Around(value = POINTCUT_SERVICELAYER)
    public Object logAroundService(ProceedingJoinPoint jp) throws Throwable {
        String targetClassName = jp.getTarget().getClass().getSimpleName();
        String signatureName = jp.getSignature().getName();
        String methodName = String.format("%s：%s ", targetClassName, signatureName);

        long beginTime = System.currentTimeMillis();
        Object result = jp.proceed();
        long serviceCostTime = System.currentTimeMillis() - beginTime;

        LOGGER.info("{}() cost time: {}", methodName, serviceCostTime);

        return result;
    }
    
    /*====== For Controller ===*/
    /*-------------------------------------*/
    @Around(value = POINTCUT_CONTROLLERLAYER)
    public Object logAroundController(ProceedingJoinPoint jp) throws Throwable {       
        String methodName = loggerACM.getMethodName(jp);
        Map<String,Object>  map = loggerACM.getServiceCostTime(jp);
        LOGGER.info("{}() 所花費時間: {}", methodName, map.get("time"));

        return map.get("result");
    }
    
    @Before(value = POINTCUT_CONTROLLERLAYER)
    public void logBeforeController(JoinPoint joinPoint) {
    	String methodName = loggerACM.getMethodName((ProceedingJoinPoint)joinPoint);
        String args = loggerACM.getargs(joinPoint);    	
        LOGGER.info("{}({}) 開始執行...", methodName, args);
    }
    
    @After(value = POINTCUT_CONTROLLERLAYER)
    public void logAfterController(JoinPoint joinPoint) {    	
        String methodName = loggerACM.getMethodName((ProceedingJoinPoint)joinPoint);
        LOGGER.info("{}() 執行結束...", methodName);
    }
    /*-------------------------------------*/
    
    
    @AfterThrowing(throwing="rEx", pointcut="execution(* com.example.demo.service..*.*(..))")
	public void showRollbackMessage(JoinPoint joinPoint,Exception rEx){
		LOGGER.error("{}()拋出異常狀況:  {}", joinPoint.getSignature().getName(),rEx.getLocalizedMessage());
		//LOGGER.error("注意!!: 若之前有執行新增或變動,都會被RollBack");
	}
	
    

    
    
}
