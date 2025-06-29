package com.SpringBootApp.UrlShortner.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution (* com.SpringBootApp.UrlShortner.service.UrlServiceImpl.*(..))")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Entering method: " + methodName);
    }

    @After("execution (* com.SpringBootApp.UrlShortner.service.UrlServiceImpl.*(..))")
    public void logAfter(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Exiting method: " + methodName);
    }
}
