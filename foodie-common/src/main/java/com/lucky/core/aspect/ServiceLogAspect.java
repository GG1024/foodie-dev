package com.lucky.core.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @FileName: ServiceLogAspect.java
 * @description: 日志切面
 * @author: 欧阳小广
 * @Date: 2021-01-26
 **/
@Component
@Aspect
public class ServiceLogAspect {
    private Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("execution(* com.lucky.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("===== 开始执行 ==== {}.{}", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        long beginTime = System.currentTimeMillis();

        Object o = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long takeTime = endTime - beginTime;
        if (takeTime > 3000) {
            logger.error("==== 执行结束 ====，耗时{}ms", takeTime);
        } else if (takeTime > 2000) {
            logger.warn("==== 执行结束 ====，耗时{}ms", takeTime);
        } else {
            logger.info("==== 执行结束 ====，耗时{}ms", takeTime);
        }
        return o;
    }

}
