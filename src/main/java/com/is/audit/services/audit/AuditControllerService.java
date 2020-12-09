package com.is.audit.services.audit;

import com.is.audit.model.Audit;
import com.is.audit.model.AuditType;
import com.is.audit.model.RestAudit;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class AuditControllerService extends AuditService{

    Logger logger = LoggerFactory.getLogger(AuditControllerService.class);

    @Override
    Audit createAudit(Object joinPointArg) {
        JoinPoint joinPoint = (JoinPoint ) joinPointArg;
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        RestAudit restAudit = new RestAudit();
        restAudit.setJavaMethod(className + "." + methodName);
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes()).getRequest();
            restAudit.setAction(request.getMethod());
            restAudit.setBody(request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator())));
            restAudit.setUrl(request.getRequestURL().toString());
            restAudit.setIp(request.getRemoteAddr());

        } catch (Exception e) {
            logger.error("Error createAudit ", e);
        }

        return restAudit;
    }

    @Override
    AuditType getAuditType() {
        return AuditType.CONTROLLER;
    }
}
