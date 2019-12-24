package com.dzk.aspect;

import com.alibaba.fastjson.JSON;
import com.dzk.dao.SysLogMapper;
import com.dzk.model.SysLog;
import com.dzk.model.UserAdmin;
import com.dzk.service.SysLogService;
import com.dzk.utils.IPUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {
    private Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("execution(public * com.dzk.controller.*.*(..))")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point,time);
        return result;
    }

    public void saveLog(ProceedingJoinPoint point,long time){
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        if(method.isAnnotationPresent(com.dzk.commonent.SysLog.class)){
            com.dzk.commonent.SysLog log = method.getAnnotation(com.dzk.commonent.SysLog.class);
            sysLog.setOperation(log.value());
        }
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className+"."+methodName+"()");

        Object[] arg =point.getArgs();
        String param = JSON.toJSONString(arg);
        sysLog.setParams(param);

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        //用户名
//        String username = ((UserAdmin) SecurityUtils.getSubject().getPrincipal()).getUsername();
        String username = "";
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreate_time(new Date());
        logger.info(JSON.toJSONString(sysLog));
        sysLogService.insert(sysLog);
    }

}
