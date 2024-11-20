package com.shipment.track.shipment_tracker.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 *
 * **/
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public * com.shipment.track.shipment_tracker.sources.*.* (..))")
    private void logControllerTime(){

    }

    @Around(value = "logControllerTime()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Logger LOG = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        Long timeInMillis = Instant.now().toEpochMilli();
        LOG.info("Started Processing message {}",methodName);
        Object proceed = joinPoint.proceed();
        Long time = Instant.now().toEpochMilli() - timeInMillis;
        LOG.info("Completed Processing in {} ms", time);
        return proceed;
    }
}
