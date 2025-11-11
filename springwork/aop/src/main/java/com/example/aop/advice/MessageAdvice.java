package com.example.aop.advice;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MessageAdvice {
	@Before("execution(* com.example.aop.service.message.MessageService*.*(..))")
	public void start_log(JoinPoint jp) {
		System.out.println("class: " + jp.getSignature());
		System.out.println("method: " + jp.getSignature().getName());
		System.out.println("args: " + Arrays.toString(jp.getArgs()));
	}
	
	@Around("execution(* com.example.aop.service.message.MessageService*.*(..))")
	public Object time_log(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		
		Object result = pjp.proceed();
		
		long end = System.currentTimeMillis();
		System.out.println(pjp.getSignature().getName() + ":" + (end - start));
		System.out.println("==========================");
		return result;
	}
}