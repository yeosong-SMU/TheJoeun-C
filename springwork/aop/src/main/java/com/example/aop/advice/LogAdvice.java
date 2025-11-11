package com.example.aop.advice;

import com.example.aop.AopApplication;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAdvice {

    private final AopApplication aopApplication;

    LogAdvice(AopApplication aopApplication) {
        this.aopApplication = aopApplication;
    }
	@Around("execution(* com.example.aop.controller..*Controller.*(..)) || "
			+ "execution(* com.example.aop.service..*Impl.*(..)) || "
			+ "execution(* com.example.aop.model..*Impl.*(..))")
	public Object print_log(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		String type = joinPoint.getSignature().getDeclaringTypeName();
		String name = "";
		
		if(type.indexOf("Controller") != -1) {
			name = "Controller\t: ";
		} else if(type.indexOf("Service") != -1) {
			name = "Service\t: ";
		} else if(type.indexOf("DAO") != -1) {
			name = "DAO\t: ";
		}
		System.out.println(name + type + "." + joinPoint.getSignature().getName() + "()");
		System.out.println(Arrays.toString(joinPoint.getArgs()));
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("실행시간: " + time);
		return result;
	}
}
