package com.aop;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MyPerformanceAop {

    @Pointcut("execution(* com.example.myperformanceapp.MainActivity.**(..))")
    public void executionTestAop(){ }

//    @Pointcut("execution(@com.example.myperformanceapp.MainActivity  * *(..))")
//    public void executionTestAop() {
//    }

    @Around("executionTestAop()")
    public void executionTestAopAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d("AOP", "MainActivity executionTestAopAround: before");
        /*执行MainActivity中的testAop()方法---执行原方法*/
        joinPoint.proceed();
        Log.d("AOP", "MainActivity executionTestAopAround: After");
    }
}
