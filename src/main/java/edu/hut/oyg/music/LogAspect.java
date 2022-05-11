package edu.hut.oyg.music;

import edu.hut.oyg.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(public * edu.hut.oyg.music.controller.*.*(..)))")
    public Object controllerLog(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long startTime = System.currentTimeMillis();
        log.info("-----{}-----", point.getSignature().getName());
        Object res = point.proceed();
        log.info("response: {}",res);
        log.info("execute time: {}",(System.currentTimeMillis() - startTime) + "ms");
        log.info("---------------------");
        return res;
    }
}
