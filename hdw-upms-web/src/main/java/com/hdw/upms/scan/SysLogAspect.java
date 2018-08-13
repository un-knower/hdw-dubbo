package com.hdw.upms.scan;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hdw.upms.entity.SysLog;
import com.hdw.upms.service.IUpmsApiService;
import com.hdw.upms.shiro.ShiroKit;
import com.hdw.upms.shiro.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Enumeration;


/**
 * @author TuMinglong
 * @description AOP 日志
 * @date 2018年3月6日 上午10:14:28
 */
@Aspect
@Component
public class SysLogAspect {
    private final static Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    private long startTime = 0;
    private long endTime = 0;

    @Reference(version = "1.0.0" , application = "${dubbo.application.id}" , group = "hdw-upms")
    private IUpmsApiService upmsApiService;

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void cutController() {
    }

    @Before("cutController()")
    public void doBefore(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
        //URL
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url={}" , request.getRequestURL());
        //method
        logger.info("method={}" , request.getMethod());
        //ip
        logger.info("ip={}" , request.getRemoteAddr());
        //类方法
        logger.info("class_method={}" , joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        logger.info("args={}" , joinPoint.getArgs());
    }

    @Around("cutController()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
        String strMethodName = point.getSignature().getName();
        String strClassName = point.getTarget().getClass().getName();
        Object[] params = point.getArgs();
        StringBuffer bfParams = new StringBuffer();
        Enumeration<String> paraNames = null;
        HttpServletRequest request = null;
        if (params != null && params.length > 0) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }

        String strMessage = String.format("[类名]:%s,[方法]:%s,[参数]:%s" , strClassName, strMethodName, bfParams.toString());
        logger.info(strMessage);
        if (isWriteLog(strMethodName)) {
            try {
                ShiroUser shiroUser = ShiroKit.getUser();
                if (null != shiroUser) {
                    String loginName = shiroUser.getLoginName();
                    SysLog sysLog = new SysLog();
                    sysLog.setLoginName(loginName);
                    sysLog.setRoleName(shiroUser.getRoles().get(0));
                    sysLog.setOptContent(strMessage);
                    sysLog.setCreateTime(new Date());
                    if (request != null) {
                        sysLog.setClientIp(request.getRemoteAddr());
                    }
                    logger.info(sysLog.toString());
                    upmsApiService.insertSysLog(sysLog);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        return point.proceed();
    }

    @AfterReturning(returning = "object" , pointcut = "cutController()")
    public void doAfterReturning(Object object) {
        if (object != null) {
            logger.info("response={}" , object.toString());
        } else {
            logger.info("response=");
        }

    }

    @After("cutController()")
    public void doAfter() {
        endTime = System.currentTimeMillis();
        long totalMillis = endTime - startTime;
        logger.info("----" + "执行时间：" + totalMillis + "毫秒" + "----");
    }

    private boolean isWriteLog(String method) {
        String[] pattern = {"login" , "logout" , "add" , "edit" , "delete" , "grant"};
        for (String s : pattern) {
            if (method.indexOf(s) > -1) {
                return true;
            }
        }
        return false;
    }
}
