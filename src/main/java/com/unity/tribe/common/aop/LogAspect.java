package com.unity.tribe.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// TODO API 로깅 AOP 구현
@Aspect
@Component
@Slf4j
public class LogAspect {


    @AfterThrowing(pointcut = "execution(* com.unity.tribe.domain..*Controller.*(..))", throwing = "throwable")
    public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
        // TODO Exception 로깅
    }

    @AfterReturning(pointcut = "execution(* com.unity.tribe.domain..*Controller.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

    }
}
