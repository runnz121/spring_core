package kuku.mainApi.common.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class AopConfig {

//    @Around("execution(* kuku.mainApi..*.TestService.*(..))")
    public Object logAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.printf(String.format("%s %s","--------- 시작 --------", LocalDateTime.now()));
        Object reval = proceedingJoinPoint.proceed();
        System.out.printf(String.format("%s %s","--------- 종료 --------", LocalDateTime.now()));
        return reval;
    }

    @Around("@annotation(TimeCheck)")
    public Object annotationAop(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("어노테이션 사용 ");
        System.out.printf(String.format("%s %s","--------- 시작 --------", LocalDateTime.now()));
        Object reval = proceedingJoinPoint.proceed();
        System.out.printf(String.format("%s %s","--------- 종료 --------", LocalDateTime.now()));
        return reval;
    }
}

