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
	//@Around�� ���� ��� �޼��带 ������ �� �ִ� ������ ������ �ְ�, �޼����� ���� ���� ���� �Ŀ� ó���� �����ϴ�.
	//ProceedingJointPoint�� @Around�� ���� �����ؼ� �Ķ���ͳ� ���� ���� ó���� �� �ִ�.
	@Around("execution(* com.codehows.service.SampleService*.*(..))")
	//logTime()�� ProceedingJoinPoint��� �Ķ���͸� ����.
	//ProceedingJoinPoint�� AOP�� ����� �Ǵ� Target�̳� �Ķ���� ���� �ľ��� �Ӹ� �ƴ϶�, ���� ������ ������ �� ����
	public Object logTime( ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("Target: " + pjp.getTarget());
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		//invoke method //���Ͽ� ���� �ʱ�ȭ
		Object result = null;
		
		try {
			result = pjp.proceed();				//�޼��� ���� -> ��� ���� result ����
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();	//�޼��� ���� �� �ð� �� ��������
		log.info("TIME: " + (end - start));		//�޼��尡 �����ϴµ� �ɸ� �ð� ���ϱ�
		return result;
	}
}
