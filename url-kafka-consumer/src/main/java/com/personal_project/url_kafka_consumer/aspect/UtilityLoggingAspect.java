package com.personal_project.url_kafka_consumer.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Aspect
@Component
public class UtilityLoggingAspect {

    @Around("execution(public reactor.core.publisher.Mono *(..)) && within(com.personal_project.url_kafka_consumer.rest..*)")
    public Object logLatency(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object result = proceedingJoinPoint.proceed();
        Mono<?> mono = (Mono<?>) result;
        return Mono.defer(() -> {
            long startTime = System.currentTimeMillis();
            return mono
            .doOnSuccess(success -> {
                long elapsed = System.currentTimeMillis() - startTime;
                logger.debug("Method {} completed successfully in {} ms", methodName, elapsed);
            })
            .doOnError(error -> {
                long elapsed = System.currentTimeMillis() - startTime;
                logger.debug("Method {} errored after in {} ms: {}", methodName, elapsed, error.getMessage());
            });
        });
    }
}
