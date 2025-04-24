package tn.esprit.ecocycletech.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Before("execution(* tn.esprit.ecocycletech.Service..*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        //String name = joinPoint.getSignature().getName();
        //log.info("In method " + name + " : ");
        log.info("***************Before : Method : " + joinPoint.getSignature().getName());
    }
    @AfterReturning("execution(* tn.esprit.ecocycletech.Service..*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        log.info("***************After returning: Method : " + joinPoint.getSignature().getName());
    }
    @AfterThrowing("execution(* tn.esprit.ecocycletech.Service..*.*(..))")
    public void logMethodThrow(JoinPoint joinPoint) {
        log.info("***************After throwing: Method : " + joinPoint.getSignature().getName());
        log.info("error:"+joinPoint.getKind());
    }
    @After("execution(* tn.esprit.ecocycletech.Service..*.*(..))")
    public void logMethodEnter(JoinPoint joinPoint) {
        log.info("***************After entering: Method : " + joinPoint.getSignature().getName());
    }
}
