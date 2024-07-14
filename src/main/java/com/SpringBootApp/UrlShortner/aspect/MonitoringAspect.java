package com.SpringBootApp.UrlShortner.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MonitoringAspect {

    private static final Logger LOGGER = LogManager.getLogger(MonitoringAspect.class);

    @Around("execution (* com.SpringBootApp.UrlShortner.rest.MyRestController.*(..))")
    public Object logRESTAction(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        LOGGER.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }
}
