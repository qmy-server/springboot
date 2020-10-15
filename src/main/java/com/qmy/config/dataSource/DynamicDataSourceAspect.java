package com.qmy.config.dataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {


    @Pointcut("execution(* com.qmy.services..*.*(..))")
    public void pointCut() {
    }

    /**
     * 执行方法前手动更换数据源
     *自己写在service里手动加入的数据源执行方法
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        DataSourceKey dataSourceKey = targetDataSource.dataSourceKey();
        String methodName=joinPoint.getSignature().getName();
        if (dataSourceKey==DataSourceKey.ds_slave ){
            DynamicDataSourceContextHolder.set(DataSourceKey.ds_slave);
        }
        if (dataSourceKey==DataSourceKey.ds_master ){
            DynamicDataSourceContextHolder.set(DataSourceKey.ds_master);
        }
    }

    /**
     * 执行方法后清除数据源设置
     *
     * @param joinPoint        切点
     * @param targetDataSource 动态数据源
     */
    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        System.out.println("执行清理方法");
        DynamicDataSourceContextHolder.clear();
    }

    @Before(value = "pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {//判断是否为接口方法
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                System.out.println("error");
            }
        }
        if (method.getName().startsWith("get")
                ||method.getName().startsWith("query")
                ||method.getName().startsWith("find")
                ||method.getName().startsWith("select")
                ||method.getName().startsWith("check")){
            DynamicDataSourceContextHolder.set(DataSourceKey.ds_slave);
        }else{
            //切换dataSource
            DynamicDataSourceContextHolder.set(DataSourceKey.ds_master);
        }
    }
}
