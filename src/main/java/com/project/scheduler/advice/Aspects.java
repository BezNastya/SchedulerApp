package com.project.scheduler.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Component
@ConditionalOnExpression("${aspect.enabled:true}")
public class Aspects {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(com.project.scheduler.advice.TrackExecutionTime)")
    public void logExecTimePointCut(){}

    @Around("logExecTimePointCut()")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.warn("Class Name: "+ point.getSignature().getDeclaringTypeName() +". Method Name: "
                + point.getSignature().getName() + ". Time taken for Execution is : "
                + (endTime-startTime) +"ms");
        return object;
    }


    @Around("@annotation(com.project.scheduler.advice.TrackParameters)")
    public Object params(ProceedingJoinPoint point) throws Throwable {
        logger.info("[logSignatureAspect] Entered: {}.{}() with arguments = {}", point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), Arrays.toString(point.getArgs()) );
        Object proceed = point.proceed();
        logger.info("[logSignatureAspect] Exited: {}.{}() with result = {}", point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(), proceed);
        return proceed;
    }
}
