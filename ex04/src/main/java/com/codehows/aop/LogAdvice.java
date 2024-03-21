package com.codehows.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Arrays;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {
	
	@Before( "execution(* com.codehows.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("======================================");
	}
	
	@Before("execution(* com.codehows.service.SampleService*.doAdd(String,String)) && args(str1, str2)") 
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* com.codehows.service.SampleService*.*(..))", throwing="exception")
	public void logException(Exception exception) {
		log.info("Exception....!!!!!");
		log.info("exception: " + exception);
	}
	//@Around는 직접 대상 메서드를 실행할 수 있는 권한을 가지고 있고, 메서드의 실행 전과 실행 후에 처리가 가능하다.
	//ProceedingJointPoint는 @Around와 같이 결합해서 파라미터나 예외 등을 처리할 수 있다.
	@Around("execution(* com.codehows.service.SampleService*.*(..))")
	//logTime()은 ProceedingJoinPoint라는 파라미터를 지정.
	//ProceedingJoinPoint는 AOP의 대상이 되는 Target이나 파라미터 등을 파악할 뿐만 아니라, 직접 실행을 결정할 수 있음
	public Object logTime( ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		//invoke method //리턴에 대한 초기화
		Object result = null;
		
		try {
			result = pjp.proceed();				//메서드 실행 -> 결과 값을 result 저장
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();	//메서드 실행 후 시간 값 가져오기
		log.info("TIME: " + (end - start));		//메서드가 실행하는데 걸린 시간 구하기
		return result;
	}
}
